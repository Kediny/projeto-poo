package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.GameObject;
import pt.iscte.poo.game.Player;
import pt.iscte.poo.game.Room;

public class Manel extends GameObject implements ImageTile {

	private Point2D position;
	
	// stuff from old Player class:
	
	

	public Manel(Point2D initialPosition) {
		position = initialPosition;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	public void updatePosition() {
	    ImageGUI.getInstance().removeImage(this); // Remove the old image from the GUI
	    ImageGUI.getInstance().addImage(this);   // Add the updated image at the new position
	}

	@Override
	public String getName() {
		return "JumpMan";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 4;
	}
	
	public void placeBomb() {
	    if (Player.getInstance().hasBomb()) {
	        Bomb bomb = new Bomb(position);
	        Room.getInstance().addInteractible(bomb);
	        ImageGUI.getInstance().addImage(bomb);
	        bomb.tick();
	        Player.getInstance().setHasBomb(false);
	    }
	}


}