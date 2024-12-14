package pt.iscte.poo.game;

import pt.iscte.poo.gui.*;
import objects.Player;

public class Status {

	private static Status instance;
	private Player player;
	private boolean dirtyFlag;

    private Status() {
    	dirtyFlag = true;
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
    	status += "                  Lives: " + player.getLives() + "                  Bomb: [";
    	if (player.getHasBomb())
    		status += "💣";
    	else
    		status += " ";
    	status += "]                  Sword: [";
    	if (player.getHasSword())
    		status += "🗡]";
    	else
    		status += " ]";
    	
    	ImageGUI.getInstance().setStatusMessage(status);
    	dirtyFlag = false;
    }
    
    public void printStatus(String text) {
    	ImageGUI.getInstance().setStatusMessage(text);
    	GameEngine.sleep(500);
		dirtyFlag = true;
    }
    
    public void printKill(String enemy) {
    	String text = enemy + " was killed!";
    	printStatus(text);
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public void setPlayer() {
    	this.player = Player.getInstance();
    }
    
    public boolean getDirtyFlag() {return dirtyFlag;}
    
    public void setDirtyFlag(boolean value) {
    	if (dirtyFlag != value) {
            dirtyFlag = value;
        }
    } 
}