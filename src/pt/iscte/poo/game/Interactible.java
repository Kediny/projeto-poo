package pt.iscte.poo.game;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.Room;

public interface Interactible {

	public void interaction();
	
	Point2D getPosition();
//	private boolean dealsDamage(Point2D position) {
//		if (!Room.isWithinRoom(position)) return false; //????
//		
//	}
			
}
