package panes.attributes;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import util.AuthoringContext;

public class LabeledTextField extends FormElement {

    private Label label;
    private TextField textField;

    /**
     * Creates a new form element. It defaults to undeletable by not adding the delete button to a visible node.
     *
     * @param context the context that makes relevant instances available
     * @param labelString the string to label this text box with
     */
    public LabeledTextField(AuthoringContext context, String labelString) {
        super(context);

        HBox hBox = new HBox();
        label = new Label(labelString);
        textField = new TextField();
        hBox.getChildren().addAll(label, textField);

        getContentChildren().add(hBox);
    }

    @Override
    public String packageData() {
        return textField.getText();
    }

    public void loadFromExisting(String text) {
        textField.setText(text);
    }

    public void setLabel(String labelString) {
        label.setText(labelString);
    }

    public String getLabel() {
        return label.getText();
    }

    public void setPromptText(String promptText) {
        textField.setPromptText(promptText);
    }

    public String getPromptText() {
        return textField.getPromptText();
    }
}
