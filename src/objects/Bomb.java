package objects;

import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import java.util.ArrayList;

public class Bomb implements ImageTile, Interactible {
    
	private final Point2D position;
	private int timer; // Timer in ticks before the bomb explodes
	private static final int EXPLOSION_RADIUS = 1;
    private Player player;

    public Bomb(Point2D position) {
        this.position = position;
        this.timer = 5;
    }

    @Override
    public String getName() {
        return "Bomb";
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
    	if(!Player.getInstance().hadBomb){
	    	player = Player.getInstance(); 
	    	player.setHasBomb(true);
	    	ImageGUI.getInstance().removeImage(this);
	    	Room.getInstance().removeInteractible(this);
    	} else { explode(); }
    }

    public void tick() {
        timer--;
        if (timer <= 0) {
            explode();
        }
    }
    
    public void explode() {
        Room room = Room.getInstance();
        for (int dx = -EXPLOSION_RADIUS; dx <= EXPLOSION_RADIUS; dx++) {
            for (int dy = -EXPLOSION_RADIUS; dy <= EXPLOSION_RADIUS; dy++) {
                Point2D targetPos = position.plus(dx, dy);
                for (Interactible interactible : new ArrayList<>(room.getInteractibles())) {
                    if (interactible instanceof Wall || interactible instanceof Stairs) continue;
                    if (interactible.getPosition().equals(targetPos)) {
                        if (interactible instanceof Player) {
                            Player.getInstance().takeDamage(1);
                        } else {
                            room.removeInteractible(interactible);
//                            ImageGUI.getInstance().removeImage();
                        }
                    }
                }
            }
        }
        room.removeInteractible(this);
        ImageGUI.getInstance().removeImage(this);
    }

}