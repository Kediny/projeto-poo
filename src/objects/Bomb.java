package objects;

import pt.iscte.poo.game.Interactible;
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
    	Room.getInstance().removeInteractible(this);
    }

    public boolean tick() {
        timer--;
        if (timer <= 0) {
            explode();
            return true;
        }
        return false;
    }
    
    public void removeSelf() {
    	ImageGUI.getInstance().removeImage(this);
    }

    private void explode() {
        Room room = Room.getInstance();
        // Damage all objects in the explosion radius
        for (int dx = -EXPLOSION_RADIUS; dx <= EXPLOSION_RADIUS; dx++) {
            for (int dy = -EXPLOSION_RADIUS; dy <= EXPLOSION_RADIUS; dy++) {
            	//implement bomb explosion lol
            	System.out.println("Boom!");
            }
        }
    }
}