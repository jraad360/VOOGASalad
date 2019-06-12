package panes.attributes.state.objective;

import authoring.IObjectiveDefinition;
import panes.attributes.LabeledEditableFormList;
import util.AuthoringContext;

import java.util.ArrayList;
import java.util.List;

public class ObjectivesForm extends LabeledEditableFormList {

    public ObjectivesForm(AuthoringContext context) {
        super(context, context.getString("Objectives"));

        setOnAdd(e -> {
            ObjectiveFormElement element = new ObjectiveFormElement(context);
            add(element);
            element.setOnDelete(e2 -> remove(element));
        });
    }

    /**
     * Packages the data from this form into a list of IObjectiveDefinition.
     * @return the List<IObjectiveDefinition> containing the data from this form.
     */
    @Override
    public List<IObjectiveDefinition> packageData() {
        List<IObjectiveDefinition> objectives = new ArrayList<>();
        iterateElements(e -> objectives.add(((ObjectiveFormElement) e).packageData()));
        return objectives;
    }

    public void loadFromExisting(List<? extends IObjectiveDefinition> objectives) {
        objectives.forEach(objective -> {
            ObjectiveFormElement element = new ObjectiveFormElement(getContext());
            element.loadFromExisting(objective);
            add(element);
            element.setOnDelete(e2 -> remove(element));
        });
    }
}
