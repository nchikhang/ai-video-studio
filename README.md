# AI Video Studio

AI Video Studio là framework Java giúp tự động tạo video YouTube cho trẻ em.

Mục tiêu cuối cùng:

episode.yml

↓

AI Images

↓

Edge-TTS

↓

Timeline

↓

Subtitle

↓

Kdenlive Project

↓

Open Kdenlive

↓

Export YouTube

---

# Technology

- Java 21
- Maven
- SnakeYAML
- Edge-TTS
- mp3agic
- Kdenlive (MLT)

---

# Current Version

v0.8.0

---

# Project Structure

src/main/java/com/aivideostudio

    asset/
    audio/
    character/
    config/
    episode/
    pipeline/
    preview/
    renderer/
    speech/
    subtitle/
    timeline/
    tts/
    workspace/

---

# Build Pipeline

Application

↓

PipelineRunner

↓

ScanAssetStep

↓

LoadCharacterStep

↓

LoadEpisodeStep

↓

ValidateEpisodeStep

↓

BuildWorkspaceStep

↓

GenerateSpeechStep

↓

BuildTimelineStep

↓

BuildManifestStep

↓

RendererRunner

↓

SubtitleRenderer

↓

PreviewRenderer

---

# Workspace

output/

    episode001/

        audio/

        subtitle/

        preview/

        manifest/

        kdenlive/

        thumbnail/

---

# Current Features

## Asset

- Background Scan
- Character Scan
- Validation

## Character

- Registry
- Voice Mapping
- Default Pose

## Episode

- YAML Loader
- Validator
- Scene Inheritance

## Speech

- SpeechTask Builder
- Edge-TTS Generator

## Audio

- MP3 Duration Reader

## Timeline

- Timeline Builder

## Subtitle

- Subtitle Renderer
- Subtitle Writer

## Preview

- Preview HTML Generator

## Renderer

- Renderer Runner

## Workspace

- Episode Workspace
- Output Folder Management

---

# Roadmap

## v0.1

Bootstrap

✅ Done

---

## v0.2

Character

Asset

Registry

✅ Done

---

## v0.3

Episode

Validation

Scene

✅ Done

---

## v0.4

Speech

Edge-TTS

Timeline

✅ Done

---

## v0.5

Manifest

Subtitle

Preview

✅ Done

---

## v0.6

Pipeline

Workspace

RendererRunner

Refactor

✅ Done

---

## v0.7

Kdenlive Object Model

✅ Done

---

## v0.8

Kdenlive XML Writer + Timeline wiring (episode.yml → openable .kdenlive)

✅ Done

---

## v0.9

Automatic Video Composer

---

## v1.0

One Click Build

episode.yml

↓

video.mp4