package pt.iscte.poo.game;

import pt.iscte.poo.gui.*;

public class Status {

	private static Status instance;

	private Player player = new Player();
	private int health = player.getHealth();
	private int lives = player.getLives();
	private boolean bomb = player.hasBomb();
	private boolean sword = player.hasSword();
	
	private boolean dirtyFlag = true;

    public Status() {
		instance = this;
		
	}
    
    public static Status getInstance() {
		return instance;
	}
    
    public void renderStatus() {
    	String status = " Health: ";
    	for (int i = 1; i <= 3; i++) {
    		if (i <= health) {
    			status += "■";
    		} else {
    			status += "□";
    		}
    	}
    	status += "  Lives: " + lives + "  Bomb: [";
    	if (bomb)
    		status += "💣";
    	else
    		status += " ";
    	status += "]  Sword: [";
    	if (sword)
    		status += "🗡]";
    	else
    		status += " ]";
    	
    	ImageGUI.getInstance().setStatusMessage(status);
    	dirtyFlag = false;
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public void setPlayer(Player player) {
    	this.player = player;
    }
    
    public boolean getDirtyFlag() {return dirtyFlag;} 
    public void setDirtyFlag(boolean value) {dirtyFlag = value;} 
    
    
    
    
    
}