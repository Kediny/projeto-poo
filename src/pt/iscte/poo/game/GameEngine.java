package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {
	
	private Room currentRoom = new Room("room0.txt");
	private Status status;
	Player player = Player.getInstance();
	
	private int lastTickProcessed = 0;
	
	public GameEngine() {
		this.status = Status.getInstance();
		player.setCurrentStatus();
		ImageGUI.getInstance().update();
		status.setPlayer(player);
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
				Movement.moveManel(dir);
			} else if (k == 66) {
				currentRoom.getManel().placeBomb();
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
		System.out.println("Tic Tac : " + lastTickProcessed);
		
		currentRoom.applyGravity(currentRoom.getManel());
	}

	public static void sleep(int ms) {
		try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("The sleep was interrupted.");
        }
	}

}