# ⚡ God Trident

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white">
  <img src="https://img.shields.io/badge/Paper-1.21.11-00AA00?style=for-the-badge">
  <img src="https://img.shields.io/badge/Minecraft-1.21.11-62B47A?style=for-the-badge">
</p>

A Paper plugin that adds a craftable **God Trident** with a custom 3D model and a set of powerful abilities.

## Abilities

| Ability | Description |
|---------|-------------|
| **Air Riptide** | Hold right-click to charge, release to launch in the direction you're facing. Works on land and in the air. |
| **Smash Lightning** | Hitting an entity during a riptide spin deals heavy damage and strikes them with lightning. |
| **Crit Lightning** | Landing a critical hit (airborne and falling) on any entity calls down a lightning strike. |
| **Fall Immunity** | No fall damage while holding the God Trident in your main hand. |

## Requirements

- [Paper](https://papermc.io/downloads) 1.21.11+
- Java 21

## Installation

1. Drop `GodTrident.jar` into your server's `plugins/` folder.
2. Set up the resource pack (required for the 3D model, see below).
3. Restart the server.

## Resource Pack

The God Trident uses custom model data ID `10000`. Without the resource pack it will appear as a regular trident.

Add these lines to `server.properties`:

```properties
require-resource-pack=true
resource-pack=https\://minepack.fr/pack/7246787d-d7d9-4316-8149-3888fc932913
resource-pack-id=7246787d-d7d9-4316-8149-3888fc932913
resource-pack-sha1=4c6d87690ad5d00d8ea7714915ace4fd6d983994
```

> The backslash before `://` is required by the `server.properties` format.

## Crafting Recipe

<img width="956" height="451" alt="god trident recipe" src="https://github.com/user-attachments/assets/df0e06c6-8ee4-4e39-b2cd-838ad875c2d4" />

## Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/givegodtrident` | Gives the God Trident to yourself | `godtrident.give` |
| `/givegodtrident <player>` | Gives the God Trident to a player | `godtrident.give` |

`godtrident.give` defaults to **op**. Tab completion is supported for the player argument.

## Configuration

Generated at `plugins/GodTrident/config.yml` on first run:

```yaml
# Damage dealt to entities hit during a riptide spin (in half-hearts)
smash-damage: 20.0

# Base attack damage of the God Trident in main hand (in half-hearts)
trident-base-damage: 10.0
```

Restart the server to apply config changes. `/reload` is not supported.

## Building from Source

Requires JDK 21. The Paper dev bundle is downloaded automatically on first build.

```bash
./gradlew build
```

Output: `build/libs/GodTrident-<version>.jar`

## Author

**Glatinis**
