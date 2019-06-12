package panes.attributes.state;

import authoring.IAttributeDefinition;
import authoring.IObjectiveDefinition;
import javafx.scene.control.Label;
import panes.attributes.TitledSaveableFormElement;
import panes.attributes.state.attribute.AttributesForm;
import panes.attributes.state.objective.ObjectivesForm;
import util.AuthoringContext;

import java.util.List;

public class StateForm extends TitledSaveableFormElement {

    private ObjectivesForm objectivesForm;
    private AttributesForm globalAttributesForm, levelAttributesForm;

    public StateForm(AuthoringContext context, int level) {
        super(context);
        init(level);
    }

    private void init(int level) {
        objectivesForm = new ObjectivesForm(getContext());
        objectivesForm.accessContainer(container -> accessVBox(vBox -> vBox.getChildren().add(container)));

        accessVBox(vBox -> vBox.getChildren().add(new Label("")));

        globalAttributesForm = new AttributesForm(getContext(), getContext().getString("GlobalAttributes"));
        globalAttributesForm.accessContainer(container -> accessVBox(vBox -> vBox.getChildren().add(container)));

        accessVBox(vBox -> vBox.getChildren().add(new Label("")));

        levelAttributesForm = new AttributesForm(getContext(), String.format(getContext().getString("LevelXAttributes"), level));
        levelAttributesForm.accessContainer(container -> accessVBox(vBox -> vBox.getChildren().add(container)));
    }

    public void loadFromExisting(List<? extends IObjectiveDefinition> objectives,
                                 List<? extends IAttributeDefinition> globalAttributes,
                                 List<? extends IAttributeDefinition> levelAttributes) {
        objectivesForm.loadFromExisting(objectives);
        globalAttributesForm.loadFromExisting(globalAttributes);
        levelAttributesForm.loadFromExisting(levelAttributes);
    }

    /**
     * Packages the data from this form into a list of the three lists (objectives, global attributes, level attributes).
     * @return the list containing the data from this form - ( List<IObjectiveDefinition>, List<IAttributeDefinition>, List<IAttributeDefinition> )
     */
    @Override
    public List<List> packageData() {
        return List.of(objectivesForm.packageData(), globalAttributesForm.packageData(), levelAttributesForm.packageData());
    }

}
