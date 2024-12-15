package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.Interactible;
import pt.iscte.poo.game.GameEngine;
import pt.iscte.poo.game.GameObject;

public class Princess extends GameObject implements ImageTile, Interactible {
    
	private final Point2D position;

    public Princess(Point2D position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Princess";
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
    	GameEngine engine = GameEngine.getInstance();
    	int ticks = engine.getTickCounter();
    	String win = "You won!\nInput your name:";
    	String name = ImageGUI.getInstance().askUser(win);
    	String time = engine.convertTicksToTime(ticks);
    	engine.bestTimes(time, name);
    	engine.printBestTimes();
        ImageGUI.getInstance().dispose();
    }
}
