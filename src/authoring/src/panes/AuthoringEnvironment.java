package panes;

import authoring.GameFactory;
import authoring.ILevelDefinition;
import authoring.IPropertyDefinition;
import frontend_objects.DraggableAgentView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import panes.attributes.AttributesPane;
import panes.tools.PathPenTool;
import panes.tools.ToolbarPane;
import state.AgentReference;
import util.AuthoringContext;
import util.AuthoringUtil;

import java.awt.geom.Point2D;
import java.util.*;

public class AuthoringEnvironment extends Application {

    private AuthoringContext context;

    private StackPane stackPane;
    private BorderPane borderPane;
    private ConsolePane consolePane;
    private PathPane pathPane;
    private AgentPane agentPane;
    private AttributesPane attributesPane;
    private ToolbarPane toolbarPane;
    private LevelHandler levelHandler;
    private MapPane map;
    private Scene scene;
    private ObservableList<Path> currentPaths;
    private PathPenTool pen;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage){
        GameFactory gameFactory = initGameFactory();
        context = new AuthoringContext(ResourceBundle.getBundle("strings/English"),
                null,
                gameFactory,
                gameFactory.createState(),
                gameFactory.createGame());
        context.getGame().setState(context.getState());

        stackPane = new StackPane();
        borderPane = new BorderPane();
        currentPaths = FXCollections.observableArrayList();
        stackPane.getChildren().add(borderPane);
        scene = new Scene(stackPane, context.getDouble("DefaultWidth"), context.getDouble("DefaultHeight"));
        initAllPanes();
        //initPathListeners();
        initStage(stage);
        levelHandler = new LevelHandler(context, map, pathPane, consolePane, toolbarPane, currentPaths);

        // This is needed here because the context needs to be created to be passed to the consolePane, but it also
        // needs access to this method from the consolePane. It is a bit of circular referencing, but since it's
        // via a lambda it is not as bad.
        context.setDisplayConsoleMessage((message, level) -> consolePane.displayMessage(message, level));
    }

    private GameFactory initGameFactory() {
        try {
            return new GameFactory();
        } catch (Exception e) {
            //ResourceBundle.getBundle("strings/English").getString("GameFactoryInitializationError");
            System.err.println(e.getMessage());
            System.exit(-1);
            return null;
        }
    }

    private void initAllPanes() {
        initAttributesPane();
        initMapPane(1);
        initToolbarPane();
        initBottomPanes();
        initAgentPane();
    }

    private void initAttributesPane() {
        attributesPane = new AttributesPane(context);
        attributesPane.accessContainer(borderPane::setLeft);
    }

    private void initMapPane(int level) {
        map = new MapPane(context);
        map.accessContainer(borderPane::setCenter);

        ILevelDefinition gameLevel = context.getGameFactory().createLevel();
        context.getState().addLevel(gameLevel);

        map.getStateMapping().put(level, new MapState(context, null, new ArrayList<>(), new ArrayList<>()));
        map.setLevel(level);
    }

    private void initAgentPane() {
        agentPane = new AgentPane(context);
        agentPane.accessContainer(borderPane::setRight);
        agentPane.addButton(context.getString("AddButtonImageFile"), context.getDouble("ButtonSize"),
                e -> attributesPane.createNewAgentForm(a -> agentPane.refreshAgentList(map.getLevel()), null, false));
        agentPane.addButton(context.getString("ObjectivesAttributesImage"), context.getDouble("ButtonSize"), e -> attributesPane.displayStateForm(null, map.getLevel()));
        agentPane.setOnImageClicked((e, agent) -> {
            //if (e.getClickCount() == getContext().getInt("CloneClickCount")) { // Only add on double click to allow editing action on single click
            context.getState().getLevels().get(map.getLevel() - 1).addAgent(agent.getName(), 0, 0, 0, new ArrayList<IPropertyDefinition>());
            List<AgentReference> agentReferences = context.getState().getLevels().get(map.getLevel() - 1).getCurrentAgents();
            AgentReference ref = agentReferences.get(agentReferences.size() - 1);
            DraggableAgentView draggableAgentView = new DraggableAgentView(context, AuthoringUtil.switchAuthorEngineURL(agent.getImageURL(), true), ref);
            draggableAgentView.setOnMouseClicked(e2 -> attributesPane.editAgentInstanceForm(editedRef -> draggableAgentView.syncWithReference(), ref));
            map.addAgent(draggableAgentView);
            context.displayConsoleMessage(context.getString("AgentAdded") + map.getAgentCount(), ConsolePane.Level.NEUTRAL);
            draggableAgentView.setMouseActionsForDrag(map);
            //}
        });
        agentPane.setOnEdit((e, agent) ->
                        attributesPane.createNewAgentForm(a -> agentPane.refreshAgentList(map.getLevel()), agent, false)
                // TODO: on editing of agent definition that already exists, update images (and widths and heights and names) of all DraggableAgentViews and AgentReferences in all levels
        );
        agentPane.setOnCopy((e, agent) ->
                attributesPane.createNewAgentForm(a -> agentPane.refreshAgentList(map.getLevel()), agent, true)
        );
        agentPane.setOnDelete((e, agent) -> {
            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION, String.format(context.getString("ConfirmAgentDeletion"), agent.getName()));
            confirmDelete.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
                context.getState().getDefinedAgents().removeIf(agentDefinition -> agentDefinition.getName().equals(agent.getName()));
                agentPane.refreshAgentList(map.getLevel());
                context.getState().getLevels().forEach(level -> {
                    level.removePlaceableAgent(agent.getName());
                    level.getCurrentAgents().removeIf(agentReference -> agentReference.getName().equals(agent.getName()));
                });
                map.getStateMapping().values().forEach(mapState ->
                        mapState.getAgents().removeIf(draggableAgentView -> draggableAgentView.getReference().getName().equals(agent.getName())));
                levelHandler.refreshCurrentLevel();
                context.displayConsoleMessage(String.format(context.getString("AgentDefinitionDeleted"), agent.getName()), ConsolePane.Level.NEUTRAL);
            });
        });
        agentPane.setOnCheckChanged((checked, agent) -> {
            if (checked) {
                context.getState().getLevels().get(map.getLevel() - 1).addPlaceableAgent(agent.getName());
            }
            else {
                context.getState().getLevels().get(map.getLevel() - 1).removePlaceableAgent(agent.getName());
            }
        });
        agentPane.refreshAgentList(1);
    }

    private void initBottomPanes() {
        consolePane = new ConsolePane(context);
        pathPane = new PathPane(context, map, scene, currentPaths, pen);
        var bottomBox = new HBox();
        consolePane.accessContainer(bottomBox.getChildren()::add);
        pathPane.accessContainer(bottomBox.getChildren()::add);
        borderPane.setBottom(bottomBox);
    }

    private void initToolbarPane() {
        toolbarPane = new ToolbarPane(context, map, scene, currentPaths);
        toolbarPane.accessContainer(borderPane::setTop);
        // TODO: Eliminate magic numbers/text here, switch to for loop through buttons
        toolbarPane.accessAddEmpty(button -> button.setOnAction(e -> {
            levelHandler.makeLevel(toolbarPane.getMaxLevel() + 1, false);
            agentPane.refreshAgentList(map.getLevel()); // FIXME: This line isn't getting called for some reason
        }));

        toolbarPane.accessAddExisting(button -> button.setOnAction(e -> {
            levelHandler.makeLevel(toolbarPane.getMaxLevel() + 1, true);
            agentPane.refreshAgentList(map.getLevel()); // FIXME: This line isn't getting called for some reason
        }));

        toolbarPane.accessClear(button -> button.setOnAction(e -> levelHandler.clearLevel()));

        toolbarPane.addButton(context.getString("LassoFile"), e -> consolePane.displayMessage("Multi-select tool enabled", ConsolePane.Level.NEUTRAL));
        toolbarPane.addButton(context.getString("PenFile"), e -> consolePane.displayMessage("Path drawing tool enabled", ConsolePane.Level.NEUTRAL));
        toolbarPane.addButton(context.getString("GrabFile"), e -> consolePane.displayMessage("Path dragging tool enabled", ConsolePane.Level.NEUTRAL));
        toolbarPane.addButton(context.getString("DeleteFile"), e -> consolePane.displayMessage("Path removal tool enabled", ConsolePane.Level.NEUTRAL));

        toolbarPane.addAction("File", context.getString("MenuItemUpload"), e -> map.loadBackground());
        toolbarPane.addAction("File", context.getString("MenuItemSave"), e -> saveGame());
        // TODO: implement loading an old game
        toolbarPane.addAction("File", context.getString("MenuItemOpen"), e -> loadGame());
        toolbarPane.getLevelChanger().valueProperty().addListener((obs, oldValue, newValue) -> {
            levelHandler.changeToExistingLevel((int)((double) newValue));
            agentPane.refreshAgentList(map.getLevel()); // FIXME: This line isn't getting called for some reason
        });

        pen = toolbarPane.getPen();
    }

    private void saveGame(){
        map.forceLoadBackgroundIfNeeded();
        AuthoringUtil.openDirectoryChooser(
                null, file -> context.getGame().saveState(file),
                () -> context.displayConsoleMessage(context.getString("GameSaveError"), ConsolePane.Level.ERROR)
        );
    }

    private void loadGame() {
        AuthoringUtil.openDirectoryChooser(
                null, file -> {
                    context.getGame().loadState(file);
                    context.setState(context.getGame().getState());
                    //map.setMapImage(map.getLevel(), context.getState().getLevels().get(map.getLevel() - 1).getBackgroundImageURL(), true);
                    agentPane.refreshAgentList(map.getLevel());
                    loadMapOnGameLoad();
                },
                () -> context.displayConsoleMessage(context.getString("GameLoadError"), ConsolePane.Level.ERROR)
        );
    }

    private void loadMapOnGameLoad(){
        map.getStateMapping().clear();
        var levelList = context.getState().getLevels();
        for (int i = 0; i < levelList.size(); i++) {
            ILevelDefinition level = levelList.get(i);
            MapState newState = new MapState(context, AuthoringUtil.switchAuthorEngineURL(level.getBackgroundImageURL(), true), new ArrayList<>(), new ArrayList<>());
            map.getStateMapping().put(i + 1, newState);
            for (AgentReference agent : level.getCurrentAgents()) {
                DraggableAgentView newAgent = new DraggableAgentView(context, agent);
                newAgent.setOnMouseClicked(e2 -> attributesPane.editAgentInstanceForm(editedRef -> newAgent.syncWithReference(), agent));
                newAgent.setMouseActionsForDrag(map);
                newState.addToAgents(newAgent);
            }
            for (Map.Entry<String, List<Point2D>> path : level.getPaths().entrySet()) {
                newState.addToPaths(new Path(path.getKey(), path.getValue()));
            }
        }
        toolbarPane.setMaxLevel(levelList.size());
        toolbarPane.getLevelChanger().getValueFactory().setValue(1.0);
        levelHandler.changeToExistingLevel(1); //(should update level handler so map is set to level 1)

        currentPaths.setAll(map.getCurrentState().getPaths());
        //pathPane.setNewPathList(currentPaths);
    }

    private void updateDimensions(double width, double height){
        var middleWidth = width - context.getDouble("AttributesWidth") - context.getDouble("AgentPaneWidth");
        var middleHeight = height - context.getDouble("ConsoleHeight") - context.getDouble("ToolbarPaneHeight") - context.getDouble("MiddleRowPadding");
        consolePane.updateSize(width/2, context.getDouble("ConsoleHeight"));
        toolbarPane.updateSize(width, context.getDouble("ToolbarPaneHeight"));
        map.updateSize(middleWidth, middleHeight);
        attributesPane.updateSize(context.getDouble("AttributesWidth"), middleHeight);
        agentPane.updateSize(context.getDouble("AgentPaneWidth"), middleHeight);
    }

    private void initStage(Stage stage) {
        stage.setTitle(context.getString("Title"));
        stage.setScene(scene);
        stage.setMinWidth(context.getDouble("DefaultWidth"));
        stage.setMinHeight(context.getDouble("DefaultHeight"));
        scene.widthProperty().addListener((observable, oldvalue, newvalue) -> updateDimensions((double) newvalue, scene.getHeight()));
        scene.heightProperty().addListener((observable, oldvalue, newvalue) -> updateDimensions(scene.getWidth (), (double) newvalue));
        stage.getScene().getStylesheets().add(context.getString("MainStyle"));
        stage.show();
    }
}
