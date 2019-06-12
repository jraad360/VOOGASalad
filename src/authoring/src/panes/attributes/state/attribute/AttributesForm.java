package panes.attributes.state.attribute;

import authoring.IAttributeDefinition;
import panes.attributes.LabeledEditableFormList;
import util.AuthoringContext;

import java.util.ArrayList;
import java.util.List;

public class AttributesForm extends LabeledEditableFormList {

    public AttributesForm(AuthoringContext context, String label) {
        super(context, label);

        setOnAdd(e -> {
            AttributeFormElement element = new AttributeFormElement(context);
            add(element);
            element.setOnDelete(e2 -> remove(element));
        });
    }

    /**
     * Packages the data from this form into a list of IAttributeDefinition.
     * @return the List<IAttributeDefinition> containing the data from this form.
     */
    @Override
    public List<IAttributeDefinition> packageData() {
        List<IAttributeDefinition> attributes = new ArrayList<>();
        iterateElements(e -> attributes.add(((AttributeFormElement) e).packageData()));
        return attributes;
    }

    public void loadFromExisting(List<? extends IAttributeDefinition> attributes) {
        attributes.forEach(attribute -> {
            AttributeFormElement element = new AttributeFormElement(getContext());
            element.loadFromExisting(attribute);
            add(element);
            element.setOnDelete(e2 -> remove(element));
        });
    }
}
