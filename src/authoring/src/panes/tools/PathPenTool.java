package panes.tools;

import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import panes.ConsolePane;
import panes.MapPane;
import panes.Path;
import util.AuthoringContext;

import java.util.Optional;

public class PathPenTool extends PathTool{

    private Path currentPath;

    public PathPenTool(AuthoringContext context, MapPane otherMap, Scene otherScene, String fileName, ObservableList<Path> paths){
        super(context, otherMap, otherScene, fileName, paths);
    }

    public void enableToolWithPath(String pathID){
        selectPath(pathID);
        setupPen();
    }

    public void removePathFromID(String id){
        // TODO: change to get path from string id
        var pathToRemove = getPath(id);
        if(pathToRemove != null){
            removePath(pathToRemove);
        }
    }

    // Called
    private void selectPath(String pathName){
        if(currentPath != null){
            currentPath.setStyleSelected(false);
        }
        String messageContent;
        ConsolePane.Level messageLevel;

        if(!containsPath(pathName)){
            messageContent = "InvalidPathError";
            messageLevel = ConsolePane.Level.ERROR;
        }
        else{
            currentPath = getPath(pathName);
            messageContent = "PathSelectedMessage";
            messageLevel = ConsolePane.Level.NEUTRAL;
        }
        currentPath.setStyleSelected(true);
        getContext().displayConsoleMessage(getContext().getString(messageContent) + pathName, messageLevel);
    }

    private void setupPen(){
        var pen = new Image(getContext().getString("PenFile"));
        ImageCursor penCursor = new ImageCursor(pen, 0, 0);
        scene.setCursor(penCursor);
        setMouseActions();
    }

    public void setMouseActions(){
        map.accessMap(node -> node.setOnMousePressed(e -> onMapClick(e)));
    }

    @Override
    public void onMapClick(MouseEvent event) {
        map.getCurrentState().removePath(currentPath);
        getContext().getState().getLevels().get(map.getLevel() - 1).removePath(currentPath.getID());
        var circle = currentPath.addPoint(event.getX(), event.getY());
        map.spawnShape(circle);
        updatePathLines(currentPath);
        map.getCurrentState().addToPaths(currentPath);
        getContext().getState().getLevels().get(map.getLevel() - 1).addPath(currentPath.getID(), currentPath.getPathLocations());
    }

    @Override
    protected void enableTool() {
        // From https://code.makery.ch/blog/javafx-dialogs-official/
        // TODO: add popup for typing in path name
        TextInputDialog dialog = new TextInputDialog("PathName");
        dialog.setTitle("Path Name Dialog");
        dialog.setHeaderText("Adding New Path");
        dialog.setContentText("Please enter your path name:");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            var path = new Path(result.get());
            addPath(path);
            selectPath(result.get());
            setupPen();
        }
        else{
            disableTool();
        }
    }

    @Override
    protected void disableTool() {
        scene.setCursor(Cursor.DEFAULT);
        if(currentPath!=null) {
            currentPath.setStyleSelected(false);
        }
        currentPath = null;
        removeMouseActions();
    }
}
