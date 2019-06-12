package panes;

import authoring.IAgentDefinition;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.AuthoringContext;
import util.AuthoringUtil;

import java.util.function.BiConsumer;

/**
 * @author Samuel Rabinowitz
 */
public class AgentPaneElement extends AuthoringPane {

    private IAgentDefinition agent;

    private VBox overallVBox;
    private ImageView image;
    private Label name;

    private HBox controlsHBox;
    private Menu optionsMenu;
    private MenuItem editMenuItem;
    private MenuItem copyMenuItem;
    private MenuItem deleteMenuItem;
    private CheckBox checkBox;

    public AgentPaneElement(AuthoringContext authoringContext, IAgentDefinition agent) {
        super(authoringContext);
        this.agent = agent;
        init();
    }

    private void init() {
        overallVBox = new VBox();
        getContentChildren().add(overallVBox);

        image = new ImageView(AuthoringUtil.switchAuthorEngineURL(agent.getImageURL(), true));
        image.setFitWidth(getContext().getDouble("AgentSize"));
        image.setFitHeight(image.getFitWidth());
        image.setId(getContext().getString("ButtonStyle"));
        overallVBox.getChildren().add(image);

        name = new Label(agent.getName());
        overallVBox.getChildren().add(name);

        // Controls
        controlsHBox = new HBox();
        overallVBox.getChildren().add(controlsHBox);

        optionsMenu = new Menu(getContext().getString("Options"));
        editMenuItem = new MenuItem(getContext().getString("Edit"));
        copyMenuItem = new MenuItem(getContext().getString("Copy"));
        deleteMenuItem = new MenuItem(getContext().getString("Delete"));
        optionsMenu.getItems().addAll(editMenuItem, copyMenuItem, deleteMenuItem);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(optionsMenu);
        controlsHBox.getChildren().add(menuBar);

        checkBox = new CheckBox();
        checkBox.setAllowIndeterminate(false);
        checkBox.setPrefWidth(18);
        checkBox.setPrefHeight(18);
        controlsHBox.getChildren().add(checkBox);
    }

    public void setOnImageClicked(BiConsumer<MouseEvent, IAgentDefinition> imageAction) {
        image.setOnMouseClicked(e -> imageAction.accept(e, agent));
    }

    public void setOnEdit(BiConsumer<ActionEvent, IAgentDefinition> editAction) {
        editMenuItem.setOnAction(e -> editAction.accept(e, agent));
    }

    public void setOnCopy(BiConsumer<ActionEvent, IAgentDefinition> copyAction) {
        copyMenuItem.setOnAction(e -> copyAction.accept(e, agent));
    }

    public void setOnDelete(BiConsumer<ActionEvent, IAgentDefinition> deleteAction) {
        deleteMenuItem.setOnAction(e -> deleteAction.accept(e, agent));
    }

    public void setOnCheckChanged(BiConsumer<Boolean, IAgentDefinition> checkListener) {
        checkBox.selectedProperty().addListener(((observableValue, oldValue, newValue) -> checkListener.accept(newValue, agent)));
    }

    public void setChecked(boolean checked) {
        checkBox.setSelected(checked);
    }

}
