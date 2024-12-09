package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.GameObject;

<<<<<<< HEAD
public class Manel extends GameObject implements ImageTile {
=======
public class Manel extends GameObject implements ImageTile{
>>>>>>> 3d0e655de751fc0709d653f17d0205ae7e018272

	private Point2D position;

	public Manel(Point2D initialPosition) {
		position = initialPosition;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	public void updatePosition() {
	    // Update the graphical position in the GUI
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
		// TODO Auto-generated method stub
		return 1;
	}

	// Updated to use a Direction object for movement
	public void move(Direction dir) {
		position = position.plus(dir.asVector()); // Updates the position by adding the direction vector
		System.out.println("Moved to: " + position); // Debugging output for the new position
	}

}