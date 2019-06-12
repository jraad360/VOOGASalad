package panes;

import javafx.event.EventHandler;

public interface IAuthoringPane {
    void setStylesheet(String url);
    void updateSize(double width, double height);
//    void addButton(String label, EventHandler action);
//    void addToParent(Consumer<Node> addMethod);
}
