package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Fire implements ImageTile {
    private final Point2D position;

    public Fire(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Fire";
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
