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

    
}
