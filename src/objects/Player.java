package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.GameObject;
import pt.iscte.poo.game.Room;
import pt.iscte.poo.game.Status;

public class Player extends GameObject implements ImageTile {

	// Attributes
	private Point2D position;
	private int maxLives = 3;
	private int lives = maxLives;
	private int fullHealth = 3;
    private boolean hasSword = false;
    private boolean hasBomb = false;
    private Status currentStatus;
    private static Player instance;
	
    // Constructors
    private Player() {
    	setHealth(fullHealth);
    }
    
    public static Player getInstance() {
    	if(Player.instance == null) {
    		Player.instance = new Player();
    	}
    	return Player.instance;
    }
    
    public static void killInstance() {
        instance = null;
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
        if (hasSword) setAttackPower(3);
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
    	if (health <= 0) loseLife();
    	currentStatus.setDirtyFlag(true);
    }
    
    @Override
    public boolean isAlive() {
    	return lives > 0;
    }

    public void loseLife() {
        if (lives > 1) {
            lives--;
            Status.getInstance().printStatus("You lost a life.........");
            GameEngine.sleep(500);
            Status.getInstance().printStatus("You lost a life..................");
            GameEngine.sleep(500);
            Status.getInstance().printStatus("You lost a life...........................");
            GameEngine.sleep(500);
            Room.getInstance().reloadRoom(Room.getInstance().getCurrentRoom());
            heal();
        } else {
        	loseGame();
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
        setHealth(fullHealth);
        Status.getInstance().setDirtyFlag(true);
    }
	
	public void loseGame() {
		Status.getInstance().printStatus("YOU LOST THE GAME.");
        GameEngine.sleep(500);
        Status.getInstance().printStatus("Restarting...");
        GameEngine.sleep(2000);
        GameEngine.getInstance().resetGame();
	}
	
	public void resetPlayer() {
		lives = maxLives;
		health = fullHealth;
		hasSword = false;
	    hasBomb = false;
	}
}