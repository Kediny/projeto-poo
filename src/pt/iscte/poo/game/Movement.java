package pt.iscte.poo.game;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

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
    
    public static void moveManel(Direction dir) {
		Point2D currentPosition = Room.getInstance().getManel().getPosition();
		Point2D futurePosition = Movement.tryMove(currentPosition, dir);
		Room currentRoom = Room.getInstance();

	    if (!futurePosition.equals(currentPosition) && currentRoom.isDoor(futurePosition)) {
	        currentRoom.nextRoom(futurePosition);
	        return;
	    }

		if (!futurePosition.equals(currentPosition)) {
			currentRoom.getManel().setPosition(futurePosition);
			for(Interactible interactible : currentRoom.getInteractibles()) {
				if(interactible.getPosition().equals(futurePosition)) {
					interactible.interaction();
					break;
				}
			}
			currentRoom.getManel().updatePosition();
		}
	}
    
}