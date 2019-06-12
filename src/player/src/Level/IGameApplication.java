package Level;

import SplashScreen.SplashScene;
import engine.Game;
import engine.IPlayerGame;
import javafx.stage.Stage;

/**
 * @author Joanna Li
 * An interface for settings to be able to access properties of game
 */

public interface IGameApplication {

    SplashScene getSplashScene();
    IPlayerGame getGame();
    Stage getPrimaryStage();


}
