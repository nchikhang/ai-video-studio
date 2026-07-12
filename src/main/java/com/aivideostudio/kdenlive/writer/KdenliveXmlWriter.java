package com.aivideostudio.kdenlive.writer;

import com.aivideostudio.kdenlive.model.*;
import com.aivideostudio.kdenlive.support.FrameUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * v0.8 — Kdenlive XML Writer (NATIVE, targeting Kdenlive 21.12 / MLT 7.4).
 *
 * Rewritten after decoding a real *.kdenlive golden file. Emits the Kdenlive-native
 * timeline structure so clips survive project open (the earlier flat-MLT output was
 * valid MLT but Kdenlive stripped every clip: "Timeline clip without bin reference").
 *
 * Native structure this writer generates from the logical model:
 *   mlt (producer="main_bin")
 *     profile
 *     producer*                          <- one per bin clip (full kdenlive:* props)
 *     playlist id="main_bin"             <- docproperties + one <entry> per bin clip
 *     producer id="black_track"          <- background color track
 *     [per logical track, audio first then video]
 *        playlist id="playlist{2i}"      <- entries (+ nested kdenlive:id) + <blank>
 *        playlist id="playlist{2i+1}"    <- empty second playlist (Kdenlive needs the pair)
 *        tractor  id="subtractor{i}"     <- 2 tracks -> the playlist pair (+ audio filters)
 *     tractor id="main"                  <- black_track + one track per subtractor
 *                                           + mix (audio) / frei0r.cairoblend (video) transitions
 *
 * INPUT (logical model, kept simple):
 *   - project.getProducers()  : bin clips (image/audio/video)
 *   - project.getTractor().getTracks() : logical timeline tracks; each Track.playlistId
 *     points to a Playlist in project.getPlaylists() holding that track's entries.
 *   - project.getMarkers()    : chapters -> guides.
 * All Kdenlive scaffolding (main_bin, black_track, playlist pairs, sub-tractors,
 * transitions) is synthesised here, so the model stays clean.
 */
public class KdenliveXmlWriter {

    private final String mltVersion;
    private final String kdenliveVersion;
    private final String rootPath;
    private int filterSeq; // reset per toXml(), gives filters unique ids like the golden file

    public KdenliveXmlWriter(String rootPath) {
        this(rootPath, "7.4.0", "21.12.3");
    }

    public KdenliveXmlWriter(String rootPath, String mltVersion, String kdenliveVersion) {
        this.rootPath = rootPath;
        this.mltVersion = mltVersion;
        this.kdenliveVersion = kdenliveVersion;
    }

    // ------------------------------------------------------------------ API
    public void write(KdenliveProject project, Path output) throws Exception {
        Files.createDirectories(output.toAbsolutePath().getParent());
        Files.writeString(output, toXml(project), StandardCharsets.UTF_8);
    }

    public String toXml(KdenliveProject project) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().newDocument();
        doc.setXmlStandalone(true);

        double fps = project.getProfile().getFps();
        filterSeq = 0;

        Element mlt = doc.createElement("mlt");
        mlt.setAttribute("LC_NUMERIC", "C");
        mlt.setAttribute("version", mltVersion);
        mlt.setAttribute("producer", "main_bin");
        mlt.setAttribute("root", rootPath);
        doc.appendChild(mlt);

        mlt.appendChild(profile(doc, project.getProfile()));

        // 1) bin producers (assign kdenlive:id per clip)
        List<Producer> bin = project.getProducers();
        int[] kids = new int[bin.size()];
        for (int i = 0; i < bin.size(); i++) {
            kids[i] = i + 2;                       // Kdenlive reserves 0/1
            mlt.appendChild(binProducer(doc, bin.get(i), kids[i], fps));
        }

        // 2) main_bin (project bin) — THE fix: every timeline clip must be listed here
        mlt.appendChild(mainBin(doc, project, bin, fps));

        // 3) black background track
        long totalFrames = totalFrames(project, fps);
        mlt.appendChild(blackTrack(doc, totalFrames, fps));

        // 4) split logical tracks: audio first, then video (Kdenlive bottom-up order)
        List<Track> logical = project.getTractor() == null
                ? List.of() : project.getTractor().getTracks();
        List<Track> audio = new ArrayList<>();
        List<Track> video = new ArrayList<>();
        for (Track t : logical) {
            if (t.getType() == TrackType.AUDIO) audio.add(t); else video.add(t);
        }
        List<Track> ordered = new ArrayList<>();
        ordered.addAll(audio);
        ordered.addAll(video);

