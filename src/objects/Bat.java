package objects;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


import pt.iscte.poo.game.Movement;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Bat implements ImageTile {
	
	private Point2D position;
	private Timer movementTimer;

    public Bat(Point2D position) {
        this.position = position;
        startRandomMovement();
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
    	Point2D below = position.plus(new Vector2D(0,1));
    	Point2D newPosition;
    	if (Room.getInstance().isStairs(below)) {
    		newPosition = Movement.tryMove(position, Direction.DOWN);
    	} else {
    		Direction randomDir = getRandomDirection();
	        newPosition = Movement.tryMove(position, randomDir);
	        if (Room.getInstance().isDoor(newPosition)) {
	    		return;
	    	}
    	}
        if (!newPosition.equals(position)) {
            position = newPosition;
        } else {
        	moveRandomly();
        }
    }

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