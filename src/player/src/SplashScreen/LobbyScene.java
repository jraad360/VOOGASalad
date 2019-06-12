package SplashScreen;
/**
 * Shows a list of available multiplayer games
 */

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
//import utils.LocalServerBrowser;

import java.util.List;

public class LobbyScene extends Scene {

    Pane root = new Pane();
    private final int HEIGHT = 1200;
    private final int WEIGHT = 1200;


    public LobbyScene(int width, int height)
    {
       super(new Pane(), width, height);
    }

    public List<String> getGames()
    {
        return null;
    }



}

