package panes.attributes;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.AuthoringContext;

import java.util.function.Consumer;

public abstract class TitledSaveableFormElement extends FormElement {

    private VBox vBox;
    private Label title;
    private Button saveButton, cancelButton;

    public TitledSaveableFormElement(AuthoringContext context) {
        super(context);

        vBox = new VBox();
        getContentChildren().add(vBox);

        title = new Label();
        vBox.getChildren().add(title);

        saveButton = new Button(getContext().getString("Save"));
        cancelButton = new Button(getContext().getString("Cancel"));

        HBox buttonsHBox = new HBox();
        buttonsHBox.getChildren().addAll(saveButton, cancelButton);
        vBox.getChildren().add(buttonsHBox);

        vBox.getChildren().add(new Label(""));
    }

    /**
     * Access the VBox from outside of this class.
     * @param accessMethod the Consumer that allows accessing of the VBox
     */
    protected void accessVBox(Consumer<VBox> accessMethod) {
        accessMethod.accept(vBox);
    }

    public void setTitle(String titleString) {
        title.setText(titleString);
    }

    public String getTitle() {
        return title.getText();
    }

    public void setOnSave(EventHandler onSave) {
        saveButton.setOnAction(onSave);
    }

    public void setOnCancel(EventHandler onCancel) {
        cancelButton.setOnAction(onCancel);
    }
}
