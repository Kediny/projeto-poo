package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import objects.Player;
import java.io.*;
import java.util.*;

public class GameEngine implements Observer {
	
	private Room currentRoom;
	private Status status;
	private Player player;
	private static GameEngine instance;
	
	private int lastTickProcessed = 0;
	
	private GameEngine() {
		// refresh
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
	
	public void resetGame() {
		Player.getInstance().resetPlayer();
		ImageGUI.getInstance().clearImages();
		Room.getInstance().getInteractibles().clear();
		Room.killInstance();
		Room.getInstance();
		lastTickProcessed = 0;
		Status.getInstance().setDirtyFlag(true);
    }

	@Override
	public void update(Observed source) {
		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
//			System.out.println("Keypressed " + k);
			if (Direction.isDirection(k)) {
//				System.out.println("Direction! ");
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
		if(status.getDirtyFlag()) {
			status.renderStatus();
		}
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
	
	public String convertTicksToTime(int ticks) {
	    int totalMilliseconds = ticks * 500;
	    int minutes = totalMilliseconds / 60000;
	    int remainingMilliseconds = totalMilliseconds % 60000;
	    int seconds = remainingMilliseconds / 1000;
	    int milliseconds = remainingMilliseconds % 1000;
	    return String.format("%dm%ds%dms", minutes, seconds, milliseconds);
	}

	public void bestTimes(String time, String playerName) {
	    File file = new File("src/objects/besttimes.txt");
	    List<String> bestTimes = new ArrayList<>();
	    if (file.exists()) {
	        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                bestTimes.add(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } else {
	        try {
	            file.createNewFile();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    bestTimes.add(time + " - " + playerName);
	    bestTimes.sort((score1, score2) -> {
	        int time1 = parseTimeToMilliseconds(score1.split(" - ")[0]);
	        int time2 = parseTimeToMilliseconds(score2.split(" - ")[0]);
	        return Integer.compare(time1, time2);
	    });
	    if (bestTimes.size() > 5) {
	        bestTimes = bestTimes.subList(0, 5);
	    }
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
	        for (String score : bestTimes) {
	            bw.write(score);
	            bw.newLine();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	private static int parseTimeToMilliseconds(String time) {
	    int minutes = 0, seconds = 0, milliseconds = 0;

	    String[] timeParts = time.split("m|s|ms");
	    
	    if (time.contains("m")) {
	        minutes = Integer.parseInt(timeParts[0]);
	    }
	    if (time.contains("s")) {
	        seconds = Integer.parseInt(timeParts[timeParts.length > 1 ? 1 : 0]);
	    }
	    if (time.contains("ms")) {
	        milliseconds = Integer.parseInt(timeParts[timeParts.length - 1]);
	    }

	    return (minutes * 60 * 1000) + (seconds * 1000) + milliseconds;
	}
	
	public int getTickCounter() {
		return lastTickProcessed;
	}

}