package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class DoorOpen implements ImageTile {
    private final Point2D position;

    public DoorOpen(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "DoorOpen";
    }

    @Override
    public int getLayer() {
        return 3;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
}
