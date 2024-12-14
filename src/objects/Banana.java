package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.GameObject;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.game.Status;

public class Banana extends GameObject implements ImageTile, Interactible {

    private Point2D position;
    private static final int DAMAGE = 1;
    
    public Banana(Point2D startPosition) {
        this.position = startPosition;
    }

    @Override
    public String getName() {
        return "Banana";
    }

    @Override
    public int getLayer() {
        return 3; // Ensures it appears above ground but below the player
    }

    @Override
    public Point2D getPosition() {
        return position;
    }
    
    public void fall() {
        Point2D newPosition = position.plus(0, 1); // Moves the banana down by one tile
        position = newPosition;
    }
    
    @Override
    public void interaction() {
        Player player = Player.getInstance();
        if (player.getPosition().equals(position)) {
            player.takeDamage(DAMAGE);
            removeSelf();
//            Status.getInstance().printStatus("Player hit by a banana!");
        }
    }
    
    public void removeSelf() {
        ImageGUI.getInstance().removeImage(this);
        Room.getInstance().removeInteractible(this);
    }
}