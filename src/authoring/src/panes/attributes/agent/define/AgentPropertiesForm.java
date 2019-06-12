package panes.attributes.agent.define;

import authoring.IPropertyDefinition;
import panes.attributes.LabeledEditableFormList;
import util.AuthoringContext;

import java.util.ArrayList;
import java.util.List;

public class AgentPropertiesForm extends LabeledEditableFormList {

    public AgentPropertiesForm(AuthoringContext context) {
        super(context, context.getString("Properties"));

        setOnAdd(e -> {
            AgentPropertyFormElement element = new AgentPropertyFormElement(context);
            add(element);
            element.setOnDelete(e2 -> remove(element));
        });
    }

    /**
     * Packages the data from this form into a list of IPropertyDefinition.
     * @return the List<IPropertyDefinition> containing the data from this form.
     */
    @Override
    public List<IPropertyDefinition> packageData() {
        List<IPropertyDefinition> properties = new ArrayList<>();
        iterateElements(e -> properties.add(((AgentPropertyFormElement) e).packageData()));
        return properties;
    }

    public void loadFromExisting(List<? extends IPropertyDefinition> properties) {
        properties.forEach(property -> {
            AgentPropertyFormElement element = new AgentPropertyFormElement(getContext());
            element.loadFromExisting(property);
            add(element);
            element.setOnDelete(e2 -> remove(element));
        });
    }

}
