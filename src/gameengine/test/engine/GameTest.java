package engine;

import authoring.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    public static final String GAME_FILE_NAME = "GameTest.xml";
    public static final int AGENT_NUM = 10;

    IGameDefinition gameEngine;
    IStateDefinition state;
    GameFactory factory;

    @BeforeEach
    void setUp(){
        try {
            factory = new GameFactory();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    void setState() {
    }

    @Test
    void saveState() {
        // deletes GameTestXML
        if(new File(GAME_FILE_NAME).exists()){
            new File(GAME_FILE_NAME).delete();
        }
        //DAVIDCOMMENTgameEngine.saveState(GAME_FILE_NAME);
        assertTrue(new File(GAME_FILE_NAME).exists());
    }

    @Test
    void stop() {
    }

}