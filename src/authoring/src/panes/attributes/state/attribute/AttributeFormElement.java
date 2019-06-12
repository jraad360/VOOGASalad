package panes.attributes.state.attribute;

import authoring.IAttributeDefinition;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import panes.ConsolePane;
import panes.attributes.FormElement;
import util.AuthoringContext;

public class AttributeFormElement extends FormElement {

    private TextField name, value;

    public AttributeFormElement(AuthoringContext context) {
        super(context);
        init();
    }

    private void init() {
        HBox hBox = new HBox();
        getContentChildren().add(hBox);

        name = new TextField();
        name.setPromptText(getContext().getString("Name"));

        value = new TextField();
        value.setPromptText(getContext().getString("double"));

        hBox.getChildren().addAll(name, value);
        accessDeleteButton(hBox.getChildren()::add);
    }

    @Override
    public IAttributeDefinition packageData() {
        try {
            return getContext().getGameFactory().createAttribute(name.getText(), Double.parseDouble(value.getText()));
        } catch (NumberFormatException e) {
            getContext().displayConsoleMessage(getContext().getString("AttributeValueMustBeDouble"), ConsolePane.Level.ERROR);
        } catch (Exception e) {
            getContext().displayConsoleMessage(getContext().getString("ErrorCreatingAttribute"), ConsolePane.Level.ERROR);
            return null;
        }
        return null;
    }

    public void loadFromExisting(IAttributeDefinition attribute) {
        name.setText(attribute.getName());
        value.setText("" + attribute.getValue());
    }
}
