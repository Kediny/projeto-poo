package objects;

import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class BadMeat implements ImageTile, Interactible {
	
	private final Point2D position;

    public BadMeat(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "BadMeat";
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
    	player.takeDamage(2); // Custom interaction logic
    }
}
