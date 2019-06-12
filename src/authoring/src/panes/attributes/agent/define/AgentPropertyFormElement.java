package panes.attributes.agent.define;

import authoring.IPropertyDefinition;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import panes.ConsolePane;
import panes.attributes.FormElement;
import util.AuthoringContext;

import java.util.List;

public class AgentPropertyFormElement extends FormElement {

    // TODO: if possible, find alternative to this list
    private static final List<String> DEFAULT_TYPES = List.of("Integer", "Double", "String");

    private TextField nameField;
    private ChoiceBox<String> typeBox;
    private TextField valueField;

    public AgentPropertyFormElement(AuthoringContext context) {
        this(context, "", DEFAULT_TYPES, "", null);
    }

    public AgentPropertyFormElement(AuthoringContext context, String name, List<String> types, String value, EventHandler onDelete) {
        super(context);
        init(name, types, value);
        setOnDelete(onDelete);
    }

    /**
     * Packages the data from this property form element into an IPropertyDefinition.
     * @return the IPropertyDefinition containing the data from this property form element.
     */
    @Override
    public IPropertyDefinition packageData() {
        String name = nameField.getText();
        if (name.isBlank()) {
            getContext().displayConsoleMessage(getContext().getString("PropertyNameCannotBeBlank"), ConsolePane.Level.ERROR);
            return null;
        }
        String type = typeBox.getSelectionModel().getSelectedItem();
        if (type == null) {
            return null;
        }
        if (type.equals(DEFAULT_TYPES.get(getContext().getInt("IntIndex")))) {
            try {
                int value = Integer.parseInt(valueField.getText());
                return getContext().getGameFactory().createProperty(name, value);
            } catch (NumberFormatException e) {
                getContext().displayConsoleMessage(String.format(getContext().getString("PropertyMustBeOfType"), name, type), ConsolePane.Level.ERROR);
                return null;
            }
        }
        else if (type.equals(DEFAULT_TYPES.get(getContext().getInt("DoubleIndex")))) {
            try {
                double value = Double.parseDouble(valueField.getText());
                return getContext().getGameFactory().createProperty(name, value);
            } catch (NumberFormatException e) {
                getContext().displayConsoleMessage(String.format(getContext().getString("PropertyMustBeOfType"), name, type), ConsolePane.Level.ERROR);
                return null;
            }
        }
        else if (type.equals(DEFAULT_TYPES.get(getContext().getInt("StringIndex")))) {
            return getContext().getGameFactory().createProperty(name, valueField.getText());
        }
        return null;
    }

    private void init(String name, List<String> types, String value) {
        HBox propertyHBox = new HBox();

        nameField = new TextField(name);
        nameField.setPromptText(getContext().getString("Name"));
        propertyHBox.getChildren().add(nameField);

        typeBox = new ChoiceBox<>();
        typeBox.getItems().addAll(types);
        typeBox.getSelectionModel().selectFirst();
        propertyHBox.getChildren().add(typeBox);

        valueField = new TextField(value);
        valueField.setPromptText(getContext().getString("Value"));
        propertyHBox.getChildren().add(valueField);

        accessDeleteButton(propertyHBox.getChildren()::add);

        getContentChildren().add(propertyHBox);
    }

    public void loadFromExisting(IPropertyDefinition property) {
        nameField.setText(property.getName());
        Object valueType = property.getValue();
        if (valueType instanceof Integer) {
            typeBox.getSelectionModel().select(DEFAULT_TYPES.get(getContext().getInt("IntIndex")));
        }
        else if (valueType instanceof Double) {
            typeBox.getSelectionModel().select(DEFAULT_TYPES.get(getContext().getInt("DoubleIndex")));
        }
        else if (valueType instanceof String) {
            typeBox.getSelectionModel().select(DEFAULT_TYPES.get(getContext().getInt("StringIndex")));
        }
        valueField.setText("" + valueType);
    }
}
