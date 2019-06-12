package panes.attributes.agent.instance;

import authoring.IActionDecisionDefinition;
import authoring.IAgentDefinition;
import authoring.IPropertyDefinition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import panes.ConsolePane;
import panes.attributes.FormElement;
import panes.attributes.TitledSaveableFormElement;
import panes.attributes.agent.define.AgentPropertiesForm;
import state.AgentReference;
import state.Property;
import util.AuthoringContext;

import java.util.ArrayList;
import java.util.List;

public class EditAgentInstanceForm extends TitledSaveableFormElement {

    private CommonAgentInstanceFieldsForm commonAgentInstanceFieldsForm;
    private AgentPropertiesForm agentPropertiesForm;

    public EditAgentInstanceForm(AuthoringContext context) {
        super(context);
        init();
    }

    private void init() {
        commonAgentInstanceFieldsForm = new CommonAgentInstanceFieldsForm(getContext());
        commonAgentInstanceFieldsForm.accessContainer(container -> accessVBox(vBox -> vBox.getChildren().add(container)));

        accessVBox(vBox -> vBox.getChildren().add(new Label("")));

        agentPropertiesForm = new AgentPropertiesForm(getContext());
        agentPropertiesForm.accessContainer(container -> accessVBox(vBox -> vBox.getChildren().add(container)));
    }

    public void loadFromExisting(AgentReference ref) {
        commonAgentInstanceFieldsForm.loadFromExisting(ref.getName(), ref.getX(), ref.getY(), ref.getDirection());
        agentPropertiesForm.loadFromExisting(ref.getInstanceProperties());
    }

    /**
     * Packages the data from this form into an IAgentDefinition.
     * @return the IAgentDefinition containing the data from this form.
     */
    @Override
    public AgentReference packageData() {
        /*if (commonAgentInstanceFieldsForm.getName().equals("")) {
            getContext().displayConsoleMessage(getContext().getString("AgentNeedsName"), ConsolePane.Level.ERROR);
            return null;
        }*/
        if (commonAgentInstanceFieldsForm.getX() == getContext().getDouble("ErrorDouble")) {
            getContext().displayConsoleMessage(getContext().getString("XMustBeDouble"), ConsolePane.Level.ERROR);
            return null;
        }
        if (commonAgentInstanceFieldsForm.getY() == getContext().getDouble("ErrorDouble")) {
            getContext().displayConsoleMessage(getContext().getString("YMustBeDouble"), ConsolePane.Level.ERROR);
            return null;
        }
        if (commonAgentInstanceFieldsForm.getDirection() == getContext().getDouble("ErrorDouble")) {
            getContext().displayConsoleMessage(getContext().getString("DirectionMustBeDouble"), ConsolePane.Level.ERROR);
            return null;
        }
        List<? extends IPropertyDefinition> properties = agentPropertiesForm.packageData();
        if (properties.contains(null)) {
            return null;
        }
        return new AgentReference(
                commonAgentInstanceFieldsForm.getName(),
                commonAgentInstanceFieldsForm.getX(),
                commonAgentInstanceFieldsForm.getY(),
                commonAgentInstanceFieldsForm.getDirection(),
                (List<Property>) properties);
    }
}
