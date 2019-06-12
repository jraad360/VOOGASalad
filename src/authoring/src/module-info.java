module authoring {
    requires java.desktop;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires gameengine;

    exports frontend_objects;
    exports panes;
    exports util;
}