package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.game.Movement;
import pt.iscte.poo.game.Room;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class DonkeyKong implements ImageTile {

    private Point2D position;
    private Timer movementTimer;

    public DonkeyKong(Point2D position) {
        this.position = position;
        startRandomMovement();
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    private void startRandomMovement() {
        movementTimer = new Timer();
        movementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moveRandomly();
            }
        }, 0, 500);
    }

    public void stopMovement() {
        if (movementTimer != null) {
            movementTimer.cancel();
        }
    }

    // Attempt random horizontal movement
    private void moveRandomly() {
        Direction randomDir = getRandomHorizontalDirection(); // Get a horizontal direction
        Point2D newPosition = Movement.tryMove(position, randomDir);
        if (!newPosition.equals(position) && !Room.getInstance().isDoor(newPosition)) { // Only update if a valid move was made
            position = newPosition;
        }
    }

    // Get a random horizontal direction (LEFT or RIGHT)
    private Direction getRandomHorizontalDirection() {
        Direction[] horizontalDirections = {Direction.LEFT, Direction.RIGHT};
        return horizontalDirections[new Random().nextInt(horizontalDirections.length)];
    }
}
