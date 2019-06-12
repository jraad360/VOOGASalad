package panes.attributes;

import authoring.IAgentDefinition;
import authoring.IAttributeDefinition;
import authoring.IObjectiveDefinition;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import panes.AuthoringPane;
import panes.ConsolePane;
import panes.attributes.agent.define.DefineAgentForm;
import panes.attributes.agent.instance.EditAgentInstanceForm;
import panes.attributes.state.StateForm;
import state.AgentReference;
import util.AuthoringContext;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AttributesPane extends AuthoringPane {

    private ScrollPane scrollPane;

    public AttributesPane(AuthoringContext context) {
        super(context);
        setStylesheet(getContext().getString("AttributesStyle"));

        VBox vBox = new VBox();
        var title = new Text(getContext().getString("AttributesTitle"));
        title.setId(getContext().getString("ConsoleTitleStyle"));
        vBox.getChildren().add(title);

        scrollPane = new ScrollPane();
        updateSize(getContext().getDouble("AttributesWidth"), getContext().getDouble("MiddleRowHeight"));
        vBox.getChildren().add(scrollPane);

        getContentChildren().add(vBox);
    }

    @Override
    public void updateSize(double width, double height) {
        scrollPane.setPrefViewportWidth(width - getContext().getDouble("AttributesPadding"));
        scrollPane.setPrefViewportHeight(height - getContext().getDouble("AttributesPadding"));
    }

    public double getWidth() {
        return scrollPane.getWidth();
    }

    /**
     * Creates the define new agent form in the attributes pane. Has the option
     * to modify an existing agent, or to create a new agent based off an
     * existing one. If creating a new agent, the form will save to the context
     * state's defined agent list. If modifying an existing agent, the form
     * will update all of the attributes of the existing agent passed in. If
     * an agent is unsuccessfully created, will notify user of problem to fix
     * and will not clear form.
     * @param onSuccess callback for successful agent creation
     * @param existingAgent an existing agent to modify or copy, if null
     *                      creates new agent
     * @param copyExisting if true and existingAgent not null, creates a new
     *                     agent based off of existingAgent
     */
    public void createNewAgentForm(Consumer<IAgentDefinition> onSuccess, IAgentDefinition existingAgent, boolean copyExisting) {
        scrollPane.setContent(null);
        DefineAgentForm defineAgentForm = new DefineAgentForm(getContext());
        if (existingAgent != null) {
            defineAgentForm.loadFromExisting(existingAgent);
        }
        defineAgentForm.accessContainer(scrollPane::setContent);
        defineAgentForm.setOnCancel(e -> scrollPane.setContent(null));
        defineAgentForm.setOnSave(e -> {
            IAgentDefinition a = defineAgentForm.packageData();
            if (a == null) {
                getContext().displayConsoleMessage(getContext().getString("AgentNotCreated"), ConsolePane.Level.ERROR);
                return;
            }
            if (existingAgent != null && copyExisting && getContext().getState().getDefinedAgents().stream().filter(agentDefinition -> agentDefinition.getName().equals(a.getName())).collect(Collectors.toList()).size() > 0) {
                getContext().displayConsoleMessage(String.format(getContext().getString("AgentAlreadyExists"), a.getName()), ConsolePane.Level.ERROR);
                return;
            }
            if (existingAgent != null && !copyExisting) {
                existingAgent.setName(a.getName());
                existingAgent.setWidth(a.getWidth());
                existingAgent.setHeight(a.getHeight());
                existingAgent.setImageURL(a.getImageURL());
                existingAgent.getProperties().clear();
                a.getProperties().forEach(existingAgent::addProperty);
                existingAgent.getActionDecisions().clear();
                a.getActionDecisions().forEach(existingAgent::addActionDecision);
                onSuccess.accept(existingAgent);
            }
            else {
                getContext().getState().addDefinedAgent(a);
                onSuccess.accept(a);
            }
            getContext().displayConsoleMessage(String.format(getContext().getString("AgentDefinitionUpdated"), a.getName()), ConsolePane.Level.SUCCESS);
            scrollPane.setContent(null);
        });
    }

    /**
     * Allows editing of agent instances.
     * @param onSuccess callback for successful agent instance modification
     * @param agentReference the reference to edit
     */
    public void editAgentInstanceForm(Consumer<AgentReference> onSuccess, AgentReference agentReference) {
        scrollPane.setContent(null);
        if (agentReference == null) {
            System.err.println("Agent reference cannot be null when editing.");
            return;
        }
        EditAgentInstanceForm editAgentInstanceForm = new EditAgentInstanceForm(getContext());
        editAgentInstanceForm.loadFromExisting(agentReference);
        editAgentInstanceForm.accessContainer(scrollPane::setContent);
        editAgentInstanceForm.setOnCancel(e -> scrollPane.setContent(null));
        editAgentInstanceForm.setOnSave(e -> {
            AgentReference a = editAgentInstanceForm.packageData();
            if (a == null) {
                getContext().displayConsoleMessage(getContext().getString("AgentInstanceNotModified"), ConsolePane.Level.ERROR);
                return;
            }
            agentReference.setX(a.getX());
            agentReference.setY(a.getY());
            agentReference.setDirection(a.getDirection());
            agentReference.setInstanceProperties(a.getInstanceProperties());
            onSuccess.accept(agentReference);
            getContext().displayConsoleMessage(String.format(getContext().getString("AgentInstanceEdited"), a.getName()), ConsolePane.Level.SUCCESS);
            scrollPane.setContent(null);
        });
    }

    /**
     * Loads the list of objectives and attributes via a state form, and allows adding, editing, and removing of them.
     * @param onSuccess callback for successful modification
     * @param level the current level, 1-indexed
     */
    public void displayStateForm(Runnable onSuccess, int level) {
        scrollPane.setContent(null);
        StateForm stateForm = new StateForm(getContext(), level);
        stateForm.loadFromExisting(getContext().getState().getObjectives(), getContext().getState().getAttributes(), getContext().getState().getLevels().get(level - 1).getAttributes());
        stateForm.accessContainer(scrollPane::setContent);
        stateForm.setOnCancel(e -> scrollPane.setContent(null));
        stateForm.setOnSave(e -> {
            List<List> a = stateForm.packageData();
            if (a.get(0).contains(null) || a.get(1).contains(null) || a.get(2).contains(null)) {
                getContext().displayConsoleMessage(getContext().getString("ObjectivesAttributesNotUpdated"), ConsolePane.Level.ERROR);
                return;
            }
            getContext().getState().getObjectives().clear();
            getContext().getState().getAttributes().clear();
            getContext().getState().getLevels().get(level - 1).getAttributes().clear();
            ((List<IObjectiveDefinition>) a.get(0)).forEach(getContext().getState()::defineObjective);
            ((List<IAttributeDefinition>) a.get(1)).forEach(getContext().getState()::defineAttribute);
            ((List<IAttributeDefinition>) a.get(2)).forEach(getContext().getState().getLevels().get(level - 1)::defineAttribute);
            if (onSuccess != null) {
                onSuccess.run();
            }
            getContext().displayConsoleMessage(getContext().getString("ObjectivesAttributesUpdated"), ConsolePane.Level.SUCCESS);
            scrollPane.setContent(null);
        });
    }

}
