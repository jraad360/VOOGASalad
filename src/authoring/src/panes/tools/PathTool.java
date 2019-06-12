package panes.tools;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import panes.MapPane;
import panes.Path;
import util.AuthoringContext;

import java.awt.geom.Point2D;
import java.util.List;


public abstract class PathTool extends MapTool{

    protected ObservableList<Path> pathOptions;

    /**
     * These tools have an action in the MapPane on click
     *
     * @param otherMap
     * @param otherScene
     * @param fileName
     */
    public PathTool(AuthoringContext context, MapPane otherMap, Scene otherScene, String fileName, ObservableList<Path> paths) {
        super(context, otherMap, otherScene, fileName);
        pathOptions = paths;
    }

    protected void addPath(Path path){
        String pathName = path.getID();
        List<Point2D> locations = path.getPathLocations();
        getContext().getState().getLevels().get(map.getLevel() - 1).addPath(pathName, locations);
        if(!pathOptions.contains(path)){
            pathOptions.add(path);
        }
    }

    protected boolean containsPath(String pathName){
        return getContext().getState().getLevels().get(map.getLevel() - 1).getPaths().containsKey(pathName);
    }

    protected void removePath(Path path){
        for(Line l: path.getLines()){
            map.removeShape(l);
        }
        for(Circle c: path.getPoints()){
            map.removeShape(c);
        }
        getContext().getState().getLevels().get(map.getLevel() - 1).removePath(path.getID());
        pathOptions.remove(path);
    }

    protected void updatePathLines(Path path){
        for(Line l: path.getLines()){
            map.removeShape(l);
        }
        for(Line l: path.updateLines()){
            map.spawnShape(l);
        }
    }

    protected void removeMouseActions(){
        map.accessMap(node -> node.setOnMousePressed(null));
        map.accessMap(node -> node.setOnMouseDragged(null));
        map.accessMap(node -> node.setOnMouseReleased(null));
    }

    protected Path getPath(String pathName){
        Path pathToRemove = null;
        for(Path path: pathOptions){
            if(path.getID().equals(pathName)){
                pathToRemove = path;
                break;
            }
        }
        return pathToRemove;
    }
}
