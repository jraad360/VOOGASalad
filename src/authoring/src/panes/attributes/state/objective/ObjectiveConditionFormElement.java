package panes.attributes.state.objective;

import authoring.IConditionDefinition;
import authoring.IObjectiveConditionDefinition;
import panes.ConsolePane;
import panes.attributes.NameFieldsFormElement;
import util.AuthoringContext;

public class ObjectiveConditionFormElement extends NameFieldsFormElement {

    public ObjectiveConditionFormElement(AuthoringContext context) {
        super(context);

        title.setText(getContext().getString("Condition"));
        populateNames(getContext().getGameFactory().getAvailableObjectiveConditions());
    }

    @Override
    public IObjectiveConditionDefinition packageData() {
        try {
            return getContext().getGameFactory().createObjectiveCondition(names.getValue(), makeParamsMap());
        } catch (Exception e) {
            e.printStackTrace();
            getContext().displayConsoleMessage(getContext().getString("ErrorCreatingObjectiveCondition"), ConsolePane.Level.ERROR);
            return null;
        }
    }

}