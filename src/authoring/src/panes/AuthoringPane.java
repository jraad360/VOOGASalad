package panes;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.AuthoringContext;

import java.util.function.Consumer;

public abstract class AuthoringPane implements IAuthoringPane, AccessibleContainer {

    private AuthoringContext context;

    private Pane container;

    private Pane hvbox;
    private boolean vertical;

    private Pane handle;
    private boolean handleEnabled;

    private Pane content;

    public AuthoringPane(AuthoringContext context) {
        this.context = context;

        container = new Pane();
        handle = new Pane();
        content = new Pane();
        setVertical(true);
        setHandleEnabled(false);
    }

    @Override
    public void setStylesheet(String url) {
        content.getStylesheets().clear();
        content.getStylesheets().add(url);
    }

    @Override
    public void updateSize(double width, double height) {
        container.setPrefWidth(width);
        container.setPrefHeight(height);
    }

    @Override
    public void accessContainer(Consumer<Pane> accessMethod) {
        accessMethod.accept(content);
    }

    protected AuthoringContext getContext() {
        return context;
    }

    /**
     * Sets the AuthoringPane to be either in vertical or horizontal orientation, rearranging subcomponents as needed.
     * TODO: Still needs complete implementation and testing
     * @param vertical true to set to vertical, false for horizontal
     */
    protected void setVertical(boolean vertical) {
        if (this.vertical == vertical) {
            return;
        }

        this.vertical = vertical;
        container.getChildren().clear();

        if (vertical) {
            hvbox = new VBox();
            handle.prefWidthProperty().bind(hvbox.prefWidthProperty());
        }
        else {
            hvbox = new HBox();
            handle.prefHeightProperty().bind(hvbox.prefHeightProperty());
        }

        hvbox.getChildren().addAll(handle, content);
        container.getChildren().add(hvbox);
    }

    public boolean isVertical() {
        return vertical;
    }

    protected ObservableList<Node> getContentChildren() {
        return content.getChildren();
    }

    public void setHandleEnabled(boolean handleEnabled) {
        this.handleEnabled = handleEnabled;
        if (handleEnabled) {

        }
        else {

        }
    }

    public boolean isHandleEnabled() {
        return handleEnabled;
    }
}
