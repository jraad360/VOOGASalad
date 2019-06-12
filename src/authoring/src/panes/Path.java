package panes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * When are paths messed with?
 *
 * Adding paths:
 *      On add -
 *          add initial list of points to level (game engine)
 *          add initial path object to map, pathpane
 * Removing paths:
 *      On remove -
 *          remove list of points from level
 *          remove path from map (shapes) and pathpane (row)
 * Adding/dragging/removing points:
 *      Update list of points in level
 *      Update path object (map, pathpane)
 */

public class Path {

    public static final int FIRST_POINT = 0;
    public static final int MINIMUM_SIZE = 1;
    public static final List<Double> DASH_ARRAY = List.of(2d, 4d);
    public static final Color SELECTED_COLOR = Color.ORCHID;
    public static final Color DEFAULT_COLOR = Color.BLUE;

    /**
     * This object tracks the order and number of points added to a given path.
     * On map click, a point is added to the path at the location of the click.
     * When an existing point is clicked and dragged, rather than adding a new point, the existing point is moved
     *  Set circle's onMouseClick and onMouseDrag to be like a draggable image
     */

    private List<PathPoint> points;
    private List<Line> lines;
    private String pathID;
    private Color pathColor;

    public Path(String id){
        points = new ArrayList<>();
        lines = new ArrayList<>();
        pathID = id;
        pathColor = DEFAULT_COLOR;
    }

    public Path(String id, List<Point2D> pointsLocations){
        this(id);
        for(Point2D location: pointsLocations){
            addPoint(location.getX(), location.getY());
        }
        updateLines();
    }

    public void setStyleSelected(boolean selected){
        if(selected){
            pathColor = SELECTED_COLOR;
        }
        else{
            pathColor = DEFAULT_COLOR;
        }
        for(Circle c: getPoints()){
            c.setStroke(pathColor);
            c.setFill(pathColor);
        }
        for(Line l: lines){
            l.setStroke(pathColor);
            l.setFill(pathColor);
        }
    }

    public String getID(){
        return pathID;
    }

    /**
     * Use to pass information to game engine
     * Possibly replace with a simple call to getPoints and translation of this into Point2Ds
     * TODO: decide method of passing point information to game engine
     * @return
     */
    public List<Point2D> getPathLocations(){
        var list = new ArrayList<Point2D>();
        for(Circle c: getPoints()){
            list.add(new Point2D.Double(c.getCenterX(), c.getCenterY()));
        }
        return list;
    }

    public List<Circle> getPoints(){
        var list = new ArrayList<Circle>();
        for(PathPoint p: points){
            list.add((Circle) p.getPoint());
        }
        return list;
    }

    public PathPoint getPoint(Circle visual){
        for(PathPoint point: points){
            var pointVisual = (Circle) point.getPoint();
            if(pointVisual == visual){
                return point;
            }
        }
        return null;
    }

    public List<Line> getLines(){
        return lines;
    }

    public Circle addPoint(double x, double y){
        var point = new PathPoint(x, y, pathColor);
        var visual = point.getPoint();
        points.add(point);
        return (Circle) visual;
    }

    public void removePoint(PathPoint point){
        if(point != null){
            points.remove(point);
        }
    }

    public List<Line> updateLines(){
        lines = new ArrayList<>();

        int pointsCount = points.size();
        if(pointsCount <= MINIMUM_SIZE){
            return List.of();
        }
        buildLines(lines);
        return lines;
    }

    private void buildLines(List<Line> linesList){
        Circle currentCircle = (Circle) points.get(FIRST_POINT).getPoint();
        Circle previousCircle;

        for(int i=FIRST_POINT + 1; i<points.size(); i++){
            previousCircle = currentCircle;
            currentCircle = (Circle) points.get(i).getPoint();
            var line = new Line(currentCircle.getCenterX(), currentCircle.getCenterY(), previousCircle.getCenterX(), previousCircle.getCenterY());
            line.getStrokeDashArray().addAll(DASH_ARRAY);
            line.setStroke(pathColor);
            linesList.add(line);
        }
    }
}
