package pt.iscte.poo.game;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.Room;

public interface Interactible {

	public void interaction();
	
	Point2D getPosition();
	
<<<<<<< HEAD
}
=======
	public boolean hasInteracted(Point2D otherPosition);
	
	private boolean dealsDamage(Point2D position) {
		if (!Room.isWithinRoom(position)) return false; //????
		
	}
			
}
>>>>>>> 3d0e655de751fc0709d653f17d0205ae7e018272
