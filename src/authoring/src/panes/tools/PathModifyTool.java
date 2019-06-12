package panes.tools;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import panes.MapPane;
import panes.Path;
import panes.PathPoint;
import util.AuthoringContext;

import java.util.List;

public abstract class PathModifyTool extends PathTool {

    protected Path selectedPath;
    protected PathPoint selectedPoint;

    /**
     * These tools have an action in the MapPane on click
     *
     * @param otherMap
     * @param otherScene
     * @param fileName
     * @param paths
     */
    public PathModifyTool(AuthoringContext context, MapPane otherMap, Scene otherScene, String fileName, ObservableList<Path> paths) {
        super(context, otherMap, otherScene, fileName, paths);
    }

    protected boolean checkPointSelected(double checkX, double checkY){
        var select = false;
        for(Path path: pathOptions){
            for(Circle point: path.getPoints()){
                if(point.contains(new Point2D(checkX, checkY))){
                    selectedPoint = path.getPoint(point);
                    selectedPath = path;
                    select = true;
                    break;
                }
            }
        }
        return select;
    }
}
