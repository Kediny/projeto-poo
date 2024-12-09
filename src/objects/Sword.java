package objects;

import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.game.Player;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Sword implements ImageTile, Interactible {
	
    private final Point2D position;
    private Player player;
    
    public Sword(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Sword";
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
    	player.setHasSword(true);
    	ImageGUI.getInstance().removeImage(this);
    }
    
    
}
