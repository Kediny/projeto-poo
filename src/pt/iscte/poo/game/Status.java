package pt.iscte.poo.game;

import pt.iscte.poo.gui.*;

public class Status {

	private static Status instance;
	private Player player;
	private boolean dirtyFlag = true;

    private Status() {
    	this.player = Player.getInstance();
    }
    
    public static Status getInstance() {
    	if (Status.instance == null) {
    		Status.instance = new Status();
    	}
		return Status.instance;
	}
    
    public void renderStatus() {
    	String status = " Health: ";
    	for (int i = 1; i <= 3; i++) {
    		if (i <= player.getHealth()) {
    			status += "■";
    		} else {
    			status += "□";
    		}
    	}
    	status += "  Lives: " + player.getLives() + "  Bomb: [";
    	if (player.hasBomb())
    		status += "💣";
    	else
    		status += " ";
    	status += "]  Sword: [";
    	if (player.hasSword())
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
