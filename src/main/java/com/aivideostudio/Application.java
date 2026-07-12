package com.aivideostudio;

import com.aivideostudio.pipeline.PipelineContext;
import com.aivideostudio.pipeline.PipelineResult;
import com.aivideostudio.pipeline.PipelineRunner;
import com.aivideostudio.pipeline.step.*;
import com.aivideostudio.preview.PreviewGenerator;
import com.aivideostudio.renderer.PreviewRenderer;
import com.aivideostudio.renderer.RendererResult;
import com.aivideostudio.renderer.RendererRunner;
import com.aivideostudio.subtitle.SubtitleRenderer;
import com.aivideostudio.subtitle.SubtitleWriter;

public class Application {
  public static final String VERSION = "1.1.0";

  public static void main(String[] args) throws Exception {
    printBanner();
    PipelineContext context = new PipelineContext();
    PipelineRunner pipelineRunner = new PipelineRunner()
            .add(new ScanAssetStep())
            .add(new LoadCharacterStep())
            .add(new LoadEpisodeStep())
            .add(new ValidateEpisodeStep())
            .add(new BuildWorkspaceStep())
            .add(new GenerateSpeechStep())
            .add(new BuildTimelineStep())
            .add(new BuildManifestStep())
            .add(new LoadCompositionConfigStep())
            .add(new BuildKdenliveStep())
            .add(new RenderStep())
            .add(new PublishCheckStep())
            .add(new ShortsExportStep());
    PipelineResult pipelineResult = pipelineRunner.run(context);
    System.out.println();
    System.out.println("----------------------------");
    System.out.println("Pipeline Finished");
    System.out.println("Success : " + pipelineResult.isSuccess());
    System.out.println("Time : " + pipelineResult.getDuration().toMillis() + " ms");

    RendererRunner rendererRunner = new RendererRunner()
            .add(new SubtitleRenderer(new SubtitleWriter()))
            .add(new PreviewRenderer(new PreviewGenerator()));
    RendererResult rendererResult = rendererRunner.run(context);
    System.out.println();
    System.out.println("--------------------------");
    System.out.println("Renderer Success : " + rendererResult.isSuccess());
    System.out.println("Renderer Time : " + rendererResult.getDuration().toMillis() + " ms");

    /*System.out.println("Scanning assets...\n");
    AssetScanner scanner = new AssetScanner();
    AssetService assets = scanner.scan();
    new AssetValidator().validate(assets);
    System.out.println("Backgrounds");
    for (Asset asset : assets.getBackgrounds().values()) {
      System.out.println(" ✓ " + asset.getId());
    }

    CharacterLoader cloader = new CharacterLoader();
    CharacterRegistry registry = cloader.load(ProjectPaths.CHARACTER_CONFIG);
    CharacterService characterService = new CharacterService(registry);
    System.out.println(characterService.voiceOf("teddy"));
    System.out.println(characterService.defaultPoseOf("teddy"));
    System.out.println(characterService.imageFolderOf("teddy"));
    System.out.println("\nDone.");

    EpisodeLoader loader = new EpisodeLoader();
    Episode episode = loader.load(ProjectPaths.PROJECTS.resolve("episode001").resolve("episode.yml"));
    System.out.println();
    System.out.println("Episode : " + episode.getId());
    System.out.println("Title   : " + episode.getTitle());
    for (Scene scene : episode.getScenes()) {
      System.out.println("--------------------------------");
      System.out.println(scene.getIndex());
      System.out.println(scene.getCharacter());
      System.out.println(scene.getPose());
      System.out.println(scene.getBackground());
      System.out.println(scene.getText());
    }

    EpisodeService episodeService = new EpisodeService();
    episodeService.inherit(episode);
    EpisodeValidator validator = new EpisodeValidator(characterService, assets);
    validator.validate(episode);

    SpeechTaskBuilder builder = new SpeechTaskBuilder(characterService);
    List<SpeechTask> tasks = builder.build(episode, ProjectPaths.OUTPUT.resolve(episode.getId()));
    EdgeTTSService edgeTTSService = new EdgeTTSService();
    edgeTTSService.generate(tasks);

    AudioMetadataReader reader = new AudioMetadataReader();
    TimelineBuilder timelineBuilder = new TimelineBuilder(reader);
    Timeline timeline = timelineBuilder.build(episode, tasks);
    for (TimelineClip clip : timeline.getClips()) {
      System.out.printf(
              "%03d | %.2f -> %.2f | %s%n",
              clip.getIndex(),
              clip.getStartTime(),
              clip.getEndTime(),
              clip.getText()
      );
    }*/

    /*SubtitleRenderer renderer = new SubtitleRenderer();
    List<Subtitle> subtitles = renderer.render(timeline);
    SubtitleWriter swriter = new SubtitleWriter();
    swriter.write(
            ProjectPaths.OUTPUT
                    .resolve(episode.getId())
                    .resolve("subtitle.srt"),
            subtitles
    );*/

    /*RenderManifestBuilder manifestBuilder = new RenderManifestBuilder();
    RenderManifest manifest = manifestBuilder.build(timeline);*/
    /*RenderManifestWriter writer = new RenderManifestWriter();
    writer.write(
            ProjectPaths.OUTPUT
                    .resolve(episode.getId())
                    .resolve("render.json"),
            manifest
    );*/

    /*PreviewGenerator generator = new PreviewGenerator();
    generator.generate(
            ProjectPaths.OUTPUT
                    .resolve(episode.getId()),
            manifest
    );*/

    /*RenderContextBuilder contextBuilder = new RenderContextBuilder();

    Path outputFolder = ProjectPaths.OUTPUT.resolve(episode.getId());

    RenderContext context =
            contextBuilder.build(
                    episode,
                    timeline,
                    manifest,
                    outputFolder
            );

    PreviewGenerator previewGenerator = new PreviewGenerator();
    PreviewRenderer previewRenderer = new PreviewRenderer(previewGenerator);
    SubtitleRenderer subtitleRenderer = new SubtitleRenderer(new SubtitleWriter());
    subtitleRenderer.render(context);*/
  }

  private static void printBanner() {
    System.out.println();
    System.out.println("======================================");
    System.out.println("        AI VIDEO STUDIO");
    System.out.println("======================================");
    System.out.println("Version : " + VERSION);
    System.out.println("Java    : " + System.getProperty("java.version"));
    System.out.println("OS      : " + System.getProperty("os.name"));
    System.out.println("======================================");
    System.out.println();

  }
}