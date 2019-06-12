package panes.attributes.agent.define;

import authoring.IConditionDefinition;
import panes.ConsolePane;
import panes.attributes.NameFieldsFormElement;
import util.AuthoringContext;

public class ConditionFormElement extends NameFieldsFormElement {

    public ConditionFormElement(AuthoringContext context) {
        super(context);

        title.setText(getContext().getString("Condition"));
        populateNames(getContext().getGameFactory().getAvailableConditions());
        accessDeleteButton(headerHBox.getChildren()::add);
    }

    @Override
    public IConditionDefinition packageData() {
        try {
            return getContext().getGameFactory().createCondition(names.getValue(), makeParamsMap());
        } catch (Exception e) {
            getContext().displayConsoleMessage(getContext().getString("ErrorCreatingCondition"), ConsolePane.Level.ERROR);
            return null;
        }
    }

}