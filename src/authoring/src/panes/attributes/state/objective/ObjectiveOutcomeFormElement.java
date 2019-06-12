package panes.attributes.state.objective;

import authoring.IConditionDefinition;
import authoring.IObjectiveOutcomeDefinition;
import panes.ConsolePane;
import panes.attributes.NameFieldsFormElement;
import util.AuthoringContext;

public class ObjectiveOutcomeFormElement extends NameFieldsFormElement {

    public ObjectiveOutcomeFormElement(AuthoringContext context) {
        super(context);

        title.setText(getContext().getString("Outcome"));
        populateNames(getContext().getGameFactory().getAvailableObjectiveOutcomes());
    }

    @Override
    public IObjectiveOutcomeDefinition packageData() {
        try {
            return getContext().getGameFactory().createObjectiveOutcome(names.getValue(), makeParamsMap());
        } catch (Exception e) {
            e.printStackTrace();
            getContext().displayConsoleMessage(getContext().getString("ErrorCreatingObjectiveOutcome"), ConsolePane.Level.ERROR);
            return null;
        }
    }

}