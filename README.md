# Changelog  

## [0.3.0] - 2024-11-19  
*Horizontal Wrapping Fixes, Donkey Kong Movement, and Room File Robustness*  

### Added:  
- **Horizontal Wrapping Logic**: Implemented robust horizontal wrapping mechanics for player movement at room boundaries. Players can now seamlessly wrap to the opposite side unless blocked by walls.  
- **Room Loading Flexibility**: Enhanced room file parsing to handle cases where the `#0;` directive (next room information) is missing. The game now processes room files with or without the directive seamlessly.  
- **Donkey Kong Movement**: Introduced basic movement functionality for the Donkey Kong character, allowing it to act autonomously within the game grid.  
- **Movement.java Utility Class**: Added the `Movement` class to encapsulate movement logic for entities, including boundary checking and wall collision detection.  
- **Code Refactoring**: Improved overall code structure, reducing redundancy and ensuring reusability across movement and room parsing systems.  
- **Changelog Refactoring**: Updated the changelog format for consistency and clarity, retroactively adjusting previous versions to match the revised format.  

### Fixed:  
- **Boundary Wrapping Bugs**: Resolved a bug where horizontal wrapping wasn't functioning correctly due to position-checking logic using `currentPosition` instead of `newPosition`.  
- **Room Parsing Error**: Fixed a critical issue where `room2.txt` and other files missing the `#0;` directive failed to load properly. The `loadRoom` method now treats such files correctly.  

---

## [0.2.0] - 2024-11-19  
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

## [0.1.0] - 2024-11-18  
*Initial Setup*  

### Added:  
- **Room Parsing Logic**: Implemented initial functionality to load and parse `room.txt` files.  
- **Tile Rendering**: Added basic rendering logic for tiles (floor, walls, doors, etc.) within the game world.  
- **Hero Starting Position**: Set up hero's starting position (`'H'`) in the room configuration file.  

---
