package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.game.Movement;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.game.Status;
import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.game.GameObject;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class DonkeyKong extends GameObject implements ImageTile, Interactible {

	// Attributes
    private Point2D position;
    private Timer movementTimer;
    private Timer bananaTimer;

    // Constructors
    public DonkeyKong(Point2D position) {
        this.position = position;
        setHealth(3);
        startRandomMovement();
        startBananaDrop();
    }

    // Getters e Setters
    @Override
    public String getName() {
        return "DonkeyKong";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    // Functions
    @Override
    public void interaction() {
        Player player = Player.getInstance();
        takeDamage(player.getAttackPower());
        if (!isAlive()) {
            stopMovement();
            ImageGUI.getInstance().removeImage(this);
            Room.getInstance().getInteractibles().remove(this);
            Status.getInstance().printKill(getName());
        }
        if (!player.getHasSword()) {
            player.takeDamage(getAttackPower());
        }
    }

    private void startRandomMovement() {
        movementTimer = new Timer();
        movementTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moveRandomly();
            }
        }, 0, 500);
    }

    public void stopMovement() {
        if (movementTimer != null) {
            movementTimer.cancel();
            movementTimer = null;
        }
        if (bananaTimer != null) {
            bananaTimer.cancel();
            bananaTimer = null;
        }
    }

    private void moveRandomly() {
        Direction randomDir = getRandomHorizontalDirection();
        Point2D newPosition = Movement.tryMove(position, randomDir);
        if (Player.getInstance().getPosition() != null && Player.getInstance().getPosition().equals(newPosition)) {
            this.interaction();
        }
        
        if (!newPosition.equals(position) && !Room.getInstance().isDoor(newPosition) && !Room.getInstance().isPrincess(newPosition)){
            position = newPosition;
        }
    }

    private Direction getRandomHorizontalDirection() {
        Direction[] horizontalDirections = {Direction.LEFT, Direction.RIGHT};
        return horizontalDirections[new Random().nextInt(horizontalDirections.length)];
    }

    private void startBananaDrop() {
        bananaTimer = new Timer();
        bananaTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                dropBanana();
            }
        }, 0, 1500);
    }

    private void dropBanana() {
        Point2D bananaPosition = position.plus(0, 1);
        Banana banana = new Banana(bananaPosition);
        ImageGUI.getInstance().addImage(banana);
        Room.getInstance().addInteractible(banana);
    }
}