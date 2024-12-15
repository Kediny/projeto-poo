package objects;

import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.game.Status;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.GameObject;


public class HiddenTrap extends GameObject implements ImageTile, Interactible {

private final Point2D position;

    public HiddenTrap(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Wall";
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
	    ImageGUI.getInstance().removeImage(this);
	    Trap trap = new Trap(position);
	    ImageGUI.getInstance().addImage(trap);
	    Room.getInstance().addInteractible(trap);
	    player.takeDamage(1);
		Status.getInstance().setDirtyFlag(true);
    }
    
}