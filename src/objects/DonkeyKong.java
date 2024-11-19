package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class DonkeyKong implements ImageTile {
    private final Point2D position;

    public DonkeyKong(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    @Override
    public int getLayer() {
        return 2; // Foreground layer
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
}
