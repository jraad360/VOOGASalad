package Level;

import Attribute.AttributeView;
import Controllers.LevelController;
import Panes.SettingsButton;
import Panes.SettingsPane;
import Store.DraggableStoreAgent;
import Store.StoreButton;
import Store.StoreItem;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import state.IPlayerLevelState;
import state.attribute.IPlayerAttribute;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.layout.BackgroundRepeat.NO_REPEAT;
import static javafx.scene.layout.BackgroundSize.AUTO;

public class Level extends Scene {
   private static int WIDTH = 800;
   private static int HEIGHT = 480;
   private static String GAME_CSS = "game.css";
   private static String FONT = "https://fonts.googleapis.com/css?family=Allerta+Stencil";
   private static String CENTER_PANE_STYLE = "center-pane";

   private Pane root;
   private VBox rightPane;
   private VBox leftPane;
   private VBox centerPane;
   private VBox bottomPane;
   private IPlayerLevelState state;

   private SettingsButton settingsButton;
   private StoreButton storeButton;
   private SettingsPane settingsPane;
   private BorderPane borderPane;
   private Pane mainPane;
   private IGameApplication gameApp;

   private LevelController levelController;
   private String backgroundImageUrl;
   private List<AttributeView> attributes;

   public Level(IPlayerLevelState levelState, IGameApplication a){
      super(new StackPane(), WIDTH, HEIGHT);
      gameApp = a;
      this.backgroundImageUrl = levelState.getBackgroundImageURL();
      this.setRoot();
      this.state = levelState;
      this.levelController = new LevelController(levelState);
      this.levelController.setGame(a);
      this.settingsPane = new SettingsPane(this);
      this.initButtons();
      this.initBorderPanes();
      this.initAttributes();
      this.resetStore();
      this.setStyles();
      System.out.println(" ");
   }

   private void setRoot(){
      this.root = (StackPane)this.getRoot();
      var image = new Image("file:" + this.backgroundImageUrl);
      BackgroundSize backgroundSize = new BackgroundSize(AUTO, AUTO, true, true, true, true);
      BackgroundImage backgroundImage = new BackgroundImage(image, NO_REPEAT, NO_REPEAT,null, backgroundSize);
      var background = new Background(backgroundImage);
      this.root.setBackground(background);
   }

   private void initButtons(){
      this.settingsButton = new SettingsButton();
      this.storeButton = new StoreButton();
      this.initializeButtonActions();
   }

   private void initBorderPanes(){
      this.leftPane = new VBox();
      this.rightPane = new VBox();
      this.centerPane = new VBox();
      this.bottomPane = new VBox();

      this.borderPane = new BorderPane();
      this.mainPane = new Pane();

      this.placePanes();
   }

   private void placePanes(){
      this.mainPane.getChildren().add(this.levelController.getMapPane());

      this.leftPane.getChildren().add(this.levelController.getAttributePane());
      this.rightPane.getChildren().addAll(this.settingsButton, this.storeButton);

      this.borderPane.setCenter(this.centerPane);
      this.borderPane.setLeft(this.leftPane);
      this.borderPane.setRight(this.rightPane);
      this.borderPane.setBottom(this.bottomPane);
      this.root.getChildren().add(mainPane);
      this.root.getChildren().add(borderPane);
   }

   private void initAttributes(){
      this.attributes = new ArrayList<>();
      for(IPlayerAttribute attribute : this.state.getImmutableAttributes()) {
         this.attributes.add(new AttributeView(attribute));
      }
   }

   private void setStyles(){
      this.getStylesheets().add(GAME_CSS);
      this.getStylesheets().add(FONT);
      this.mainPane.getStyleClass().add(CENTER_PANE_STYLE);
   }

   private void initializeButtonActions(){
      this.settingsButton.setOnAction(e -> toggleSettingsPane());
      this.storeButton.setOnAction(e -> toggleStorePane());

   }

   private void toggleSettingsPane(){
      if(this.centerPane.getChildren().contains(this.settingsPane)){
         gameApp.getGame().play();
         this.centerPane.getChildren().remove(this.settingsPane);
         //this.centerPane.getChildren().add(this.levelController.getMapPane());
      }
      else {
         gameApp.getGame().stop();
         this.centerPane.getChildren().add(this.settingsPane);
         //this.centerPane.getChildren().remove(this.levelController.getMapPane());
      }
   }

   private void toggleStorePane(){
      if(this.bottomPane.getChildren().contains(this.levelController.getStorePane())){
         this.bottomPane.getChildren().remove(this.levelController.getStorePane());
      }
      else {
         System.out.println("Toggling store pane");
         this.bottomPane.getChildren().add(this.levelController.getStorePane());
      }
   }

   private void resetStore(){
      for(StoreItem item: this.levelController.getInventory()){
         setMouseActionsForDrag(item.getDraggableStoreAgent());
      }
   }



   private void setMouseActionsForDrag(DraggableStoreAgent draggableAgent){
      draggableAgent.setOnMousePressed(mouseEvent -> mousePressed(mouseEvent, draggableAgent));
      draggableAgent.setOnMouseDragged(mouseEvent -> mouseDragged(mouseEvent, draggableAgent));
      draggableAgent.setOnMouseReleased(mouseEvent -> mouseReleased(mouseEvent, draggableAgent));
   }


   private void mousePressed(MouseEvent event, DraggableStoreAgent draggableAgent) {
      draggableAgent.setMyStartSceneX(event.getSceneX());
      draggableAgent.setMyStartSceneY(event.getSceneY());
      draggableAgent.setMyStartXOffset(((DraggableStoreAgent)(event.getSource())).getTranslateX());
      draggableAgent.setMyStartYOffset(((DraggableStoreAgent)(event.getSource())).getTranslateY());
   }

   private void mouseDragged(MouseEvent event, DraggableStoreAgent draggableAgent) {
      double offsetX = event.getSceneX() - draggableAgent.getMyStartSceneX();
      double offsetY = event.getSceneY() - draggableAgent.getMyStartSceneY();
      double newTranslateX = draggableAgent.getStartX() + offsetX;
      double newTranslateY = draggableAgent.getStartY() + offsetY;
      ((DraggableStoreAgent)(event.getSource())).setTranslateX(newTranslateX);
      ((DraggableStoreAgent)(event.getSource())).setTranslateY(newTranslateY);
      /*if (outOfBounds(draggableAgent)) {
         draggableAgent.setEffect(setLighting());
      } else {
         draggableAgent.setEffect(null);
      }*/
   }

   private void mouseReleased(MouseEvent event, DraggableStoreAgent draggableAgent) {
      /*if (outOfBounds(draggableAgent)) {
         draggableAgent.setImage(null);
         map.removeAgent(draggableAgent);
      }*/

      // HAVE TO FIX WHAT X IS
//      System.out.println( "(x: "       + event.getX()      + ", y: "       + event.getY()       + ") -- " +
//              "(sceneX: "  + event.getSceneX() + ", sceneY: "  + event.getSceneY()  + ") -- " +
//              "(screenX: " + event.getScreenX()+ ", screenY: " + event.getScreenY() + ")");
      double offsetX = event.getSceneX()-(draggableAgent.getFitWidth()/2);
     double offsetY = event.getSceneY()-(draggableAgent.getFitWidth()/2);
      state.addAgentFromStore(draggableAgent.getStoreIndex(), offsetX, offsetY);
      this.levelController.getStorePane().reset(draggableAgent.getStoreIndex());
      this.resetStore();
   }

   public IGameApplication getGameApp() {
      return gameApp;
   }
}