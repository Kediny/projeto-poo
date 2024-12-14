package pt.iscte.poo.game;

import objects.Player;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Movement {

    public static boolean isValidMove(Point2D position) {
        return Room.getInstance().isWithinRoom(position) && !Room.getInstance().isWall(position);
    }

    public static Point2D tryMove(Point2D currentPosition, Direction direction) {
        Point2D newPosition = currentPosition.plus(direction.asVector());
        if (isValidMove(newPosition)) {
            return newPosition;
        }
        return currentPosition;
    }
    
    public static void movePlayer(Direction dir) {
		Point2D currentPosition = Room.getInstance().getPlayer().getPosition();
		Point2D futurePosition = Movement.tryMove(currentPosition, dir);
		Room currentRoom = Room.getInstance();

	    if (!futurePosition.equals(currentPosition) && currentRoom.isDoor(futurePosition)) {
	        currentRoom.nextRoom(futurePosition);
	        return;
	    }

		if (!futurePosition.equals(currentPosition)) {
			currentRoom.getPlayer().setPosition(futurePosition);
			for(Interactible interactible : currentRoom.getInteractibles()) {
				if(interactible.getPosition().equals(futurePosition)) {
					interactible.interaction();
					break;
				}
			}
			currentRoom.getPlayer().update();
		}
	}
    
    public static void applyGravity(Player player) {
    	Room room = Room.getInstance();
		Vector2D v = new Vector2D(0,1);
	    Point2D below = player.getPosition().plus(v);
	    if (room.isStairs(player.getPosition()) || room.isStairs(below)) {
	        return;
	    }
	    if (room.isWithinRoom(below) && room.isWalkable(below)) {
	        player.setPosition(below);
	        player.update();
	    }
	    if(room.isTrap(player.getPosition())) player.takeDamage(1);
	}
}