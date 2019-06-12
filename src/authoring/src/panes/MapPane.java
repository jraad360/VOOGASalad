package panes;

import frontend_objects.AgentView;
import frontend_objects.DraggableAgentView;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import util.AuthoringContext;
import util.AuthoringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MapPane extends AuthoringPane {

    private Pane mapPane;
    private StackPane overallPane;
    private int level;
    private Map<Integer, MapState> levelToState;
    private SimpleBooleanProperty selection = new SimpleBooleanProperty();

    public MapPane(AuthoringContext context) {
        super(context);
        selection.set(false);
        levelToState = new HashMap<>();
        initPanes();
        getContentChildren().add(overallPane);
    }

    public void accessMap(Consumer<Pane> accessMethod) {
        accessMethod.accept(mapPane);
    }

    public Map<Integer, MapState> getStateMapping() {
        return levelToState;
    }

    public void handleSelectionChange(int newValue) {
        MapState currentLevel = levelToState.get(level);
        if (newValue > 0) {
            for (DraggableAgentView agent : currentLevel.getAgents()) {
                if (agent.getSelect()) {
                    agent.setOnMousePressed(event -> clickMultiple(event));
                    agent.setOnMouseDragged(event -> dragMultiple(event));
                    agent.setOnMouseReleased(event -> releaseMultiple());
                }
            }
        } else {
            for (DraggableAgentView agent: currentLevel.getAgents()) {
                agent.setOnMousePressed(event -> agent.mousePressed(event));
                agent.setOnMouseDragged(event -> agent.mouseDragged(event, this));
                agent.setOnMouseReleased(event -> agent.mouseReleased(this));
            }
        }
    }

    private void clickMultiple(MouseEvent event) {
        MapState currentLevel = levelToState.get(level);
        for (DraggableAgentView agent : currentLevel.getAgents()) {
            if (agent.getSelect()) {
                agent.mousePressed(event);
            }
        }
    }

    private void dragMultiple(MouseEvent event) {
        MapState currentLevel = levelToState.get(level);
        for (DraggableAgentView agent : currentLevel.getAgents()) {
            if (agent.getSelect()) {
                agent.mouseDragged(event, this);
            }
        }
    }

    private void releaseMultiple() {
        MapState currentLevel = levelToState.get(level);
        List<DraggableAgentView> agentsCopy = new ArrayList<>(currentLevel.getAgents());
        for (DraggableAgentView agent : agentsCopy) {
            if (agent.getSelect()) {
                agent.mouseReleased(this);
            }
        }
    }

    public MapState getCurrentState() {
        return levelToState.get(level);
    }

    private void initPanes() {
        overallPane = initOverallPane();
        mapPane = new Pane();
        mapPane.setMaxSize(getContext().getDouble("InsetMapWidth"), getContext().getDouble("InsetMapHeight"));
        mapPane.setMinSize(getContext().getDouble("InsetMapWidth"), getContext().getDouble("InsetMapHeight"));
        overallPane.getChildren().add(mapPane);
    }

    private StackPane initOverallPane(){
        var stack = new StackPane();
        stack.setPrefSize(getContext().getDouble("MapWidth"), getContext().getDouble("MiddleRowHeight") - getContext().getDouble("MiddleRowPadding"));
        stack.getStylesheets().add(getContext().getString("MapStyle"));
        stack.setId(getContext().getString("MapStyleId"));
        stack.setAlignment(Pos.CENTER);
        return stack;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getPaneWidth() {
        return overallPane.getWidth();
    }

    /**
     *
     * @param agent
     */
    public void addAgent(DraggableAgentView agent){
        MapState currentLevel = levelToState.get(level);
        currentLevel.addToAgents(agent);
        mapPane.getChildren().add(agent);
    }

    /**
     *
     * @param agent
     */
    public void removeAgent(DraggableAgentView agent) {
        MapState currentLevel = levelToState.get(level);
        currentLevel.removeAgent(agent);
        getContext().getState().getLevels().get(level - 1).getCurrentAgents().remove(agent.getReference());
        agent.setImage(null);
        //System.out.println("Removed: new size is " + levelToState.get(level).getAgentCount());
    }

    /**
     *
     * @return
     */
    public int getAgentCount() {
        MapState currentLevel = levelToState.get(level);
        return currentLevel.getAgentCount();
    }

    /**
     * Called from Tools to add Tool image to display
     * @param sprite
     */
    public void spawnShape(Shape sprite){
        mapPane.getChildren().add(sprite);
    }

    /**
     * Called from Tools to remove Tool image from display
     * @param sprite
     */
    public void removeShape(Shape sprite){
        if(mapPane.getChildren().contains(sprite)){
            mapPane.getChildren().remove(sprite);
        }
    }

    /**
     *
     */
    public void loadBackground() {
        AuthoringUtil.openFileChooser(
                getContext().getString("ImageFile"), AuthoringUtil.IMAGE_EXTENSIONS, false, null,
                file -> setMapImage(level, file.getAbsolutePath(), true),
                () -> getContext().displayConsoleMessage(getContext().getString("MapImageLoadError"), ConsolePane.Level.ERROR)
        );
    }

    public void forceLoadBackgroundIfNeeded() {
        while (levelToState.get(level).getBackgroundURL() == null) {
            Alert needBackgroundAlert = new Alert(Alert.AlertType.ERROR, getContext().getString("NeedBackground"));
            needBackgroundAlert.showAndWait();
            loadBackground();
        }
    }

    /**
     * Assumes input from loadBackground method, which gives exclusively valid correct file names
     * @param fileName
     */
    public void setMapImage(int level, String fileName, boolean fromGameEngine){

        String modifiedFile = AuthoringUtil.switchAuthorEngineURL(fileName, fromGameEngine);
        String authorURL = fromGameEngine ? modifiedFile : fileName;
        String engineURL = !fromGameEngine ? modifiedFile : fileName;

        levelToState.get(level).setBackgroundURL(authorURL);
        getContext().getState().getLevels().get(level-1).setBackgroundImageURL(engineURL);
        mapPane.setStyle(
                "-fx-background-image: url(" +
                        authorURL +
                        "); " +
                        "-fx-background-size: cover;"
        );
    }

    public void clearMap() {
        mapPane.getChildren().clear();
    }

    public Pane getMapPane() {
        return mapPane;
    }


    @Override
    public void setStylesheet(String url) {

    }

    /**
     *
     * @param width
     * @param height
     */
    @Override
    public void updateSize(double width, double height) {
        overallPane.setPrefSize(width, height);
    }
}
