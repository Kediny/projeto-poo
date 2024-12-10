package objects;

import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.game.Player;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

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
    	player = Player.getInstance(); 
    	player.setHasBomb(true);
    	ImageGUI.getInstance().removeImage(this);
    }

    // Decrease the timer and check if it's time to explode
    public boolean tick() {
        timer--;
        if (timer <= 0) {
            explode();
            return true; // Return true if the bomb should be removed
        }
        return false;
    }

    // Bomb explosion logic
    private void explode() {
        Room room = Room.getInstance();

        // Damage all objects in the explosion radius
        for (int dx = -EXPLOSION_RADIUS; dx <= EXPLOSION_RADIUS; dx++) {
            for (int dy = -EXPLOSION_RADIUS; dy <= EXPLOSION_RADIUS; dy++) {
//                Point2D target = position.plus(dx, dy);
//                GameObject obj = room.getObjectAt(target);
//
//                if (obj != null && obj instanceof Damageable) {
//                    ((Damageable) obj).takeDamage(50); // Example damage value
//                }
            	System.out.println("Boom!");
            }
        }
        // Render explosion (optional, e.g., change tile to "explosion" for a moment)
    }
}