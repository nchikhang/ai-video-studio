package com.aivideostudio.kdenlive.model;

public class PlaylistEntry {

    /**
     * producer id
     *
     * ex:
     * producer0
     */
    private String producerId;

    /**
     * frame
     */
    private long inFrame;

    /**
     * frame
     */
    private long outFrame;

    private long timelineFrame;

    public PlaylistEntry() {
    }

    public PlaylistEntry(String producerId,
                         long inFrame,
                         long outFrame) {

        this.producerId = producerId;
        this.inFrame = inFrame;
        this.outFrame = outFrame;

    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public long getInFrame() {
        return inFrame;
    }

    public void setInFrame(long inFrame) {
        this.inFrame = inFrame;
    }

    public long getOutFrame() {
        return outFrame;
    }

    public void setOutFrame(long outFrame) {
        this.outFrame = outFrame;
    }

    public long getDuration() {
        return outFrame - inFrame + 1;
    }

    @Override
    public String toString() {

        return "Entry{" +
                "producer='" + producerId + '\'' +
                ", in=" + inFrame +
                ", out=" + outFrame +
                '}';

    }

    public long getTimelineFrame() {
        return timelineFrame;
    }

    public void setTimelineFrame(long timelineFrame) {
        this.timelineFrame = timelineFrame;
    }
}