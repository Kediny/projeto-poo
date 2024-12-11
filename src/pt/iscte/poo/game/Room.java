package pt.iscte.poo.game;

import objects.*;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Room {

	private static Room instance;
	private Point2D heroStartingPosition;
	private Manel manel;
	private String nextRoom = null;
	private static final int MAX_X = 9;
	private static final int MAX_Y = 9;
	private ArrayList<Interactible> interactibles;
	private int roomTickCounter = 0;

	public static int getMaxX() {
	    return MAX_X;
	}

	public static int getMaxY() {
	    return MAX_Y;
	}

	private char[][] roomGrid;

	public Room(String roomFileName) {
		instance = this;
		loadRoom(roomFileName);
	}

	public Room() {
		instance = this;
		loadRoom("room0.txt");
	}

	public static Room getInstance() {
		return instance;
	}

	private void loadRoom(String fileName) {
	    String filePath = "rooms/" + fileName;
	    interactibles = new ArrayList<Interactible>();
	    roomGrid = new char[MAX_Y + 1][MAX_X + 1];

	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        int row = 0;

	        line = reader.readLine();
	        if (line != null && line.startsWith("#0;")) {
	            nextRoom = line.split(";")[1].trim();
	            line = reader.readLine();
	        } else {
	            nextRoom = null;
	        }

	        while (line != null) {
	            processLine(line, row++);
	            line = reader.readLine();
	        }

	        heroStartingPosition = findSpawnPoint();
	        manel = new Manel(heroStartingPosition);
	        ImageGUI.getInstance().addImage(manel);
			roomTickCounter = 0;

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	private void processLine(String line, int row) {
		for (int col = 0; col < line.length(); col++) {
			roomGrid[row][col] = line.charAt(col);
			Point2D position = new Point2D(col, row);
			ImageGUI.getInstance().addImage(new Floor(position));
		}

		for (int col = 0; col < line.length(); col++) {
			char tile = line.charAt(col);
			Point2D position = new Point2D(col, row);

			if (tile == ' ') {
				continue;
			}

			createObjectFromTile(tile, position);
		}
	}

	private void createObjectFromTile(char tile, Point2D position) {
		switch (tile) {
		case 'W':
			ImageGUI.getInstance().addImage(new Wall(position));
			break;
		case '0':
			ImageGUI.getInstance().addImage(new DoorClosed(position));
			break;
		case 'G':
			DonkeyKong dk = new DonkeyKong(position);
			ImageGUI.getInstance().addImage(dk);
			//interactibles.add(dk); IMPLEMENT INTERACTIBLE IN DONKEYKONG CLASS
			break;
		case 'B':
			Bat bat = new Bat(position);
			ImageGUI.getInstance().addImage(bat);
			//interactibles.add(bat); IMPLEMENT INTERACTIBLE IN BAT CLASS
			break;
		case 'S':
			ImageGUI.getInstance().addImage(new Stairs(position));
			break;
		case 's':
			Sword sword = new Sword(position);
			ImageGUI.getInstance().addImage(sword);
			interactibles.add(sword);
			break;
		case 't':
			ImageGUI.getInstance().addImage(new Trap(position));
			break;
		case 'm':
			GoodMeat meat = new GoodMeat(position);
			ImageGUI.getInstance().addImage(meat);
			interactibles.add(meat);
			break;
		case 'b':
			Bomb bomb = new Bomb(position);
			ImageGUI.getInstance().addImage(bomb);
			interactibles.add(bomb);
			break;
		case 'P':
			ImageGUI.getInstance().addImage(new Princess(position));
			break;
		case 'H':
			break;
		default:
			System.out.println("Unknown tile: " + tile);
		}
	}

	public String getNextRoom() {
		return nextRoom;
	}

	public Point2D getHeroStartingPosition() {
		return heroStartingPosition;
	}

	public boolean isWithinRoom(Point2D position) {
		return position.getX() >= 0 && position.getX() <= MAX_X && position.getY() >= 0 && position.getY() <= MAX_Y;
	}

	private Point2D findSpawnPoint() {
		for (int row = MAX_Y; row >= 0; row--) {
			for (int col = 0; col <= MAX_X; col++) {
				if (roomGrid[row][col] == 'H') {
					return new Point2D(col, row);
				}
			}
		}
		return null;
	}

	public void moveManel(Direction dir) {
		Point2D currentPosition = manel.getPosition();
		Point2D futurePosition = Movement.tryMove(currentPosition, dir);

	    if (!futurePosition.equals(currentPosition) && isDoor(futurePosition)) {
	        nextRoom();
	        return;
	    }

		if (!futurePosition.equals(currentPosition)) {
			manel.setPosition(futurePosition);
			for(Interactible interactible : interactibles) {
				if(interactible.getPosition().equals(futurePosition)) {
					interactible.interaction();
					break;
				}
			}
			manel.updatePosition();
		}
	}

	public boolean isWalkable(Point2D position) {
	    return isWithinRoom(position) && !isWall(position) && !isFloor(position);
	}
	
	private boolean isFloor(Point2D position) {
	    return roomGrid[position.getY()][position.getX()] == 'F';
	}

	public boolean isWall(Point2D position) {
		if (!isWithinRoom(position))
			return false;
		return roomGrid[position.getY()][position.getX()] == 'W';
	}
	
	private boolean isStairs(Point2D position) {
	    if (!isWithinRoom(position)) return false;
	    return roomGrid[position.getY()][position.getX()] == 'S';
	}
	
	public boolean isTrap(Point2D position) {
		if (!isWithinRoom(position))
			return false;
		return roomGrid[position.getY()][position.getX()] == 't';
	}
	
	private boolean isDoor(Point2D position) {
	    if (!isWithinRoom(position)) return false;
	    char tile = roomGrid[position.getY()][position.getX()];
	    return tile == '0';
	}
	
	public void tick() {
//		applyGravity(manel);
		System.out.println("Room tick: " + roomTickCounter);
		roomTickCounter++;
		
		
	}

	public void applyGravity(Manel manel) {
		Vector2D v = new Vector2D(0,1);
	    Point2D below = manel.getPosition().plus(v);
	    if (isStairs(manel.getPosition()) || isStairs(below)) {
	        return;
	    }
	    if (isWithinRoom(below) && isWalkable(below)) {
	        manel.setPosition(below);
	        manel.updatePosition();
	    }
	}
	
	private void nextRoom() {
	    if (nextRoom == null || nextRoom.isEmpty()) {
	        System.out.println("No next room defined! Transition aborted.");
	        return;
	    }
	    System.out.println("Changing to room: " + nextRoom);
	    ImageGUI.getInstance().clearImages();
	    loadRoom(nextRoom);
	    manel.setPosition(heroStartingPosition);
	    manel.updatePosition();
	}
	
	public int getRoomTickCounter() {
		return roomTickCounter;
	}
	
	public ArrayList<Interactible> getInteractibles() {
		return interactibles;
	}
	
	public void addInteractible(Interactible i) {
		interactibles.add(i);
	}
	
	public void removeInteractible(Interactible i) {
		interactibles.remove(i);
	}
	
	public Manel getManel() {
		return manel;
	}

}