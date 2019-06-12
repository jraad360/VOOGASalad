package SplashScreen;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SplashButton extends Button {

    //private static final double SIZE = 100;
    private String gameID;
    private ImageView myImage;

    public SplashButton(String image, double width, double height)
    {
        //myImage = i;
        gameID = image;
        myImage = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(image)));
        myImage.setFitWidth(width);
        myImage.setFitHeight(height);
        this.setGraphic(myImage);
        this.getStyleClass().add("butt");
    }

    public String getGameID() {
        return gameID;
    }
}
