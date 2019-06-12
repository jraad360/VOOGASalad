package panes;

import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.AuthoringContext;

import java.util.ResourceBundle;

/**
 * A pane that shows a console for notifying the user of changes and will eventually allow user input.
 *
 * @author Mary Stuart Elder
 * @author Samuel Rabinowitz
 *
 * Styling resources:
 *  https://www.w3.org/TR/css-backgrounds-3/#propdef-border-style
 *  https://stackoverflow.com/questions/48048943/javafx-8-scroll-bar-css
 */
public class ConsolePane extends AuthoringPane {

    /**
     * Console message level.
     * @author Samuel Rabinowitz
     */
    public enum Level {
        NEUTRAL,
        ERROR,
        SUCCESS
    }

    private VBox myVBox;
    private VBox fullBox;
    private ScrollPane scrollPane;

    public ConsolePane(AuthoringContext context) {
        super(context);
        getContentChildren().add(initializeContent());
    }

    private Region initializeContent(){
        fullBox = new VBox();
        fullBox.getStylesheets().add(getContext().getString("ConsoleStyle"));
        fullBox.setId(getContext().getString("ConsoleGeneralStyle"));
        fullBox.setPrefSize(getContext().getDouble("ConsoleWidth"), getContext().getDouble("ConsoleHeight"));
        myVBox = new VBox();
        scrollPane = new ScrollPane();
        formatScrollPane(scrollPane);
        scrollPane.setContent(myVBox);
        myVBox.setFillWidth(true);

        var title = new Text(getContext().getString("ConsoleTitle"));
        title.setId(getContext().getString("ConsoleTitleStyle"));
        fullBox.getChildren().addAll(title, scrollPane);
        return fullBox;
    }

    private void formatScrollPane(ScrollPane consolePane){
        //scrollPane.setPrefViewportWidth(WIDTH);
        //scrollPane.setPrefViewportHeight(TITLE_HEIGHT + HEIGHT);
        consolePane.setId(getContext().getString("Message"));
        consolePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        consolePane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    @Override
    public void setStylesheet(String url) {

    }

    @Override
    public void updateSize(double width, double height) {
        fullBox.setPrefSize(width, height);
    }

    /**
     * This method can be called externally to notify the user of environment changes.
     * Changes (good, neutral, and bad) to the environment that don't require immediate user action are sent to the console.
     * @param message String, message to send to user
     * @param level ConsolePane.Level, indicates the importance of the message
     */
    public void displayMessage(String message, Level level) {
        var text = new Text(message);

        if (level == Level.NEUTRAL) {
            text.setId(getContext().getString("Message"));
        }
        else if (level == Level.ERROR) {
            text.setId(getContext().getString("ErrorMessage"));
        }
        else if (level == Level.SUCCESS) {
            text.setId(getContext().getString("SuccessMessage"));
        }

        myVBox.getChildren().add(text);
        scrollPane.setVvalue(1);
    }

    public ScrollPane getConsole() {
        return scrollPane;
    }

}
