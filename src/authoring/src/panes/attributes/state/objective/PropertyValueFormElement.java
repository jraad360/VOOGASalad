package panes.attributes.state.objective;

import authoring.IPropertyDefinition;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import panes.ConsolePane;
import panes.attributes.FormElement;
import util.AuthoringContext;

import java.util.List;

public class PropertyValueFormElement extends FormElement {

    private static final List<String> DEFAULT_TYPES = List.of("Integer", "Double", "String");

    private Label name;
    private ChoiceBox<String> typeBox;
    private TextField valueField;

    public PropertyValueFormElement(AuthoringContext context, String name) {
        this(context, name, DEFAULT_TYPES, "", null);
    }

    public PropertyValueFormElement(AuthoringContext context, String name, List<String> types, String value, EventHandler onDelete) {
        super(context);
        init(name, types, value);
        setOnDelete(onDelete);
    }

    /**
     * Packages the data from this property form element into a comparable type.
     * @return the comparable type containing the data from this property form element.
     */
    @Override
    public Object packageData() {
        String type = typeBox.getSelectionModel().getSelectedItem();
        if (type == null) {
            return null;
        }
        if (type.equals(DEFAULT_TYPES.get(getContext().getInt("IntIndex")))) {
            try {
                return Integer.parseInt(valueField.getText());
            } catch (NumberFormatException e) {
                getContext().displayConsoleMessage(String.format(getContext().getString("PropertyMustBeOfType"), name, type), ConsolePane.Level.ERROR);
                return null;
            }
        }
        else if (type.equals(DEFAULT_TYPES.get(getContext().getInt("DoubleIndex")))) {
            try {
                return Double.parseDouble(valueField.getText());
            } catch (NumberFormatException e) {
                getContext().displayConsoleMessage(String.format(getContext().getString("PropertyMustBeOfType"), name, type), ConsolePane.Level.ERROR);
                return null;
            }
        }
        else if (type.equals(DEFAULT_TYPES.get(getContext().getInt("StringIndex")))) {
            return valueField.getText();
        }
        return null;
    }

    public String getName() {
        return name.getText();
    }

    private void init(String nameString, List<String> types, String value) {
        HBox propertyHBox = new HBox();

        name = new Label(nameString);
        propertyHBox.getChildren().add(name);

        typeBox = new ChoiceBox<>();
        typeBox.getItems().addAll(types);
        typeBox.getSelectionModel().selectFirst();
        propertyHBox.getChildren().add(typeBox);

        valueField = new TextField(value);
        valueField.setPromptText(getContext().getString("Value"));
        propertyHBox.getChildren().add(valueField);

        getContentChildren().add(propertyHBox);
    }

    public void loadFromExisting(Object valueType) {
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
