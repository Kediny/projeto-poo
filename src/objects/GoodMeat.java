package objects;

import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class GoodMeat implements ImageTile, Interactible {
	
	private final Point2D position;
    private Player player;

    public GoodMeat(Point2D position) {
        this.position = position;
    }
    
//    public void updateMeat(){
//    	if (Room.getInstance().getRoomTickCounter() > 10)
//    		Room.getInstance().getInteractibles().remove(this);
//    }
    
    @Override
    public String getName() {
        return "GoodMeat";
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
    	player = Player.getInstance();
    	ImageGUI.getInstance().removeImage(this);
    	if (Room.getInstance().getRoomTickCounter() <= 10) {
    		player.heal();
    	} else {
    		player.takeDamage(1);
    	}
    	Room.getInstance().getInteractibles().remove(this);
    }
}