package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.game.Movement;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.game.Status;
import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.GameObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class DonkeyKong extends GameObject implements ImageTile, Interactible {

    private Point2D position;
    private Timer movementTimer;
    
    public DonkeyKong(Point2D position) {
        this.position = position;
        setHealth(3);
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
        }, 0, 500);  // Random movement every half-second (can adjust for your game speed)
    }
    
    public void stopMovement() {
        if (movementTimer != null) {
            movementTimer.cancel();
            movementTimer = null;
        }
    }

    private void moveRandomly() {
        Direction randomDir = getRandomHorizontalDirection();
        Point2D newPosition = Movement.tryMove(position, randomDir);
    	if (Player.getInstance().getPosition() != null && Player.getInstance().getPosition().equals(newPosition)) {
    		this.interaction();
    	}
        if (!newPosition.equals(position) && !Room.getInstance().isDoor(newPosition)) {
            position = newPosition;
        }
    }

    private Direction getRandomHorizontalDirection() {
        Direction[] horizontalDirections = {Direction.LEFT, Direction.RIGHT};
        return horizontalDirections[new Random().nextInt(horizontalDirections.length)];
    }
    
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
}