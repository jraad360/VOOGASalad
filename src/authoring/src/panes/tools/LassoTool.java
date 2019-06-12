package panes.tools;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import panes.MapPane;
import util.AuthoringContext;

/**
 * Resources used:
 *  https://stackoverflow.com/questions/40629841/circle-not-visible-as-i-drag-the-mouse-in-javafx
 */

public class LassoTool extends MapTool{

    private Ellipse ellipse;
    private double startingPosX, startingPosY;

    public LassoTool(AuthoringContext context, MapPane otherMap, Scene otherScene, String fileName){
        super(context, otherMap, otherScene, fileName);
        initEllipse();
    }

    public void setMouseActions(){
        map.accessMap(node -> node.setOnMouseClicked(e -> onMapClick(e)));
        map.accessMap(node -> node.setOnMouseDragged(e -> mouseDragged(e)));
        map.accessMap(node -> node.setOnMouseReleased(e -> mouseReleased(e)));
    }

    private void initEllipse(){
        ellipse = new Ellipse();
        ellipse.setStrokeWidth(1.0);
        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setStroke(Color.BLACK);
        ellipse.getStrokeDashArray().addAll(3.0);
    }

    private void removeMouseActions(){
        map.accessMap(node -> node.setOnMouseClicked(null));
        map.accessMap(node -> node.setOnMouseDragged(null));
        map.accessMap(node -> node.setOnMouseReleased(null));
    }

    private void mouseReleased(MouseEvent event){
        map.getCurrentState().selectAgents(ellipse);
        map.removeShape((Shape) ellipse);
        startingPosX = 0;
        startingPosY = 0;
        ellipse = null;
        scene.setCursor(Cursor.HAND);
    }

    private void mouseDragged(MouseEvent event){
        map.removeShape((Shape) ellipse);
        ellipse.setCenterX((event.getX() + startingPosX) / 2);
        ellipse.setCenterY((event.getY() + startingPosY) / 2);
        ellipse.setRadiusX(Math.abs((event.getX() - startingPosX) / 2));
        ellipse.setRadiusY(Math.abs((event.getY() - startingPosY) / 2));
        map.spawnShape((Shape) ellipse);
        scene.setCursor(Cursor.CLOSED_HAND);
    }

    @Override
    public void onMapClick(MouseEvent event) {
        initEllipse();
        startingPosX = event.getX();
        startingPosY = event.getY();
        ellipse.setCenterX(startingPosX);
        ellipse.setCenterY(startingPosY);
        ellipse.setRadiusX(0);
        ellipse.setRadiusY(0);
        map.spawnShape((Shape) ellipse);
        scene.setCursor(Cursor.HAND);
    }

    @Override
    protected void enableTool() {
        scene.setCursor(Cursor.HAND);
        setMouseActions();
    }

    @Override
    protected void disableTool() {
        scene.setCursor(Cursor.DEFAULT);
        removeMouseActions();
    }
}
