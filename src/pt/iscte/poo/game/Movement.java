package pt.iscte.poo.game;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Movement {


    // Check if a given position is valid within the room
    public static boolean isValidMove(Point2D position) {
        return Room.getInstance().isWithinRoom(position) && !Room.getInstance().isWall(position);
    }

    // Attempt to move an entity in a given direction
    public static Point2D tryMove(Point2D currentPosition, Direction direction) {
        // Try the regular move (no wrapping yet)
        Point2D newPosition = currentPosition.plus(direction.asVector());

        // If this move is valid (within bounds and not a wall), accept it
        if (isValidMove(newPosition)) {
            return newPosition;
        }

        // Handle horizontal wrapping:
        if (direction == Direction.LEFT && newPosition.getX() < 0) {
            // Wrap to the rightmost column (MAX_X) if the player is trying to move off the left edge
            Point2D wrapPosition = new Point2D(Room.getMaxX(), currentPosition.getY());
            if (Room.getInstance().isWalkable(wrapPosition)) {
                return wrapPosition;
            }
        } else if (direction == Direction.RIGHT && newPosition.getX() > Room.getMaxX()) {
            // Wrap to the leftmost column (0) if the player is trying to move off the right edge
            Point2D wrapPosition = new Point2D(0, currentPosition.getY());
            if (Room.getInstance().isWalkable(wrapPosition)) {
                return wrapPosition;
            }
        }

        // If no valid move or wrap, stay at the current position
        return currentPosition;
    }
}
