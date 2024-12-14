package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.GameObject;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.game.Status;

public class Banana extends GameObject implements ImageTile, Interactible {

    private Point2D position;
    
    public Banana(Point2D startPosition) {
        this.position = startPosition;
    }
	public void fall() {
	        Point2D newPosition = position.plus(0, 1);
	        position = newPosition;
	}
	
    @Override
    public String getName() {
        return "Banana";
    }

    @Override
    public int getLayer() {
        return 3; // Ensures it appears above ground but below the player
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
    
    
    
    @Override
    public void interaction() {
		Status.getInstance().setDirtyFlag(false);
    	Player player = Player.getInstance();
    	player.takeDamage(1);
		Status.getInstance().setDirtyFlag(true);
    }
}