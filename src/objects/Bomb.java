package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Bomb implements ImageTile {
    private final Point2D position;

    public Bomb(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Bomb";
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