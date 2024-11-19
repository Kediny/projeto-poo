package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.game.Movement;

import java.util.Timer;
import java.util.TimerTask;

public class DonkeyKong implements ImageTile {

    private Point2D position; // Make mutable for movement
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
        return 2; // Foreground layer
    }

    @Override
    public Point2D getPosition() {
        return position;
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

    // Attempt random movement
    private void moveRandomly() {
        Direction randomDir = Direction.random();
        Point2D newPosition = Movement.tryMove(position, randomDir);
        if (!newPosition.equals(position)) { // Only update if a valid move was made
            position = newPosition;
        }
    }
}
