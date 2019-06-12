package panes;

import authoring.ILevelDefinition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import panes.tools.ToolbarPane;
import util.AuthoringContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LevelHandler {

    private AuthoringContext context;
    private MapPane map;
    private PathPane pathPane;
    private ConsolePane console;
    private ToolbarPane toolbarPane;
    private ObservableList<Path> currentPaths;

    public LevelHandler(AuthoringContext context, MapPane map, PathPane pathPane, ConsolePane console, ToolbarPane toolbarPane,  ObservableList<Path> currentPaths) {
        this.context = context;
        this.map = map;
        this.pathPane = pathPane;
        this.console = console;
        this.toolbarPane = toolbarPane;
        this.currentPaths = currentPaths;
        currentPaths.addListener((ListChangeListener<Path>) c -> onPathListChange(c));
        // Establish select count listener for the first level
        map.getCurrentState().accessSelectCount(countProperty -> establishSelectCountListener(countProperty));

    }

    private void establishSelectCountListener(SimpleIntegerProperty selectCount) {
        selectCount.addListener((observable, oldValue, newValue) -> updateOnLevelSelection((int) newValue));
    }

    private void updateOnLevelSelection(int newValue){
        map.handleSelectionChange(newValue);
        //pathPane.setNewPathList(map.getCurrentState().getPaths());
    }

    private void onPathListChange(ListChangeListener.Change<? extends Path> c){
        var oldPaths = new ArrayList<>(map.getCurrentState().getPaths());
        for(Path p: oldPaths){
            map.getCurrentState().removePath(p);
        }
        for(Path p: currentPaths){
            map.getCurrentState().addToPaths(p);
        }
    }


    void clearLevel() {
        map.clearMap();
        int levelIndex = (int)(double) toolbarPane.getLevelChanger().getValue();
        ILevelDefinition gameLevel = context.getState().getLevels().get(levelIndex-1);
        clearGameLevelContents(gameLevel);
        map.getStateMapping().put(levelIndex, new MapState(context, map.getCurrentState().getBackgroundURL(), new ArrayList<>(), new ArrayList<>()));
        map.getCurrentState().accessSelectCount(countProperty -> establishSelectCountListener(countProperty));
        console.displayMessage("Level Cleared", ConsolePane.Level.NEUTRAL);
    }


    private void clearGameLevelContents(ILevelDefinition gameLevel) {
        // Commenting this out to change functionality - background images do not clear on level clear //gameLevel.setBackgroundImageURL(null);
        for(String pathName: gameLevel.getPaths().keySet()){
            gameLevel.removePath(pathName);
        }
        for(int i=0; i<gameLevel.getCurrentAgents().size(); i++){
            gameLevel.removeAgent(i);
        }
    }

    void makeLevel(int newLevel, boolean fromExisting) {
        map.forceLoadBackgroundIfNeeded();
        String newLevelDisplay;
        ILevelDefinition level;
        MapState newState;
        if (fromExisting) {
            newLevelDisplay = "Level " + newLevel + " created from Level: " + toolbarPane.getExistingLevelValue();
            try {
                level = context.getState().getLevels().get(toolbarPane.getExistingLevelValue() - 1).clone();
                context.getState().addLevel(level);
                newState = new MapState(map.getStateMapping().get(toolbarPane.getExistingLevelValue()), map);
            } catch (CloneNotSupportedException e) {
                context.displayConsoleMessage(context.getString("CloneError"), ConsolePane.Level.ERROR);
                return;
            }
        } else {
            level = context.getGameFactory().createLevel();
            newState = new MapState(context, map.getCurrentState().getBackgroundURL(), new ArrayList<>(), new ArrayList<>());
            newLevelDisplay = "Level " + newLevel + " created";
            context.getState().addLevel(level);
        }
        map.setLevel(newLevel);
        toolbarPane.setMaxLevel(newLevel);
        toolbarPane.addToExistingLevelCreator(newLevel);
        console.displayMessage(newLevelDisplay, ConsolePane.Level.NEUTRAL);
        if (!map.getStateMapping().containsKey(newLevel)) {
            map.getStateMapping().put(newLevel, newState);
            map.getCurrentState().accessSelectCount(countProperty -> establishSelectCountListener(countProperty));
            MapState revertToState = map.getStateMapping().get(newLevel);
            revertToState.updateMap(map);
        }
        int currentSpinnerValue = (int)(double)toolbarPane.getLevelChanger().getValue();
        toolbarPane.updateSpinner(currentSpinnerValue, newLevel);
    }

    void changeToExistingLevel(int newValue) {
        if (map.getStateMapping().containsKey(newValue)) {
            System.out.println("Old path names");
            for(Path p: map.getCurrentState().getPaths()){
                System.out.println(p.getID());
            }
            currentPaths.removeListener((ListChangeListener<Path>) c -> onPathListChange(c));

            map.setLevel(newValue);
            MapState revertToState = map.getStateMapping().get(newValue);

            currentPaths.setAll(revertToState.getPaths());
            pathPane.setNewPathList(currentPaths);

            System.out.println("New path names");
            for(Path p: revertToState.getPaths()){
                System.out.println(p.getID());
            }

            currentPaths.addListener((ListChangeListener<Path>) c -> onPathListChange(c));

            revertToState.updateMap(map);
        }
    }

    public void refreshCurrentLevel() {
        changeToExistingLevel(map.getLevel());
    }

}

