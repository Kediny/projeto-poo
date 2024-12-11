package pt.iscte.poo.game;

public class Player extends GameObject {

    private int lives = 3;
    private boolean hasSword = false;
    private boolean hasBomb = false;
    
    private Status currentStatus;
    
    private static Player instance;

    private Player() {    }
    
    public static Player getInstance() {
    	if(Player.instance == null) {
    		Player.instance = new Player();
    	}
    	return Player.instance;
    }
    
    public void setCurrentStatus(Status status) {
    	currentStatus = status.getInstance();
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    
    public boolean hasBomb() {
        return hasBomb;
    }

    public void setHasBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
        currentStatus.setDirtyFlag(true);
    }
    
    public boolean hasSword() {
        return hasSword;
    }

    public void setHasSword(boolean hasSword) {
        this.hasSword = hasSword;
        currentStatus.setDirtyFlag(true);
        if (hasSword) setAttackPower(2);
    }
    
    public void heal() {
        setHealth(3);
        currentStatus.setDirtyFlag(true);
    }
    
    public void takeDamageBeef() {
    	takeDamage();
    	currentStatus.setDirtyFlag(true);
    }
    
    public void takeDamageTrap() {
    	takeDamage();
    	currentStatus.setDirtyFlag(true);
    }
    
    public void takeDamageBat() {
    	takeDamage();
    	currentStatus.setDirtyFlag(true);
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }

}
