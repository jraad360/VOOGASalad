import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorPlayerSizingTest {

    public static final double CORRECT_WIDTH = 800;
    public static final double CORRECT_HEIGHT = 480;

    @DisplayName("Test map/level sizing in author and player")
    @Test
    public void testSizingAuthorPlayer() {
        ResourceBundle authoringProperties = ResourceBundle.getBundle("strings/English");
        double authoringWidth = Double.parseDouble(authoringProperties.getString("InsetMapWidth"));
        double authoringHeight = Double.parseDouble(authoringProperties.getString("InsetMapHeight"));

        double playerWidth = 800;//1000; // Note in player/Level/Level.java, the WIDTH variable
        double playerHeight = 480;//600; // Note in player/Level/Level.java, the HEIGHT variable

        assertEquals(authoringWidth, playerWidth);
        assertEquals(authoringWidth, CORRECT_WIDTH);
        assertEquals(playerWidth, CORRECT_WIDTH);

        assertEquals(authoringHeight, playerHeight);
        assertEquals(authoringHeight, CORRECT_HEIGHT);
        assertEquals(playerHeight, CORRECT_HEIGHT);
    }
}
