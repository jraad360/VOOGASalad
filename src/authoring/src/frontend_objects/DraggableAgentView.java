package frontend_objects;

import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import panes.ConsolePane;
import panes.MapPane;
import state.AgentReference;
import util.AuthoringContext;
import util.AuthoringUtil;

import java.util.stream.Collectors;

public class DraggableAgentView extends AgentView {

    /**
     * Superclass for all frontend_objects.AuthorView objects intended to be dragged into the map
     * Enables ImageViews to be placeable in the map (or draggable)
     * Subclasses must implement their own version of mouseReleased
     *  Ex. if a class wants the object to return to the initial position when the imageview is dropped:
     *      @Override
     *     public void mouseReleased(MouseEvent event){
     *         ((ImageView)(event.getSource())).setTranslateX(getStartX());
     *         ((ImageView)(event.getSource())).setTranslateY(getStartY());
     *     }
     * Resource consulted for draggable images: http://java-buddy.blogspot.com/2013/07/javafx-drag-and-move-something.html
     * @author Mary Stuart Elder and Eric Lin
     */

    private double myStartSceneX, myStartSceneY;
    private double myStartXOffset, myStartYOffset;
    private AgentReference reference;
    private boolean selected;
    private String url;

    public DraggableAgentView(String imgUrl){
        super(imgUrl);
    }
/*
    public DraggableAgentView(AuthoringContext authoringContext, CloneableAgentView agent) {
        super(authoringContext, agent.getUrl());
        url = agent.getUrl();
    }*/
    public DraggableAgentView(AuthoringContext authoringContext, String url, AgentReference reference) {
        super(authoringContext, url);
        this.url = url;
        this.reference = reference;
        this.setTranslateX(reference.getX());
        this.setTranslateY(reference.getY());
        selected = false;
    }

    public DraggableAgentView(AuthoringContext authoringContext, AgentReference reference) {
        this(authoringContext, AuthoringUtil.switchAuthorEngineURL(authoringContext.getState().getDefinedAgents().stream().filter(agentDefinition -> agentDefinition.getName().equals(reference.getName())).collect(Collectors.toList()).get(0).getImageURL(), true), reference);
    }

    public DraggableAgentView(AuthoringContext authoringContext, DraggableAgentView other) {
        super(authoringContext, other.url);
        url = other.url;
        selected = false;
        this.reference = other.reference;
        myStartSceneX = other.myStartSceneX;
        myStartSceneY = other.myStartSceneY;
        myStartXOffset = other.myStartXOffset;
        myStartYOffset = other.myStartYOffset;
    }

    public AgentReference getReference() {
        return reference;
    }


    public void setMouseActionsForDrag(MapPane map) {
        this.setOnMousePressed(mouseEvent -> mousePressed(mouseEvent));
        this.setOnMouseDragged(mouseEvent -> mouseDragged(mouseEvent, map));
        this.setOnMouseReleased(mouseEvent -> mouseReleased(map));
    }

    public void mousePressed(MouseEvent event) {
        myStartSceneX = event.getSceneX();
        myStartSceneY = event.getSceneY();
        myStartXOffset = getTranslateX();
        myStartYOffset = getTranslateY();
    }

    public void mouseDragged(MouseEvent event, MapPane map) {
        double offsetX = event.getSceneX() - myStartSceneX;
        double offsetY = event.getSceneY() - myStartSceneY;
        double newTranslateX = myStartXOffset + offsetX;
        double newTranslateY = myStartYOffset + offsetY;
        setTranslateX(newTranslateX);
        setTranslateY(newTranslateY);
        reference.setX(newTranslateX);
        reference.setY(newTranslateY);
        if (trashIntersect(map)) {
            setEffect(setLighting(Color.RED));
        } else if (outOfBounds()) {
            setEffect(setLighting(Color.WHITE));
        } else {
            setEffect(null);
        }
    }

    private Lighting setLighting(Color color) {
        Lighting lighting = new Lighting();
        lighting.setDiffuseConstant(1.0);
        lighting.setSpecularConstant(0.0);
        lighting.setSpecularExponent(0.0);
        lighting.setSurfaceScale(0.0);
        lighting.setLight(new Light.Distant(getContext().getDouble("LightingConstant"), getContext().getDouble("LightingConstant"), color));
        return lighting;
    }

    public void mouseReleased(MapPane map) {
        if (trashIntersect(map)) {
            map.removeAgent(this);
            getContext().getState().getLevels().get(map.getLevel() - 1).getCurrentAgents().remove(reference);
            getContext().displayConsoleMessage(getContext().getString("AgentRemoved") + map.getAgentCount(), ConsolePane.Level.NEUTRAL);
        } else if (outOfBounds()) {
            setEffect(null);
            setTranslateX(myStartXOffset);
            setTranslateY(myStartYOffset);
            reference.setX(myStartXOffset);
            reference.setY(myStartYOffset);
            getContext().displayConsoleMessage(getContext().getString("AgentOutOfBounds"), ConsolePane.Level.NEUTRAL);
        }
    }

    private boolean outOfBoundsHorizontal() {
        double xPos = getTranslateX();
        double xPosRight = getTranslateX() + getFitWidth();
        boolean rightOutOfBounds = xPosRight > getContext().getDouble("InsetMapWidth");
        boolean leftOutOfBounds = xPos < 0;
        return leftOutOfBounds || rightOutOfBounds;
    }

    private boolean outOfBoundsVertical() {
        double yPos = getTranslateY();
        double yPosBot = getTranslateY() + getFitHeight();
        boolean topOutOfBounds = yPos < 0;
        boolean botOutOfBounds = yPosBot > getContext().getDouble("InsetMapHeight");
        return topOutOfBounds || botOutOfBounds;
    }

    private boolean outOfBounds() {
        return outOfBoundsHorizontal() || outOfBoundsVertical();
    }

    private boolean trashIntersect(MapPane map) {
        double yPos = getTranslateY();
        double xPosRight = getTranslateX() + getFitWidth();
        boolean topOutOfBounds = yPos < 0;
        boolean rightOutOfBounds =  xPosRight > getContext().getDouble("InsetMapWidth") + (map.getPaneWidth() - getContext().getDouble("InsetMapWidth"))/2;
        return topOutOfBounds && rightOutOfBounds;
    }

    private void setImageOpacity(){
        // Translucent if selected
        var opacity = 0.3;
        int selectAddition = selected ? 0 : 1;
        var select = 0.7*selectAddition + opacity;
        this.setStyle("-fx-opacity: " + select + ";");
    }

    public void setSelect(boolean select){
        selected = select;
        setImageOpacity();
    }

    public boolean getSelect() {
        return selected;
    }

    public void syncWithReference() {
        setTranslateX(reference.getX());
        setTranslateY(reference.getY());
    }

}
