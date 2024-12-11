package objects;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.game.Movement;
import pt.iscte.poo.game.Player;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bat implements ImageTile {
	
	private Point2D position;
	private Timer movementTimer;
    
    private Player player;

    public Bat(Point2D position) {
        this.position = position;
        startRandomMovement();
    }
    
 // Start the random movement timer
    private void startRandomMovement() {
        movementTimer = new Timer();
        movementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moveRandomly();
            }
        }, 0, 500); // Execute immediately and repeat every 500 ms
    }

    // Stop the movement timer
    public void stopMovement() {
        if (movementTimer != null) {
            movementTimer.cancel();
        }
    }

    // Attempt random horizontal movement
    private void moveRandomly() {
        Direction randomDir = getRandomDirection(); // Get a horizontal direction
        Point2D newPosition = Movement.tryMove(position, randomDir);
        if (!newPosition.equals(position)) { // Only update if a valid move was made
            position = newPosition;
        }
    }

    // Get a random horizontal direction (LEFT or RIGHT)
    private Direction getRandomDirection() {
        Direction[] directions = {Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN};
        return directions[new Random().nextInt(directions.length)];
    }
    
    @Override
    public String getName() {
        return "Bat";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
    
//    @Override
//    public void interaction() {
//    	player = Player.getInstance();
//    	ImageGUI.getInstance().removeImage(this);
//		player.takeDamageBat();
//		Room.getInstance().getInteractibles().remove(this);
//    }
}