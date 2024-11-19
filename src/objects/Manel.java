package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel implements ImageTile {

	private Point2D position;

	public Manel(Point2D initialPosition) {
		position = initialPosition;
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
