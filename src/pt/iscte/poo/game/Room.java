package pt.iscte.poo.game;

import objects.*;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Room {

	private static Room instance;
	private Point2D heroStartingPosition;
	private Manel manel;
	private String nextRoom = null; // Store the next room file name
	private static final int MAX_X = 9; // x max coordinate
	private static final int MAX_Y = 9; // y max coordinate
	
	// Getters for MAX_X and MAX_Y
	public static int getMaxX() {
	    return MAX_X;
	}

	public static int getMaxY() {
	    return MAX_Y;
	}

	private char[][] roomGrid; // Store the room layout as a 2D array

	// Constructor that takes the room file name as an argument
	public Room(String roomFileName) {
		instance = this;
		loadRoom(roomFileName); // Load the room based on the provided file
	}

	// Constructor that defaults to a hardcoded room (e.g., "room0.txt")
	public Room() {
		instance = this;
		loadRoom("room0.txt"); // Default room if no argument is provided
	}

	public static Room getInstance() {
		return instance;
	}

	// Load the room from the specified file
	private void loadRoom(String fileName) {
	    String filePath = "rooms/" + fileName; // Path to the room file
	    roomGrid = new char[MAX_Y + 1][MAX_X + 1]; // Initialize the grid

	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        int row = 0;

	        // Read the first line
	        line = reader.readLine();
	        if (line != null && line.startsWith("#0;")) {
	            nextRoom = line.split(";")[1].trim(); // Extract the next room filename
	            line = reader.readLine(); // Move to the next line for grid processing
	        } else {
	            nextRoom = null; // No next room directive
	        }

	        // Process the room file lines to populate the grid and render objects
	        while (line != null) {
	            processLine(line, row++);
	            line = reader.readLine();
	        }

	        // After the grid is populated, now find the hero's starting position ('H')
	        heroStartingPosition = findSpawnPoint();

	        // Initialize Manel at the heroStartingPosition and render it
	        manel = new Manel(heroStartingPosition);
	        ImageGUI.getInstance().addImage(manel);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	// Process each line of the room file
	private void processLine(String line, int row) {
		// Populate the roomGrid first
		for (int col = 0; col < line.length(); col++) {
			roomGrid[row][col] = line.charAt(col); // Store the tile in the grid
			Point2D position = new Point2D(col, row);
			ImageGUI.getInstance().addImage(new Floor(position)); // Render the floor tile first
		}

		// Now process the actual tiles, skipping the space (' ') tiles
		for (int col = 0; col < line.length(); col++) {
			char tile = line.charAt(col);
			Point2D position = new Point2D(col, row);

			// If the tile is a space, skip it (because floor tile is already rendered)
			if (tile == ' ') {
				continue;
			}

			// Otherwise, render the appropriate object for this tile
			createObjectFromTile(tile, position);
		}
	}

	// Create objects based on the tile type
	private void createObjectFromTile(char tile, Point2D position) {
		switch (tile) {
		case 'W':
			ImageGUI.getInstance().addImage(new Wall(position));
			break;
		case '0':
			ImageGUI.getInstance().addImage(new DoorClosed(position));
			break;
		case 'G':
			ImageGUI.getInstance().addImage(new DonkeyKong(position));
			break;
		case 'S':
			ImageGUI.getInstance().addImage(new Stairs(position));
			break;
		case 's':
			ImageGUI.getInstance().addImage(new Sword(position));
			break;
		case 't':
			ImageGUI.getInstance().addImage(new Trap(position));
			break;
		case 'm':
			ImageGUI.getInstance().addImage(new GoodMeat(position));
			break;
		case 'b':
			ImageGUI.getInstance().addImage(new Fire(position));
			break;
		case 'P': // Added case for Princess
			ImageGUI.getInstance().addImage(new Princess(position)); // Create Princess object for 'P' tile
			break;
		case 'H':
			break;
		default:
			System.out.println("Unknown tile: " + tile);
		}
	}

	// Method to get the next room to load
	public String getNextRoom() {
		return nextRoom;
	}

	// Method to get the starting position of the hero ('Manel')
	public Point2D getHeroStartingPosition() {
		return heroStartingPosition;
	}

	// Method to check if a position is within the room's fixed grid boundaries (0
	// to 9 for both x and y)
	public boolean isWithinRoom(Point2D position) {
		return position.getX() >= 0 && position.getX() <= MAX_X && position.getY() >= 0 && position.getY() <= MAX_Y;
	}

	// Separate function to find the spawn point ('H'), starting from the bottom of
	// the room file
	private Point2D findSpawnPoint() {
		// Search the roomGrid for the 'H' character
		for (int row = MAX_Y; row >= 0; row--) { // Search from bottom to top
			for (int col = 0; col <= MAX_X; col++) {
				if (roomGrid[row][col] == 'H') { // Look for the 'H' (hero) tile
					return new Point2D(col, row); // Return the found position
				}
			}
		}
		// If no 'H' is found, return null or a default position
		return null;
	}

	public void moveManel(Direction dir) {
		Point2D currentPosition = manel.getPosition();
		Point2D futurePosition = Movement.tryMove(currentPosition, dir);
		
		// Check if Manel moves onto a door
	    if (!futurePosition.equals(currentPosition) && isDoor(futurePosition)) {
	        nextRoom(); // Handle room transition
	        return; // Exit movement to prevent further updates
	    }

		// Handle horizontal wrapping at boundaries
		if (dir == Direction.LEFT && futurePosition.getX() < 0) {
			// Wrap to the right side of the grid
			Point2D oppositePosition = new Point2D(MAX_X, currentPosition.getY());
			if (isWalkable(oppositePosition)) {
				futurePosition = oppositePosition; // Allow wrapping
			}
		} else if (dir == Direction.RIGHT && futurePosition.getX() > MAX_X) {
			// Wrap to the left side of the grid
			Point2D oppositePosition = new Point2D(0, currentPosition.getY());
			if (isWalkable(oppositePosition)) {
				futurePosition = oppositePosition; // Allow wrapping
			}
		}

		// If the position changes, update Manel's position
		if (!futurePosition.equals(currentPosition)) {
			manel.setPosition(futurePosition); // Update Manel's position directly
			manel.updatePosition(); // Refresh the GUI
		}
	}

	// Helper method to determine if a position is walkable
	public boolean isWalkable(Point2D position) {
	    return isWithinRoom(position) && !isWall(position) && !isFloor(position);
	}
	
	private boolean isFloor(Point2D position) {
	    return roomGrid[position.getY()][position.getX()] == 'F'; // Assuming 'F' is floor
	}

	// Method to check if a given position is a wall ('W') in the room grid
	public boolean isWall(Point2D position) {
		if (!isWithinRoom(position))
			return false; // Prevent out-of-bounds access
		return roomGrid[position.getY()][position.getX()] == 'W';
	}
	
	private boolean isStairs(Point2D position) {
	    if (!isWithinRoom(position)) return false; // Prevent out-of-bounds errors
	    return roomGrid[position.getY()][position.getX()] == 'S'; // Assuming 'S' is the stairs tile
	}
	
	private boolean isDoor(Point2D position) {
	    if (!isWithinRoom(position)) return false; // Prevent out-of-bounds access
	    char tile = roomGrid[position.getY()][position.getX()];
	    return tile == '0'; // Assuming '0' represents a door
	}

	
	public void tick() {
	    applyGravity(manel); // Apply gravity to the player character
	}

	private void applyGravity(Manel manel) {
		Vector2D v = new Vector2D(0,1);
	    Point2D below = manel.getPosition().plus(v); // Check the position below Manel
	    if (isStairs(manel.getPosition()) || isStairs(below)) {
	        return; // Do nothing if on stairs or above stairs
	    }
	    if (isWithinRoom(below) && isWalkable(below)) {
	        manel.setPosition(below); // Move Manel down
	        manel.updatePosition(); // Update the GUI to reflect the new position
	    }
	}
	
	private void nextRoom() {
	    if (nextRoom == null || nextRoom.isEmpty()) {
	        System.out.println("No next room defined! Transition aborted.");
	        return;
	    }

	    System.out.println("Changing to room: " + nextRoom);

	    // Clear the current room from the GUI
	    ImageGUI.getInstance().clearImages();

	    // Load the new room
	    loadRoom(nextRoom);

	    // Center Manel in the new room
	    manel.setPosition(heroStartingPosition);
	    manel.updatePosition();
	}

}