        // 5) per track: playlist pair + sub-tractor
        List<String> subTractorIds = new ArrayList<>();
        for (int i = 0; i < ordered.size(); i++) {
            Track t = ordered.get(i);
            boolean isAudio = t.getType() == TrackType.AUDIO;
            String plMain = "playlist" + (2 * i);
            String plExtra = "playlist" + (2 * i + 1);

            Playlist src = findPlaylist(project, t.getPlaylistId());
            mlt.appendChild(trackPlaylist(doc, plMain, src, bin, kids, isAudio, fps));
            mlt.appendChild(emptyPlaylist(doc, plExtra, isAudio));

            String subId = "subtractor" + i;
            subTractorIds.add(subId);
            mlt.appendChild(subTractor(doc, subId, plMain, plExtra, isAudio, totalFrames, fps));
        }

        // 6) main tractor
        mlt.appendChild(mainTractor(doc, project, subTractorIds, audio.size(),
                totalFrames, fps));

        return serialize(doc);
    }

    // -------------------------------------------------------------- producers
    private Element binProducer(Document doc, Producer p, int kid, double fps) {
        boolean image = p.getType() == ProducerType.IMAGE;
        long durFrames = clipFrames(p, fps);              // default/insert duration
        long available = image ? Math.max(durFrames, 15000) : durFrames; // playable length
        Element e = el(doc, "producer");
        e.setAttribute("id", p.getId());
        e.setAttribute("in", tc(0, fps));
        e.setAttribute("out", tc(Math.max(available - 1, 0), fps));

        prop(doc, e, "length", String.valueOf(available));
        prop(doc, e, "eof", "pause");
        prop(doc, e, "resource", p.getResource());
        if (image) {
            prop(doc, e, "ttl", "1");
            prop(doc, e, "aspect_ratio", "1");
            prop(doc, e, "progressive", "1");
        } else { // audio / video
            prop(doc, e, "audio_index", "0");
            prop(doc, e, "video_index", p.getType() == ProducerType.AUDIO ? "-1" : "0");
        }
        prop(doc, e, "seekable", "1");
        prop(doc, e, "mlt_service", mltService(p.getType()));
        prop(doc, e, "kdenlive:clipname", p.getName() == null ? "" : p.getName());
        prop(doc, e, "kdenlive:duration", tc(durFrames, fps));
        prop(doc, e, "kdenlive:proxy", "-");
        prop(doc, e, "kdenlive:originalurl", p.getResource());
        prop(doc, e, "kdenlive:id", String.valueOf(kid));
        prop(doc, e, "kdenlive:folderid", "-1");
        p.getProperties().forEach((k, v) -> prop(doc, e, k, v)); // model overrides/extras
        return e;
    }

    private Element blackTrack(Document doc, long totalFrames, double fps) {
        Element e = el(doc, "producer");
        e.setAttribute("id", "black_track");
        e.setAttribute("in", tc(0, fps));
        e.setAttribute("out", tc(Math.max(totalFrames - 1, 0), fps));
        prop(doc, e, "length", "2147483647");
        prop(doc, e, "eof", "continue");
        prop(doc, e, "resource", "black");
        prop(doc, e, "aspect_ratio", "1");
        prop(doc, e, "mlt_service", "color");
        prop(doc, e, "mlt_image_format", "rgba");
        prop(doc, e, "set.test_audio", "0");
        return e;
    }

    // ------------------------------------------------------------- main_bin
    private Element mainBin(Document doc, KdenliveProject project,
                            List<Producer> bin, double fps) {
        Element e = el(doc, "playlist");
        e.setAttribute("id", "main_bin");
        prop(doc, e, "kdenlive:docproperties.kdenliveversion", kdenliveVersion);
        prop(doc, e, "kdenlive:docproperties.profile",
                project.getProfile().getDescription());
        prop(doc, e, "kdenlive:docproperties.version", "1.04");
        if (!project.getMarkers().isEmpty()) {
            prop(doc, e, "kdenlive:docproperties.guides", guidesJson(project.getMarkers()));
        }
        prop(doc, e, "kdenlive:documentnotes", "");
        prop(doc, e, "xml_retain", "1");
        for (Producer p : bin) {
            long f = clipFrames(p, fps);
            Element en = el(doc, "entry");
            en.setAttribute("producer", p.getId());
            en.setAttribute("in", tc(0, fps));
            en.setAttribute("out", tc(Math.max(f - 1, 0), fps));
            e.appendChild(en);
        }
        return e;
    }

    // ----------------------------------------------------------- playlists
    private Element trackPlaylist(Document doc, String id, Playlist src,
                                  List<Producer> bin, int[] kids,
                                  boolean isAudio, double fps) {
        Element e = el(doc, "playlist");
        e.setAttribute("id", id);
        if (isAudio) prop(doc, e, "kdenlive:audio_track", "1");
        if (src == null) return e;

        long cursor = 0;
        for (PlaylistEntry pe : src.getEntries()) {
            long gap = pe.getTimelineFrame() - cursor;
            if (gap > 0) {
                Element blank = el(doc, "blank");
                blank.setAttribute("length", tc(gap, fps));
                e.appendChild(blank);
                cursor += gap;
            }
            Element en = el(doc, "entry");
            en.setAttribute("producer", pe.getProducerId());
            en.setAttribute("in", tc(pe.getInFrame(), fps));
            en.setAttribute("out", tc(pe.getOutFrame(), fps));
            // link back to bin clip
            prop(doc, en, "kdenlive:id", String.valueOf(kidOf(pe.getProducerId(), bin, kids)));
            // per-clip effects (v0.9 composer: qtblend transform, etc.)
            for (Filter f : pe.getFilters()) {
                en.appendChild(entryFilter(doc, f));
            }
            e.appendChild(en);
            cursor += pe.getDuration();
        }
        return e;
    }

    private Element emptyPlaylist(Document doc, String id, boolean isAudio) {
        Element e = el(doc, "playlist");
        e.setAttribute("id", id);
        if (isAudio) prop(doc, e, "kdenlive:audio_track", "1");
        return e;
    }

    // ------------------------------------------------------------ tractors
    private Element subTractor(Document doc, String id, String plMain, String plExtra,
                               boolean isAudio, long totalFrames, double fps) {
        Element e = el(doc, "tractor");
        e.setAttribute("id", id);
        e.setAttribute("in", tc(0, fps));
        e.setAttribute("out", tc(Math.max(totalFrames - 1, 0), fps));
        if (isAudio) prop(doc, e, "kdenlive:audio_track", "1");
        prop(doc, e, "kdenlive:trackheight", "69");
        prop(doc, e, "kdenlive:timeline_active", "1");

        String hide = isAudio ? "video" : "audio";
        Element t1 = el(doc, "track"); t1.setAttribute("hide", hide); t1.setAttribute("producer", plMain);
        Element t2 = el(doc, "track"); t2.setAttribute("hide", hide); t2.setAttribute("producer", plExtra);
        e.appendChild(t1); e.appendChild(t2);

        if (isAudio) appendAudioFilters(doc, e);
        return e;
    }

    private Element mainTractor(Document doc, KdenliveProject project,
                                List<String> subTractorIds, int audioCount,
                                long totalFrames, double fps) {
        Element e = el(doc, "tractor");
        e.setAttribute("id", "maintractor");
        e.setAttribute("in", tc(0, fps));
        e.setAttribute("out", tc(Math.max(totalFrames - 1, 0), fps));

        Element black = el(doc, "track");
        black.setAttribute("producer", "black_track");
        e.appendChild(black);
        for (String sub : subTractorIds) {
            Element tr = el(doc, "track");
            tr.setAttribute("producer", sub);
            e.appendChild(tr);
        }

        // transitions: audio tracks -> mix, video tracks -> frei0r.cairoblend
        int tIdx = 0;
        for (int i = 0; i < subTractorIds.size(); i++) {
            int bTrack = i + 1;                 // black is track 0
            boolean isAudio = i < audioCount;
            e.appendChild(transition(doc, "transition" + tIdx++, bTrack, isAudio));
        }
        appendAudioFilters(doc, e);
        return e;
    }

    private Element transition(Document doc, String id, int bTrack, boolean isAudio) {
        Element e = el(doc, "transition");
        e.setAttribute("id", id);
        prop(doc, e, "a_track", "0");
        prop(doc, e, "b_track", String.valueOf(bTrack));
        if (isAudio) {
            prop(doc, e, "mlt_service", "mix");
            prop(doc, e, "kdenlive_id", "mix");
            prop(doc, e, "internal_added", "237");
            prop(doc, e, "always_active", "1");
            prop(doc, e, "accepts_blanks", "1");
            prop(doc, e, "sum", "1");
        } else {
            prop(doc, e, "version", "0.9");
            prop(doc, e, "mlt_service", "frei0r.cairoblend");
            prop(doc, e, "kdenlive_id", "frei0r.cairoblend");
            prop(doc, e, "internal_added", "237");
            prop(doc, e, "always_active", "1");
        }
        return e;
    }

    /** Per-clip effect nested inside a playlist <entry> (e.g. qtblend transform). */
    private Element entryFilter(Document doc, Filter f) {
        Element fe = el(doc, "filter");
        fe.setAttribute("id", "filter" + filterSeq++);
        prop(doc, fe, "mlt_service", f.getService());
        for (Property p : f.getProperties()) {
            prop(doc, fe, p.getName(), p.getValue());
        }
        return fe;
    }

    private void appendAudioFilters(Document doc, Element parent) {
        Element f1 = el(doc, "filter");
        f1.setAttribute("id", "filter" + filterSeq++);
        prop(doc, f1, "window", "75");
        prop(doc, f1, "max_gain", "20dB");
        prop(doc, f1, "mlt_service", "volume");
        prop(doc, f1, "internal_added", "237");
        prop(doc, f1, "disable", "1");
        Element f2 = el(doc, "filter");
        f2.setAttribute("id", "filter" + filterSeq++);
        prop(doc, f2, "channel", "-1");
        prop(doc, f2, "mlt_service", "panner");
        prop(doc, f2, "internal_added", "237");
        prop(doc, f2, "start", "0.5");
        prop(doc, f2, "disable", "1");
        Element f3 = el(doc, "filter");
        f3.setAttribute("id", "filter" + filterSeq++);
        prop(doc, f3, "iec_scale", "0");
        prop(doc, f3, "mlt_service", "audiolevel");
        prop(doc, f3, "disable", "1");
        parent.appendChild(f1); parent.appendChild(f2); parent.appendChild(f3);
    }

    // -------------------------------------------------------------- helpers
    private Element profile(Document doc, Profile p) {
        int[] fr = frameRate(p.getFps());
        Element e = el(doc, "profile");
        e.setAttribute("description", p.getDescription());
        e.setAttribute("width", String.valueOf(p.getWidth()));
        e.setAttribute("height", String.valueOf(p.getHeight()));
        e.setAttribute("progressive", p.isProgressive() ? "1" : "0");
        e.setAttribute("sample_aspect_num", String.valueOf(p.getSampleAspectNum()));
        e.setAttribute("sample_aspect_den", String.valueOf(p.getSampleAspectDen()));
        e.setAttribute("display_aspect_num", String.valueOf(p.getDisplayAspectNum()));
        e.setAttribute("display_aspect_den", String.valueOf(p.getDisplayAspectDen()));
        e.setAttribute("frame_rate_num", String.valueOf(fr[0]));
        e.setAttribute("frame_rate_den", String.valueOf(fr[1]));
        e.setAttribute("colorspace", "709");
        return e;
    }

    private long clipFrames(Producer p, double fps) {
        if (p.getDuration() > 0) return FrameUtil.millisToFrame(p.getDuration(), fps);
        return FrameUtil.secondsToFrame(5, fps); // default still-image length
    }

    private long totalFrames(KdenliveProject project, double fps) {
        long max = 0;
        for (Playlist pl : project.getPlaylists()) {
            long len = 0;
            for (PlaylistEntry en : pl.getEntries()) {
                len = Math.max(len, en.getTimelineFrame() + en.getDuration());
            }
            max = Math.max(max, len);
        }
        return max;
    }

    private int kidOf(String producerId, List<Producer> bin, int[] kids) {
        for (int i = 0; i < bin.size(); i++) {
            if (bin.get(i).getId().equals(producerId)) return kids[i];
        }
        return 0;
    }

    private Playlist findPlaylist(KdenliveProject project, String id) {
        if (id == null) return null;
        for (Playlist pl : project.getPlaylists()) {
            if (id.equals(pl.getId())) return pl;
        }
        return null;
    }

    private String mltService(ProducerType type) {
        return switch (type) {
            case IMAGE -> "qimage";
            case AUDIO, VIDEO -> "avformat-novalidate";
            case COLOR -> "color";
        };
    }

    /** Kdenlive in/out timecode HH:MM:SS.mmm derived from frame index. */
    private String tc(long frame, double fps) {
        double totalSec = frame / fps;
        long ms = Math.round(totalSec * 1000.0);
        long hh = ms / 3_600_000; ms %= 3_600_000;
        long mm = ms / 60_000;    ms %= 60_000;
        long ss = ms / 1000;      ms %= 1000;
        return String.format("%02d:%02d:%02d.%03d", hh, mm, ss, ms);
    }

    private int[] frameRate(double fps) {
        long milli = Math.round(fps * 1000);
        if (milli == 23976) return new int[]{24000, 1001};
        if (milli == 29970) return new int[]{30000, 1001};
        if (milli == 59940) return new int[]{60000, 1001};
        return new int[]{(int) Math.round(fps), 1};
    }

    private String guidesJson(List<Marker> markers) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < markers.size(); i++) {
            Marker m = markers.get(i);
            if (i > 0) sb.append(",");
            sb.append("{\"comment\":\"").append(esc(m.getName()))
              .append("\",\"pos\":").append(m.getFrame()).append("}");
        }
        return sb.append("]").toString();
    }

    private String esc(String s) {
        return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private Element el(Document doc, String tag) { return doc.createElement(tag); }

    private void prop(Document doc, Element parent, String name, String value) {
        Element p = el(doc, "property");
        p.setAttribute("name", name);
        p.setTextContent(value == null ? "" : value);
        parent.appendChild(p);
    }

    private String serialize(Document doc) throws Exception {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1");
        StringWriter sw = new StringWriter();
        t.transform(new DOMSource(doc), new StreamResult(sw));
        return sw.toString();
    }
}
