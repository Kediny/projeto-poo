package pt.iscte.poo.game;

public class Player extends GameObject {

    private int lives = 3;
    private boolean hasSword = false;
    private boolean hasBomb = false;
    private boolean hasMeat = false;
    
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
        currentStatus.setDirtyFlag(hasBomb);
    }
    
    public boolean hasMeat() {
    	return hasMeat;
    }
    
    public void setHasMeat(boolean hasMeat) {
        this.hasMeat = hasMeat;
        currentStatus.setDirtyFlag(hasMeat);
    }
    
    public boolean hasSword() {
        return hasSword;
    }

    public void setHasSword(boolean hasSword) {
        this.hasSword = hasSword;
        currentStatus.setDirtyFlag(hasSword);
        if (hasSword) setAttackPower();
    }

//	// Overriding interact for player-specific logic
//	@Override
//	public void interact(GameObject other) {
//		if (other instanceof Enemy) {
//			this.attack(other); // Example: attack an enemy
//			if (other.isAlive()) {
//				this.takeDamage(other.getAttackPower()); // Take counter-attack damage
//			}
//		} else if (other instanceof Item) {
//			// Example: interact with items
//			((Item) other).applyEffect(this);
//		}
//	}


    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }
}
