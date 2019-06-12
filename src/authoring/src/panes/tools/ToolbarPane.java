package panes.tools;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import panes.AuthoringEnvironment;
import panes.AuthoringPane;
import panes.Path;
import util.AuthoringContext;
import util.AuthoringUtil;
import panes.MapPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class ToolbarPane extends AuthoringPane {

    /**
     * Want it so the public method called from AuthoringEnvironment gives an EventHandler for Button to Toolbar, then Toolbar sets the button action to this eventHandler AND the toggle method of the tool
     * The AuthoringEnvironment is responsible for giving the Map and Scene to the ToolbarPane when needed for Tools
     */

    private MenuBar menuBar;
    private ToolBar toolBar;
    private ComboBox<Integer> existingLevelCreator;
    private Spinner levelChanger;
    private Button addEmpty;
    private Button addExisting;
    private Button clearCurrent;
    private int maxLevel;
    private Map<String, Menu> menuMap;
    private VBox box;
    private Map<String, Tool> toolImageMap;
    private ObservableList<Path> pathOptions;

    private MapPane map;
    private Scene scene;

    public ToolbarPane(AuthoringContext context, MapPane authorMap, Scene authorScene, ObservableList<Path> paths){
        super(context);
        maxLevel = getContext().getInt("InitialLevel");
        map = authorMap;
        scene = authorScene;
        menuMap = new HashMap<>();
        toolImageMap = new HashMap<>();
        pathOptions = paths;
        initBars();
        initLevelTools();
    }

    private void initBars(){
        menuBar = initMenuBar();
        toolBar = initToolBar();
        toolBar.setId(getContext().getString("ToolbarStyle"));
        box = new VBox();
        box.getStylesheets().add(getContext().getString("ToolbarPaneStyle"));
        box.getChildren().addAll(menuBar, toolBar);
        box.setPrefSize(getContext().getDouble("DefaultWidth"), getContext().getDouble("ToolbarPaneHeight"));
        getContentChildren().add(box);
    }

    private MenuBar initMenuBar(){
        var menu = new MenuBar();
        for(String option: getContext().getString("MenuOptions").split(",")){
            var menuOption = new Menu(option);
            menu.getMenus().add(menuOption);
            menuMap.put(option, menuOption);
        }
        return menu;
    }

    private ToolBar initToolBar(){
        var toolbar = new ToolBar();
        toolbar.setPrefSize(getContext().getDouble("DefaultWidth"), getContext().getDouble("ToolbarHeight"));

        var lasso = new LassoTool(getContext(), map, scene, getContext().getString("LassoFile"));
        var pen = new PathPenTool(getContext(), map, scene, getContext().getString("PenFile"), pathOptions);
        var dragger = new PathDragTool(getContext(), map, scene, getContext().getString("GrabFile"), pathOptions);
        var remover = new PathDeleteTool(getContext(), map, scene, getContext().getString("DeleteFile"), pathOptions);

        toolImageMap.put(getContext().getString("LassoFile"), lasso);
        toolImageMap.put(getContext().getString("PenFile"), pen);
        toolImageMap.put(getContext().getString("GrabFile"), dragger);
        toolImageMap.put(getContext().getString("DeleteFile"), remover);
        return toolbar;
    }

    private void initLevelTools() {
        initLevelChanger();
        initExistingLevelCreator();
        initLevelButtons();
        final Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        toolBar.getItems().addAll(levelChanger, addEmpty, existingLevelCreator, addExisting, clearCurrent, spacer);
    }

    private void initLevelChanger() {
        levelChanger = new Spinner(getContext().getDouble("InitialLevel"), getContext().getDouble("MaxLevel"), getContext().getDouble("InitialLevel"));
        levelChanger.valueProperty().addListener((obs, oldValue, newValue) -> checkMax((int) (double) newValue));
    }

    private void initExistingLevelCreator() {
        existingLevelCreator = new ComboBox<>(FXCollections.observableArrayList(1));
        //existingLevelCreator.setPromptText(getContext().getString("ChooserPrompt"));
        existingLevelCreator.getSelectionModel().selectFirst();
    }

    public int getExistingLevelValue() {
        if (existingLevelCreator.getValue() == null) {
            return -1;
        }
        return existingLevelCreator.getValue();
    }

    private void initLevelButtons() {
        addEmpty = new Button (getContext().getString("NewEmptyLevel"));
        addExisting = new Button (getContext().getString("NewFromExisting"));
        clearCurrent = new Button (getContext().getString("Clear"));
    }

    public void accessAddEmpty(Consumer<Button> accessMethod) {accessMethod.accept(addEmpty);}

    public void accessAddExisting(Consumer<Button> accessMethod) {accessMethod.accept(addExisting);}

    public void accessClear(Consumer<Button> accessMethod) {accessMethod.accept(clearCurrent);}

    public void addToExistingLevelCreator(int newLevel) {
        existingLevelCreator.getItems().add(newLevel);
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int newMax) {
        maxLevel = newMax;
    }

    public PathPenTool getPen(){
        return (PathPenTool) toolImageMap.get(getContext().getString("PenFile"));
    }

    private void checkMax(int newValue) {
        if (newValue > maxLevel) {
            levelChanger.decrement();
        }
    }

    public void updateSpinner(int oldValue, int newValue) {
        for (int i = oldValue; i < newValue; i++) {
            levelChanger.increment();
        }
    }

    public Spinner getLevelChanger() {
        return levelChanger;
    }

    /**
     * Called from the AuthoringEnvironment class to add actions to the MenuBar
     * @param menuName String representing the key of the menu item in the map
     * @param label String representing the text in the menu
     * @param action Action on clicking the item in the menu
     */
    public void addAction(String menuName, String label, EventHandler action){
        var menu = menuMap.get(menuName);
        var menuItem = new MenuItem(label);
        menuItem.setOnAction(action);
        menu.getItems().add(menuItem);
    }

    public void addButton(String buttonImageName, double buttonSize, EventHandler action){
        Button button = AuthoringUtil.createSquareImageButton(buttonImageName, buttonSize, action);
        EventHandler handler = e -> toggleTool(toolImageMap.get(buttonImageName));
        button.addEventHandler(ActionEvent.ACTION, handler);
        toolBar.getItems().addAll(button);
    }

    private void toggleTool(Tool enabled){
        for(Tool t: toolImageMap.values()){
            if(t != enabled){
                t.setToolEnabled(false);
            }
        }
        enabled.setToolEnabled(!enabled.getToolEnabled());
    }

    public void addButton(String buttonImageName, EventHandler action) {
        addButton(buttonImageName, getContext().getDouble("ToolbarButtonSize"), action);
    }

    public double getHeight() {
        return this.menuBar.getHeight() + this.toolBar.getHeight();
    }

    @Override
    public void setStylesheet(String url) {

    }

    @Override
    public void updateSize(double width, double height) {
        box.setPrefSize(width, height);
    }
}
