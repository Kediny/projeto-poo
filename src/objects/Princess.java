package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Princess implements ImageTile {
    private final Point2D position;

    public Princess(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Princess";
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
