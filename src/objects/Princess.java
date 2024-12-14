package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.game.Status;
import pt.iscte.poo.game.GameObject;

public class Princess extends GameObject implements ImageTile, Interactible {
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
    
    @Override
    public void interaction() {
    	Player player = Player.getInstance();
//    	TODO add winning interaction
    }
}
