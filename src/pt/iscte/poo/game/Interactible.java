package pt.iscte.poo.game;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.Room;

public interface Interactible {
	
	public boolean hasInteracted(Point2D otherPosition);
	
	private boolean dealsDamage(Point2D position) {
		if (!Room.isWithinRoom(position)) return false; //????
		
	}
			
}