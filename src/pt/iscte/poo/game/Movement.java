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
}
