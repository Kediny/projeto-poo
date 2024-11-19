# Changelog

## [1.1.0] - 2024-11-19
*Major Features & Refactoring*

### Added:
- **Room Grid System**: Refactored `Room.java` to implement a 2D grid (`roomGrid`) to represent the room layout based on `room.txt` files.
- **Boundary Detection**: Implemented boundary checking for player movement to prevent moving out of the room boundaries.
- **Wall Collision Detection**: Implemented checks to prevent the player from moving into walls or out of bounds, ensuring Manel (the player) stays within the grid.
- **Hero Spawn Point Refactor**: Modified the `findSpawnPoint` function to search for the hero spawn point (`'H'`) in the grid instead of reading the file directly, ensuring proper initialization.
- **Room Loading Refactor**: Optimized the room loading process to ensure that all elements, including walls, doors, and other objects, are correctly loaded based on the room’s configuration file.
  
### Changed:
- **Room Initialization Flow**: Rearranged the initialization of `heroStartingPosition` and `roomGrid` to ensure proper object placement and spawn point detection after loading the room.
- **Movement Logic**: Simplified movement logic for Manel by using the `Direction` enum for navigation, ensuring a clean method for handling all possible directions.

### Fixed:
- **Hero Positioning**: Fixed bug where the hero (`'H'`) wasn't correctly detected on the grid due to the initial empty state of `roomGrid`.
- **Grid Boundary Errors**: Corrected an issue where the player could potentially move outside the grid boundaries, even though checks were in place.
  
### Removed:
- **Redundant File Parsing**: Removed unnecessary file-based operations in the `findSpawnPoint` method, opting for a more efficient grid-based search.

---

## [1.0.0] - 2024-11-18
*Initial Setup*

### Added:
- **Room Parsing Logic**: Implemented initial functionality to load and parse `room.txt` files.
- **Tile Rendering**: Added basic rendering logic for tiles (floor, walls, doors, etc.) within the game world.
- **Hero Starting Position**: Set up hero's starting position (`'H'`) in the room configuration file.

---
