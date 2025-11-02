# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**RogueNights (Arrrknights)** is a modified version of Shattered Pixel Dungeon, which itself is based on the original Pixel Dungeon by Watabou. This is a roguelike RPG with randomly generated levels, items, enemies, and traps, built on LibGDX for cross-platform support (Android and Desktop).

- **Package**: `com.shatteredpixel.tomorrowpixel`
- **Version**: 0.5.0-B4 (build 583)
- **License**: GPLv3
- **Language**: Java
- **Build System**: Gradle with multi-module setup
- **Platforms**: Android (min SDK 21, target SDK 35) and Desktop (via LibGDX/LWJGL3)

## Build Commands

### Desktop Development
```bash
./gradlew desktop:debug           # Run game in debug mode
./gradlew desktop:release         # Build release JAR -> /desktop/build/libs
```

### Android Development
```bash
./gradlew android:assembleDebug   # Build debug APK
./gradlew android:assembleRelease # Build release APK with R8 optimization
./gradlew copyAndroidNatives      # Extract native libraries for Android
```

### General
```bash
./gradlew clean                   # Clean all build artifacts
./gradlew build                   # Build all modules
./gradlew tasks                   # List all available Gradle tasks
```

## Architecture

### Multi-Module Gradle Structure

The project is organized into several Gradle modules:

1. **core/** - Platform-independent game logic (main codebase)
   - All game actors, items, levels, scenes, and mechanics
   - Assets: sprites, sounds, music, fonts, localization files
   - Package: `com.shatteredpixel.shatteredpixeldungeon`

2. **SPD-classes/** - Base LibGDX/Noosa framework
   - Low-level rendering engine and utilities
   - Package: `com.watabou.*`
   - Contains: glscripts, gltextures, glwrap, input, noosa, utils

3. **android/** - Android platform launcher and configuration
   - Android-specific implementations
   - ProGuard/R8 rules for code optimization

4. **desktop/** - Desktop platform launcher
   - Separate debug and release source sets
   - LWJGL3 backend for desktop rendering

5. **services/** - Modular service implementations
   - Update checkers: `updates/debugUpdates/`, `updates/githubUpdates/`
   - News feeds: `news/debugNews/`, `news/shatteredNews/`

### Core Architecture Patterns

#### Scene-Based Flow
The game uses a scene-based architecture where different screens are managed as scenes:
- **TomorrowRogueNight.java** - Main game class (LibGDX `Game` subclass)
- **Dungeon.java** - Core dungeon state manager
- Scenes in `com.shatteredpixel.shatteredpixeldungeon.scenes/`:
  - TitleScene, GameScene, InterlevelScene, AmuletScene
  - AlchemyScene, BadgesScene, ChangesScene, RankingsScene

#### Actor System
Entity-Component pattern for all game objects:
- **Actor.java** - Base class for all game entities with turn-based actions
- **Char.java** - Character base class
- **actors/hero/** - Player character implementation
- **actors/mobs/** - Enemy and NPC implementations
- **actors/buffs/** - Status effects and temporary modifiers
- **actors/blobs/** - Area-of-effect entities

#### Procedural Level Generation
- **levels/** - Level generation and management
  - Area types: SewerLevel, PrisonLevel, CavesLevel, CityLevel, HallsLevel
  - Boss levels: SeaBossLevel1/2, GavialBossLevel1/2, NewPrisonBossLevel
  - **levels/builders/** - Room layout algorithms
  - **levels/features/** - Level feature implementations (traps, doors, etc.)

#### Item System
Extensive item categorization in `items/`:
- Standard categories: armor, weapons, artifacts, potions, scrolls, wands, rings
- Custom categories: Gunaccessories/, Skill/, NewGameItem/
- **Generator.java** - Procedural item generation and loot tables

### Key Files

- **core/src/main/java/com/shatteredpixel/shatteredpixeldungeon/TomorrowRogueNight.java:1** - Application entry point
- **core/src/main/java/com/shatteredpixel/shatteredpixeldungeon/Dungeon.java:1** - Central game state
- **core/src/main/java/com/shatteredpixel/shatteredpixeldungeon/actors/Actor.java:1** - Base actor class
- **core/src/main/java/com/shatteredpixel/shatteredpixeldungeon/items/Generator.java:1** - Item generation system
- **build.gradle:1** - Root build configuration with version numbers

## Assets

Located in **core/src/main/assets/**:
- **sprites/** - Character and object sprites
- **environment/** - Environment sprites and custom tiles
- **interfaces/** - UI elements
- **music/** - Background music files
- **sounds/** - Sound effects
- **messages/** - Localization files (.properties format)
  - Organized by category: actors, items, levels, scenes, ui, windows
  - Translation project: https://www.transifex.com/team-rosemari/tomorrows-roguenight/
- **fonts/** - Game fonts
- **splashes/** - Splash screens
- **effects/** - Visual effect assets

## Development Environment

- **IDE**: Android Studio (recommended) or IntelliJ IDEA
- **JDK**: Java 11
- **LibGDX**: 1.13.5
- **Gradle**: Wrapper included (gradlew/gradlew.bat)

Configuration files:
- **.idea/** - IDE project settings
- **gradle.properties** - JVM args: `-Xmx2048m -XX:MaxMetaspaceSize=512m`, parallel builds enabled
- **android/proguard-rules.pro** - R8/ProGuard configuration for release builds

## Custom Features in This Mod

This fork includes several custom additions:
- Custom characters: Jessica, Ceylon, FrostLeaf, NPC_Phantom
- Custom boss encounters: SeaBossLevel (phases 1-2), GavialBossLevel (phases 1-2)
- Custom areas: RhodesLevel, SiestaLevel
- Custom items: Gunaccessories system, Skills system
- Custom weapons: Ots03, Beowulf

## Code Organization

Main package structure within `com.shatteredpixel.shatteredpixeldungeon`:
- **actors/** - All game entities (player, enemies, buffs, blobs)
- **effects/** - Visual effects and particles
- **items/** - All game items and equipment
- **journal/** - In-game journal and quest tracking
- **levels/** - Level generation, builders, and features
- **mechanics/** - Game mechanics and algorithms
- **messages/** - i18n support classes
- **plants/** - Plant/herb system
- **scenes/** - Game screens and UI flows
- **services/** - External services (updates, news)
- **sprites/** - Sprite management and rendering
- **tiles/** - Tilemap rendering
- **ui/** - Reusable UI components
- **utils/** - Utility classes
- **windows/** - In-game dialog windows

## Important Notes

- **No Pull Requests**: This repository does not accept PRs. Code is provided for reference and modding.
- **License**: GPLv3 - any modifications must be open-sourced if distributed.
- **Localization**: Properties-based system in assets/messages/
- **ProGuard**: R8 full mode is disabled in gradle.properties for better debugging; enabled for release builds via android/proguard-rules.pro
- **Documentation**: See `/docs` for compilation guides and recommended changes for creating mods