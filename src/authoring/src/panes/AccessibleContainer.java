package panes;

import javafx.scene.layout.Pane;

import java.util.function.Consumer;

public interface AccessibleContainer {
    void accessContainer(Consumer<Pane> accessMethod);
}
