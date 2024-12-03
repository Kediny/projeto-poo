package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class StatusBg implements ImageTile {
    private final Point2D position;

    public StatusBg(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Beige";
    }

    @Override
    public int getLayer() {
        return 0;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
}
