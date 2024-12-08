
package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class StatusThree implements ImageTile {
    private final Point2D position;

    public StatusThree(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "3";
    }

    @Override
    public int getLayer() {
        return 1;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
}