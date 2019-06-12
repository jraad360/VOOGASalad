package state;

import authoring.GameFactory;
import authoring.IGameDefinition;
import authoring.ILevelDefinition;
import authoring.IStateDefinition;
import engine.Game;
import engine.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    IGameDefinition gameEngine;
    IStateDefinition state;
    GameFactory factory;

    @BeforeEach
    void setUp(){
        try {
            factory = new GameFactory();
            state = factory.createState();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Tests clone() in engine.Level
     * Test that level-cloning does not throw CloneNotSupportedException
     * @throws CloneNotSupportedException
     */
    @Test
    public void testLevelClone() throws CloneNotSupportedException {
        ILevelDefinition level = factory.createLevel();
        List<Property> propertyList = new ArrayList<>();
        propertyList.add((Property) factory.createProperty("property", "value"));
        state.addDefinedAgent(factory.createAgent(0, 0, 10, 10, 0, "testAgent",
                "pvz/pea.gif", new ArrayList<>(), new ArrayList<>()));
        level.addAgent("testAgent", 0, 0, 0, propertyList);
        ILevelDefinition clonedLevel = level.clone();
    }

}