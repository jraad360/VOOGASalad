import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Scanner;

public class SavingTest {

    public static final String BUGGY_XML_FILEPATH = "testGameOutputs/gameSaveOutputBuggy/game.xml";
    public static final String CLEAN_XML_FILEPATH = "testGameOutputs/gameSaveOutputClean/game.xml";
    public static final String INVALID_STRING = "file:";

    @DisplayName("Test XML Contains file:")
    @Test
    public void testXMLContainsFile() {

        String content = new Scanner(this.getClass().getResourceAsStream(CLEAN_XML_FILEPATH)).useDelimiter("\\Z").next();
        assertFalse(content.contains(INVALID_STRING));
    }
}
