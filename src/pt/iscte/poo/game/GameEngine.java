package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import objects.Player;

public class GameEngine implements Observer {
	
	private Room currentRoom;
	private Status status;
	private Player player;
	private static GameEngine instance;
	
	private int lastTickProcessed = 0;
	
	private GameEngine() {
		currentRoom = Room.getInstance();
		player = Player.getInstance();
		status = Status.getInstance();
		player.setCurrentStatus();
		status.setPlayer();
		ImageGUI.getInstance().update();
	}
	
	public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }
	
	public static void resetGame() {
		Player.getInstance().resetPlayer();
		ImageGUI.getInstance().clearImages();
		Room.killInstance();
		Room.getInstance();
		Status.getInstance().setDirtyFlag(true);
    }

	@Override
	public void update(Observed source) {
		if(status.getDirtyFlag()) {
			status.renderStatus();
		}
		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
			System.out.println("Keypressed " + k);
			if (Direction.isDirection(k)) {
				System.out.println("Direction! ");
				Direction dir = Direction.directionFor(k);
				Movement.movePlayer(dir);
			} else if (k == 66) {
				currentRoom.getPlayer().placeBomb();
			}
		}
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();
		}
		ImageGUI.getInstance().update();
	}

	private void processTick() {
		Room.getInstance().tick();
		lastTickProcessed++;
//		System.out.println("Tic Tac : " + lastTickProcessed);
		Movement.applyGravity(currentRoom.getPlayer());
	}

	public static void sleep(int ms) {
		try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("The sleep was interrupted.");
        }
	}

}