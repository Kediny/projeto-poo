package pt.iscte.poo.game;

import pt.iscte.poo.gui.*;
import objects.*;
import pt.iscte.poo.utils.Point2D;

public class StatusBar {

    private static final int STATUS_BAR_Y = Room.getMaxY() + 1; // Status bar row
    private static final int STATUS_BAR_WIDTH = 10; // 10 blocks wide

    public void renderBaseBar() {
        // Render the beige background across the status bar
        for (int x = 0; x < STATUS_BAR_WIDTH; x++) {
            ImageGUI.getInstance().addImage(new StatusBg(new Point2D(x, STATUS_BAR_Y)));
        }
    }

    public void renderHealthAndLives() {
        for (int x = 0; x < 4; x++) {
            if (x < 3) {
                // If x is within the range of the player's lives, draw a green health unit
                ImageGUI.getInstance().addImage(new StatusGreen(new Point2D(x, STATUS_BAR_Y)));
            } else {
                // Otherwise, draw an "empty" (e.g., gray or red) health unit
                ImageGUI.getInstance().addImage(new StatusThree(new Point2D(x, STATUS_BAR_Y)));
            }
        }
    }
}
