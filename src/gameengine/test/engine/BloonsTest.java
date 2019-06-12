package engine;

import authoring.*;
import authoring.exception.ActionDoesNotExistException;
import authoring.exception.ConditionDoesNotExistException;
import authoring.exception.ReflectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import state.Property;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BloonsTest {
    public static final String GAME_FILE_NAME = "resources/gamefiles/bloonsTest.xml";
    public static final int AGENT_NUM = 10;

    IGameDefinition gameEngine;
    IStateDefinition state;
    GameFactory factory;

    @BeforeEach
    void setUp(){
        try {
            factory = new GameFactory();
            gameEngine = factory.createGame();
            state = factory.createState();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Creates a balloon following a path.
     */
    @Test
    void setUpOne() {
        try {
            ILevelDefinition level = factory.createLevel();

            List<Point2D> path = new ArrayList<>();
            path.add(new Point2D.Double(0.0, 0.0));
            path.add(new Point2D.Double(200.0, 0.0));
//            path.add(new Point2D.Double(200.0, 200.0));
//            path.add(new Point2D.Double(400.0, 200.0));main
//            path.add(new Point2D.Double(400.0, 400.0));
            level.addPath("bloonPath", path);

            state.addDefinedAgent(createBloon("red"));

            level.addAgent("red", 0, 100, 0, new ArrayList<>());
            level.addAgent("red", 50,50, 0, new ArrayList<>());
            state.addLevel(level);
            gameEngine.setState(state);
            //DAVIDCOMMENTgameEngine.saveState(GAME_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private IAgentDefinition createBloon(String name) throws ActionDoesNotExistException, ReflectionException, ConditionDoesNotExistException {
        List<IActionDecisionDefinition> AD = new ArrayList<>();

        List<IConditionDefinition> pathConditions = new ArrayList<>();
        pathConditions.add(factory.createCondition("DoOnceWithSelf", new HashMap<>()));
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("speed", 10.0);
        AD.add(factory.createActionDecision(factory.createAction("MoveOnPointPath", pathParams), pathConditions));

        List<Property> properties = new ArrayList<>();

        properties.add((Property)factory.createProperty("pathName", "bloonPath"));
        return factory.createAgent(0, 0, 12, 15,
                180, name, "bloons/redBloon.png", AD, properties);
    }

//    private IAgentDefinition createTower(String name, String projectileName) throws ActionDoesNotExistException, ReflectionException, ConditionDoesNotExistException {
//
//        return factory.createAgent(50, 50, 20, 20,
//                0,name, "pvz/peashooter.gif", AD2, properties);
//    }

}