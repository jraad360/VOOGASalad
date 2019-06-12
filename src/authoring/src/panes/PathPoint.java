package panes;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class PathPoint {

    /**
     * Eventhandler is passed in for removal because
     */

    public static final double SIZE = 10;
    //public static final Color COLOR = Color.BLUE;

    private Circle visualPoint;
    private double circleStartX, circleStartY;
    private double dragStartX, dragStartY;

    public PathPoint(double x, double y, Color pointColor){
        visualPoint = new Circle(x, y, SIZE, pointColor);
    }

    public Shape getPoint(){
        return visualPoint;
    }

    public void dragSetup(MouseEvent event){
        dragStartX = event.getSceneX();
        dragStartY = event.getSceneY();
        circleStartX = visualPoint.getCenterX();
        circleStartY = visualPoint.getCenterY();
    }

    public void onDrag(MouseEvent event){
        double offsetX = event.getSceneX() - dragStartX;
        double offsetY = event.getSceneY() - dragStartY;

        visualPoint.setCenterX(circleStartX + offsetX);
        visualPoint.setCenterY(circleStartY + offsetY);
    }

}
