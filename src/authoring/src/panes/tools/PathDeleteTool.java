package panes.tools;

import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import panes.MapPane;
import panes.Path;
import util.AuthoringContext;


public class PathDeleteTool extends PathModifyTool{

    public PathDeleteTool(AuthoringContext context, MapPane otherMap, Scene otherScene, String fileName, ObservableList<Path> paths){
        super(context, otherMap, otherScene, fileName, paths);
    }

    public void setMouseActions(){
        map.accessMap(node -> node.setOnMousePressed(e -> onMapClick(e)));
    }

    @Override
    public void onMapClick(MouseEvent event) {
        var selected = checkPointSelected(event.getX(), event.getY());
        if(selected){
            map.getCurrentState().removePath(selectedPath);
            selectedPath.removePoint(selectedPoint);
            map.removeShape(selectedPoint.getPoint());
            updatePathLines(selectedPath);
            map.getCurrentState().addToPaths(selectedPath);
        }
    }

    @Override
    protected void enableTool() {
        var delete = new Image(getContext().getString("DeleteFile"));
        ImageCursor deleteCursor = new ImageCursor(delete, 0, 0);
        scene.setCursor(deleteCursor);
        setMouseActions();
    }

    @Override
    protected void disableTool() {
        scene.setCursor(Cursor.DEFAULT);
        removeMouseActions();
    }
}
