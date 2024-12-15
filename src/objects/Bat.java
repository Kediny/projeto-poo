package objects;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import pt.iscte.poo.game.Movement;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.game.Status;
import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.game.GameObject;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Bat extends GameObject implements ImageTile, Interactible {
    
	// Attributes
    private Point2D position;
    private Timer movementTimer;

    // Constructors
    public Bat(Point2D position) {
        this.position = position;
        setHealth(1);
        startRandomMovement();
    }

    // Getters e Setters
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

    public void setPosition(Point2D position) {
        this.position = position;
    }

    // Functions
    @Override
    public void interaction() {
        Player player = Player.getInstance();
        takeDamage(player.getAttackPower());
        if (!isAlive()) {
            Status.getInstance().setDirtyFlag(false);
            stopMovement();
            ImageGUI.getInstance().removeImage(this);
            Room.getInstance().getInteractibles().remove(this);
            Status.getInstance().printKill(getName());
        }
        if (!player.getHasSword()) {
            player.takeDamage(getAttackPower());
        }
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

    private void moveRandomly() {
        Point2D below = position.plus(new Vector2D(0, 1));
        Point2D newPosition;
        if (Room.getInstance().isStairs(below)) {
            newPosition = Movement.tryMove(position, Direction.DOWN);
        } else {
            Direction randomDir = getRandomDirection();
            newPosition = Movement.tryMove(position, randomDir);
        }
        if (Player.getInstance().getPosition() != null && Player.getInstance().getPosition().equals(newPosition)) {
            this.interaction();
        }
        
        
        if (!Room.getInstance().isDoor(newPosition) && !newPosition.equals(position)) {
            position = newPosition;
        } else {
            moveRandomly();
        }
    }

    private Direction getRandomDirection() {
        Direction[] directions = {Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN};
        return directions[new Random().nextInt(directions.length)];
    }
}
