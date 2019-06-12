package panes;

import authoring.IAgentDefinition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import util.AuthoringContext;
import util.AuthoringUtil;

import java.util.function.BiConsumer;

public class AgentPane extends AuthoringPane {

    private VBox inventoryContainer; // overall VBox
    private HBox buttonPane; // button panes at top of VBox
    private ScrollPane scrollInventory; // scrollpane that contains inventory
    private FlowPane inventory; // the inventory itself inside the scrollpane
    private ImageView trash; // trash for deleting agents from map

    private BiConsumer<MouseEvent, IAgentDefinition> imageAction;
    private BiConsumer<ActionEvent, IAgentDefinition> editAction, copyAction, deleteAction;
    private BiConsumer<Boolean, IAgentDefinition> checkListener;

    public AgentPane(AuthoringContext context) {
        super(context);
        initElements();
        getContentChildren().add(inventoryContainer);
    }

    private void initElements() {
        initInventoryContainer();
        initButtonPane();
        initScrollPane();
        initInventory();
    }

    private void initInventoryContainer() {
        inventoryContainer = new VBox();
        inventoryContainer.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        inventoryContainer.setPrefWidth(getContext().getDouble("AgentPaneWidth"));
        //inventoryContainer.setPrefSize(getContext().getDouble("AgentPaneWidth"), getContext().getDouble("MiddleRowHeight") - getContext().getDouble("MiddleRowPadding"));
        inventoryContainer.getStylesheets().add(getContext().getString("AgentPaneStyle"));
    }

    private void initButtonPane() {
        VBox topVBox = new VBox();
        
        buttonPane = new HBox();
        trash = new ImageView(new Image(getContext().getString("TrashImageFile")));
        trash.setPreserveRatio(true);
        trash.setFitWidth(35);
        trash.setFitHeight(35);
        buttonPane.getChildren().add(trash);
        topVBox.getChildren().add(buttonPane);

        var title = new Text(getContext().getString("Agents"));
        title.setId(getContext().getString("ConsoleTitleStyle"));
        topVBox.getChildren().add(title);

        inventoryContainer.getChildren().add(topVBox);
    }

    private void initScrollPane() {
        scrollInventory = new ScrollPane();
        //scrollInventory.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        //scrollInventory.setPrefViewportWidth(getContext().getDouble("AgentPaneWidth"));
        //scrollInventory.setPrefViewportHeight(getContext().getDouble("MiddleRowHeight") - getContext().getDouble("MiddleRowPadding"));
        scrollInventory.getStyleClass().add(getContext().getString("ScrollPaneStyle"));
        inventoryContainer.getChildren().add(scrollInventory);
    }

    private void initInventory() {
        inventory = new FlowPane();
        inventory.setHgap(2);
        inventory.setVgap(2);
        inventory.setPrefWrapLength(getContext().getDouble("AgentSize") * 2 + inventory.getHgap());
        scrollInventory.setContent(inventory);
    }

    public void setOnImageClicked(BiConsumer<MouseEvent, IAgentDefinition> imageAction) {
        this.imageAction = imageAction;
    }

    public void setOnEdit(BiConsumer<ActionEvent, IAgentDefinition> editAction) {
        this.editAction = editAction;
    }

    public void setOnCopy(BiConsumer<ActionEvent, IAgentDefinition> copyAction) {
        this.copyAction = copyAction;
    }

    public void setOnDelete(BiConsumer<ActionEvent, IAgentDefinition> deleteAction) {
        this.deleteAction = deleteAction;
    }

    /**
     * Sets a listener that fires when a checkbox on an agent in the agent pane is toggled.
     * This checkbox is meant to indicate whether the agent is placeable (available in the store) in this level.
     * @param checkListener a BiConsumer that fires when the checkbox is toggled<br>
     *                      The first parameter, Boolean, is the updated value of the checkbox.<br>
     *                      The second parameter, IAgentDefinition, is the agent on which the toggle occurred.
     */
    public void setOnCheckChanged(BiConsumer<Boolean, IAgentDefinition> checkListener) {
        this.checkListener = checkListener;
    }

    public void refreshAgentList(int level) {
        inventory.getChildren().clear();
        getContext().getState().getDefinedAgents().forEach(agent -> {
            AgentPaneElement newAgent = new AgentPaneElement(getContext(), agent);
            newAgent.accessContainer(inventory.getChildren()::add);
            newAgent.setOnImageClicked(imageAction);
            newAgent.setOnEdit(editAction);
            newAgent.setOnCopy(copyAction);
            newAgent.setOnDelete(deleteAction);
            newAgent.setOnCheckChanged(checkListener);
            newAgent.setChecked(getContext().getState().getLevels().get(level - 1).getPlaceableAgents().contains(agent.getName()));
        });
    }

    public void addButton(String buttonImageName, double buttonSize, EventHandler action){
        Button button = AuthoringUtil.createSquareImageButton(buttonImageName, buttonSize, action);
        buttonPane.getChildren().addAll(button);
    }

    @Override
    public void updateSize(double width, double height) {
        inventoryContainer.setPrefSize(width, height);
        //scrollInventory.setPrefViewportWidth(width);
        //scrollInventory.setPrefViewportHeight(height);
    }
}
