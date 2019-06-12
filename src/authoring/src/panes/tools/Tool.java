package panes.tools;

import javafx.scene.Scene;
import panes.MapPane;
import util.AuthoringContext;

public abstract class Tool {

    /**
     * Abstract class used to create a general structure for tools to be displayed in the toolbar
     * Each Tool has an image associated with it, as well as the ability to enable/disable the tool
     *
     * AuthoringContext is used to display console messages
     * Scene is used to modify cursors for tools
     * MapPane is used so tools can modify the map
     * @author Mary Stuart Elder
     */

    protected String imageFile;
    protected boolean toolEnabled;
    protected MapPane map;
    protected Scene scene;
    private AuthoringContext context;

    /**
     * General constructor for Tool
     * @param authoringContext context from AuthoringEnvironment
     * @param otherMap MapPane for environment
     * @param otherScene Scene for environment
     * @param fileName String representing the file
     */
    public Tool(AuthoringContext authoringContext, MapPane otherMap, Scene otherScene, String fileName){
        map = otherMap;
        scene = otherScene;
        toolEnabled = false;
        imageFile = fileName;
        context = authoringContext;
    }

    /**
     * Often used by subclasses to display messages to the console
     * @return AuthoringContext
     */
    protected AuthoringContext getContext() {
        return context;
    }

    /**
     * This method is called (typically from a button press) to toggle whether the tool is enabled
     * Calls the abstract enableTool / disableTool methods
     * @param enable boolean used to enable/disable tool
     */
    public void setToolEnabled(boolean enable){
        toolEnabled = enable;
        if(toolEnabled){
            enableTool();
        }
        else{
            disableTool();
        }
    }

    /**
     * Returns the value of toolEnabled
     * Often used in order to toggle the tool (toolEnabled = !toolEnabled)
     * @return boolean toolEnabled
     */
    public boolean getToolEnabled(){
        return toolEnabled;
    }

    /**
     * Implemented differently for each subclass, called as a result of setToolEnabled if enabled
     */
    protected abstract void enableTool();

    /**
     * Implemented differently for each subclass, called as a result of setToolEnabled if disabled
     */
    protected abstract void disableTool();
}
