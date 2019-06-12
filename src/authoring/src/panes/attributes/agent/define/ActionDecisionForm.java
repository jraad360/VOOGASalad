package panes.attributes.agent.define;

import authoring.IActionDecisionDefinition;
import panes.attributes.LabeledEditableFormList;
import util.AuthoringContext;

import java.util.ArrayList;
import java.util.List;

public class ActionDecisionForm extends LabeledEditableFormList {

    public ActionDecisionForm(AuthoringContext context) {
        super(context, context.getString("ActionDecisions"));

        setOnAdd(e -> {
            ActionDecisionFormElement element = new ActionDecisionFormElement(context);
            add(element);
            element.setOnDelete(e2 -> remove(element));
        });
    }

    /**
     * Packages the data from this form into a list of IActionDecisionDefinition.
     * @return the List<IActionDecisionDefinition> containing the data from this form.
     */
    @Override
    public List<IActionDecisionDefinition> packageData() {
        List<IActionDecisionDefinition> actionDecisions = new ArrayList<>();
        iterateElements(e -> actionDecisions.add(((ActionDecisionFormElement) e).packageData()));
        return actionDecisions;
    }

    public void loadFromExisting(List<? extends IActionDecisionDefinition> actionDecisions) {
        actionDecisions.forEach(actionDecision -> {
            ActionDecisionFormElement element = new ActionDecisionFormElement(getContext());
            element.loadFromExisting(actionDecision);
            add(element);
            element.setOnDelete(e2 -> remove(element));
        });
    }

}
