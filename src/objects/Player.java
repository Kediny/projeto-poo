package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.GameObject;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.game.Status;

public class Player extends GameObject implements ImageTile {

	// Attributes
	private Point2D position;
	private int lives = 3;
    private boolean hasSword = false;
    private boolean hasBomb = false;
    private Status currentStatus;
    private static Player instance;
	
    // Constructors
    private Player() {
    	setHealth(3);
    }
    
    public static Player getInstance() {
    	if(Player.instance == null) {
    		Player.instance = new Player();
    	}
    	return Player.instance;
    }
    
    public static Player spawn(Point2D initialPosition) {
    	if(Player.instance == null) {
    		Player.instance = new Player();
    	}
		Player.instance.position = initialPosition;
    	return Player.instance;
    }
    
    // Setters and getters
    public void setCurrentStatus() {
    	currentStatus = Status.getInstance();
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    
    public boolean getHasBomb() {
        return hasBomb;
    }
    
    public void setHasBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
        currentStatus.setDirtyFlag(true);
    }

    public boolean getHasSword() {
        return hasSword;
    }

    public void setHasSword(boolean hasSword) {
        this.hasSword = hasSword;
        currentStatus.setDirtyFlag(true);
        if (hasSword) setAttackPower(2);
    }
    
    @Override
	public Point2D getPosition() {
		return position;
	}
    
    public void setPosition(Point2D position) {
		this.position = position;
	}
    
    @Override
	public String getName() {
		return "JumpMan";
	}
    
    @Override
	public int getLayer() {
		return 4;
	}
    
    // Functions
    @Override
    public void takeDamage(int damage) {
    	health -= damage;
    	currentStatus.setDirtyFlag(true);
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }
    
	public void update() {
	    ImageGUI.getInstance().removeImage(this);
	    ImageGUI.getInstance().addImage(this);
	}

	public void placeBomb() {
	    if (hasBomb) {
	        Bomb bomb = new Bomb(position);
	        Room.getInstance().addInteractible(bomb);
	        ImageGUI.getInstance().addImage(bomb);
	        bomb.tick();
	        setHasBomb(false);
	    }
	}
	
	public void heal() {
        setHealth(3);
        currentStatus.setDirtyFlag(true);
    }

}