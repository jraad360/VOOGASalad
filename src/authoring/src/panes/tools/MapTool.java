package panes.tools;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import panes.MapPane;
import util.AuthoringContext;

public abstract class MapTool extends Tool{

    /**
     * These tools have an action in the MapPane on click
     *
     * @param otherMap
     * @param otherScene
     * @param fileName
     */
    public MapTool(AuthoringContext context, MapPane otherMap, Scene otherScene, String fileName) {
        super(context, otherMap, otherScene, fileName);
    }

    public abstract void onMapClick(MouseEvent event);
}
