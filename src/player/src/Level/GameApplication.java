package Level;

import Level.Level;
import SplashScreen.SplashScene;
import engine.Game;
import engine.IPlayerGame;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class GameApplication extends Application implements IGameApplication{
   private static final int MILLISECOND_DELAY = 10;

   public static void main(String[] args) throws Exception {
      System.setErr(new PrintStream(new FileOutputStream("temp.txt")));
      launch(args);

   }

   public Stage primaryStage;
   private Scene myScene;
   private Group root;
   public static final int SCREEN_WIDTH = 800;
   public static final int SCREEN_HEIGHT = 800;
   public static final Paint BACKGROUND = Color.WHITE;
   public static final String GAME_NAME = "Game Time";
   public static final String BACKGROUND_MUSIC = "src/player/resources/grasswalk.wav";
   private Timeline animation;
   public IPlayerGame game;
   public SplashScene ss;
   private Level initialLevel;

   @Override
   public void start(Stage primaryStage) {
      this.primaryStage = primaryStage;
      ss = new SplashScene(primaryStage, this);
      primaryStage.setScene(ss.pickGame());
      this.primaryStage.show();
     /* game = ss.selectGame();
      var levelState = game.getLevelState();
      Level level = new Level(levelState);
      this.primaryStage.setScene(level);
      this.primaryStage.show();*/
      //setAnimation();

   }

   public void setup()
   {
      System.out.println("in setup");
      game = ss.selectGame();
      var levelState = game.getLevelState();
      Level level = new Level(levelState, this);
      AudioClip audio =  new AudioClip(new File(BACKGROUND_MUSIC).toURI().toString());
      audio.setCycleCount(Timeline.INDEFINITE);
      audio.setVolume(0.5f);
      audio.setCycleCount(Timeline.INDEFINITE);
      audio.play();
      this.primaryStage.setScene(level);
      this.primaryStage.show();
      setAnimation();
   }

   private void step(){
      game.step();
   }

   private void setAnimation() {
         animation = new Timeline();
         var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), event -> step());
         animation.setCycleCount(Timeline.INDEFINITE);
         animation.getKeyFrames().add(frame);
         animation.play();
   }

   @Override
   public SplashScene getSplashScene() {
      return ss;
   }

   @Override
   public IPlayerGame getGame(){
      return game;
   }

   @Override
   public Stage getPrimaryStage(){
      return primaryStage;
   }


}
