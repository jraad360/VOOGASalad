package panes.attributes;

import authoring.AvailableNameFields;
import authoring.Field;
import authoring.INameFieldsDefinition;
import authoring.IPropertyDefinition;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import panes.ConsolePane;
import panes.attributes.FormElement;
import panes.attributes.LabeledTextField;
import panes.attributes.agent.define.AgentPropertiesForm;
import panes.attributes.state.objective.PropertyValueFormElement;
import util.AuthoringContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class NameFieldsFormElement extends FormElement {

    protected HBox headerHBox;
    protected Label title;
    protected ChoiceBox<String> names;
    protected VBox parametersVBox;
    protected List<FormElement> parameters;
    // TODO: Make this whole shebang work with more than only parameters that are strings

    protected List<? extends AvailableNameFields> nameFields;

    /**
     * Creates a new name field form element has a name, a dropdown, and a list of parameters.
     * Is front-end version of the game engine's AvailableNameFields class.
     *
     * @param context the context that makes relevant instances available
     */
    public NameFieldsFormElement(AuthoringContext context) {
        super(context);

        VBox overallVBox = new VBox();

        // Header
        headerHBox = new HBox();
        title = new Label();
        names = new ChoiceBox<>();
        headerHBox.getChildren().addAll(title, names);
        overallVBox.getChildren().add(headerHBox);

        // Parameters
        overallVBox.getChildren().add(new Label(getContext().getString("Parameters")));
        parametersVBox = new VBox();
        parameters = new ArrayList<>();
        overallVBox.getChildren().add(parametersVBox);

        getContentChildren().add(overallVBox);
    }

    protected void populateNames(List<? extends AvailableNameFields> nameFields) {
        this.nameFields = nameFields;
        this.nameFields.forEach((nameField) -> names.getItems().add(nameField.getName()));
        names.getSelectionModel().selectedItemProperty().addListener((observable, oldString, newString) -> {
            updateParameters();
        });
        names.getSelectionModel().selectFirst();
    }

    private void updateParameters() {
        parametersVBox.getChildren().clear();
        parameters.clear();

        String selectedName = names.getValue();
        AvailableNameFields selectedNameField = nameFields.stream().filter(p -> p.getName().equals(selectedName)).collect(Collectors.toList()).get(0);

        for (Field f : selectedNameField.getFields()) {
            // TODO: Load more than just text fields as parameter entry options
            FormElement field;
            switch (f.getType()) {
                case "int":
                case "double":
                case "string":
                    field = new LabeledTextField(getContext(), f.getName());
                    ((LabeledTextField) field).setPromptText(f.getType());
                    break;
                case "T":
                case "comparable":
                    field = new PropertyValueFormElement(getContext(), f.getName());
                    break;
                case "propertyList":
                    field = new AgentPropertiesForm(getContext());
                    break;
                default:
                    getContext().displayConsoleMessage(getContext().getString("ErrorLoadingParameters"), ConsolePane.Level.ERROR);
                    return;
            }

            parameters.add(field);
            field.accessContainer(parametersVBox.getChildren()::add);
        }
    }

    public void addSelectedNameListener(ChangeListener<? super String> changeListener) {
        names.getSelectionModel().selectedItemProperty().addListener(changeListener);
        int index = names.getSelectionModel().getSelectedIndex();
        names.getSelectionModel().selectLast();
        names.getSelectionModel().selectFirst();
        names.getSelectionModel().select(index);
    }

    protected Map<String, Object> makeParamsMap() {
        Map<String, Object> paramsMap = new HashMap<>();
        for (FormElement e : parameters) {
            if (e instanceof LabeledTextField) {
                LabeledTextField p = (LabeledTextField) e;
                switch (p.getPromptText()) {
                    case "int":
                        paramsMap.put(p.getLabel(), Integer.parseInt(p.packageData()));
                        break;
                    case "double":
                        paramsMap.put(p.getLabel(), Double.parseDouble(p.packageData()));
                        break;
                    case "string":
                        paramsMap.put(p.getLabel(), p.packageData());
                        break;
                }
            }
            else if (e instanceof PropertyValueFormElement) {
                if (e.packageData() == null) {
                    getContext().displayConsoleMessage(getContext().getString("ErrorSavingPropertyValue"), ConsolePane.Level.ERROR);
                    return null;
                }
                else {
                    paramsMap.put(((PropertyValueFormElement) e).getName(), e.packageData());
                }
            }
            else if (e instanceof AgentPropertiesForm) {
                paramsMap.put("properties", e.packageData());
            }
        }
        return paramsMap;
    }

    public void loadFromExisting(INameFieldsDefinition definition) {
        names.getSelectionModel().select(definition.getName());
        definition.getParams().forEach((name, object) -> {
            parameters.forEach(e -> {
                if (e instanceof LabeledTextField) {
                    LabeledTextField p = (LabeledTextField) e;
                    if (p.getLabel().equals(name)) {
                        p.loadFromExisting(object.toString());
                    }
                }
                else if (e instanceof PropertyValueFormElement) {
                    PropertyValueFormElement p = (PropertyValueFormElement) e;
                    if (p.getName().equals(name)) {
                        p.loadFromExisting(object);
                    }
                }
                else if (e instanceof AgentPropertiesForm) {
                    AgentPropertiesForm p = (AgentPropertiesForm) e;
                    if ("properties".equals(name)) {
                        p.loadFromExisting((List<? extends IPropertyDefinition>) object);
                    }
                }
            });
        });
    }
}
