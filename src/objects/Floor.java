package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Floor implements ImageTile {
    
	private final Point2D position;

    public Floor(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Floor";
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