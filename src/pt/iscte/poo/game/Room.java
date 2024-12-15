package pt.iscte.poo.game;

import objects.*;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Room {

    // Attributes
    private static Room instance;
    private static final int MAX_X = 9;
    private static final int MAX_Y = 9;
    private int roomTickCounter = 0;
    private Point2D heroStartingPosition;
    private Player player;
    private String nextRoom = null;
    private String currentRoom = null;
    private ArrayList<Interactible> interactibles;
    private char[][] roomGrid;

    // Constructors
    private Room(String roomFileName) {
        instance = this;
        loadRoom(roomFileName);
    }

    private Room() {
        instance = this;
        loadRoom("room0.txt");
    }

    // Setters and getters
    public static Room getInstance() {
        if (Room.instance == null) {
            Room.instance = new Room();
        }
        return Room.instance;
    }

    public static void killInstance() {
        Room.instance = null;
    }

    public String getNextRoom() {
        return nextRoom;
    }

    public Point2D getHeroStartingPosition() {
        return heroStartingPosition;
    }

    public int getRoomTickCounter() {
        return roomTickCounter;
    }

    public ArrayList<Interactible> getInteractibles() {
        return interactibles;
    }

    public Player getPlayer() {
        return player;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    // Functions
    public void loadRoom(String fileName) {
        currentRoom = fileName;
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
            
            if (row <= MAX_Y) {
                System.out.println("Error: Missing expected line in room file. Aborting game...");
                ImageGUI.getInstance().dispose();
                System.exit(1);
            }

            heroStartingPosition = findSpawnPoint();
            player = Player.getInstance();
            player.setPosition(heroStartingPosition);
            ImageGUI.getInstance().addImage(player);
            roomTickCounter = 0;

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            System.out.print("Please enter a valid room file name: ");
            Scanner sc = new Scanner(System.in);
            String newFileName = sc.nextLine();
            loadRoom(newFileName);
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void processLine(String line, int row) {
    	if (line.isEmpty()) {
            System.out.println("Error: Line " + row + " in the room file is empty. Aborting game...");
            ImageGUI.getInstance().dispose();
            System.exit(1);
        }
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
        	case 'H': break;
            case 'W': ImageGUI.getInstance().addImage(new Wall(position)); break;
            case '0': ImageGUI.getInstance().addImage(new DoorClosed(position)); break;
            case 'S': ImageGUI.getInstance().addImage(new Stairs(position)); break;
            case 'G':
                DonkeyKong dk = new DonkeyKong(position);
                ImageGUI.getInstance().addImage(dk);
                interactibles.add(dk);
                break;
            case 'B':
                Bat bat = new Bat(position);
                ImageGUI.getInstance().addImage(bat);
                interactibles.add(bat);
                break;
            case 's':
                Sword sword = new Sword(position);
                ImageGUI.getInstance().addImage(sword);
                interactibles.add(sword);
                break;
            case 't':
                Trap trap = new Trap(position);
                ImageGUI.getInstance().addImage(new Trap(position));
                interactibles.add(trap);
                break;
            case 'h':
                HiddenTrap hTrap = new HiddenTrap(position);
                ImageGUI.getInstance().addImage(new Wall(position));
                interactibles.add(hTrap);
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
                Princess princess = new Princess(position);
                ImageGUI.getInstance().addImage(princess);
                interactibles.add(princess);
                break;
            default:
            	System.out.println("Warning: Unrecognized tile '" + tile + "' at position (" + position.getX() + ", " + position.getY() + "). Defaulting to Floor tile.");
                ImageGUI.getInstance().addImage(new Floor(position));
        }
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

    public boolean isWalkable(Point2D position) {
        return isWithinRoom(position) && !isWall(position) && !isFloor(position);
    }

    public boolean isFloor(Point2D position) {
        return roomGrid[position.getY()][position.getX()] == 'F';
    }

    public boolean isWall(Point2D position) {
        if (!isWithinRoom(position)) return false;
        return roomGrid[position.getY()][position.getX()] == 'W';
    }

    public boolean isStairs(Point2D position) {
        if (!isWithinRoom(position)) return false;
        return roomGrid[position.getY()][position.getX()] == 'S';
    }

    public boolean isTrap(Point2D position) {
        if (!isWithinRoom(position)) return false;
        return roomGrid[position.getY()][position.getX()] == 't';
    }

    public boolean isDoor(Point2D position) {
        if (!isWithinRoom(position)) return false;
        char tile = roomGrid[position.getY()][position.getX()];
        return tile == '0';
    }

    public boolean isPrincess(Point2D position) {
        if (!isWithinRoom(position)) return false;
        return roomGrid[position.getY()][position.getX()] == 'P';
    }

    public void tick() {
        roomTickCounter++;
        if (roomTickCounter == 10) {
            updateGoodMeatToBadMeat();
        }

        for (Interactible interactible : new ArrayList<>(interactibles)) {
            if (interactible instanceof Banana) {
                Banana banana = (Banana) interactible;
                banana.applyGravity();
            } else if (interactible instanceof Bomb) {
                Bomb bomb = (Bomb) interactible;
                if(Player.getInstance().bombDropped) { bomb.tick(); }
            }
        } 
        
    }

    private void updateGoodMeatToBadMeat() {
        for (Interactible interactible : new ArrayList<>(interactibles)) {
            if (interactible instanceof GoodMeat) {
                GoodMeat goodMeat = (GoodMeat) interactible;
                Point2D position = goodMeat.getPosition();
                ImageGUI.getInstance().removeImage(goodMeat);
                interactibles.remove(goodMeat);
                BadMeat badMeat = new BadMeat(position);
                ImageGUI.getInstance().addImage(badMeat);
                interactibles.add(badMeat);
            }
        }
    }

    public void nextRoom(Point2D openDoorPosition) {
        ImageGUI.getInstance().addImage(new DoorOpen(openDoorPosition));
        ImageGUI.getInstance().update();
        player.setPosition(openDoorPosition);
        player.update();
        GameEngine.sleep(250);
        Status.getInstance().setDirtyFlag(false);
        Status.getInstance().printStatus("Entering next room.........");
        Status.getInstance().printStatus("Entering next room..................");
        Status.getInstance().printStatus("Entering next room...........................");
        if (nextRoom == null || nextRoom.isEmpty()) {
            System.out.println("No next room defined! Transition aborted.");
            return;
        }
        spawnRoom(nextRoom);
    }

    public void spawnRoom(String room) {
        ImageGUI.getInstance().clearImages();
        loadRoom(room);
        player.setPosition(heroStartingPosition);
        player.update();
    }

    public void reloadRoom(String room) {
        player.setPosition(heroStartingPosition);
        player.update();
    }

    public void addInteractible(Interactible i) {
        interactibles.add(i);
    }

    public void removeInteractible(Interactible i) {
        interactibles.remove(i);
    }
}