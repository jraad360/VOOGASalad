package engine;

import authoring.*;
import authoring.exception.ActionDoesNotExistException;
import authoring.exception.ConditionDoesNotExistException;
import authoring.exception.ReflectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClashOfClansTest {

    public static final String CLASH_FILE = "resources/gamefiles/ClashTest1.xml";
    public static final String GOOD_GUYS = "Luke";
    public static final String BAD_GUYS = "Jorge";

    IGameDefinition gameEngine;
    IStateDefinition state;
    GameFactory factory;

    @BeforeEach
    void setUp() {
        try {
            factory = new GameFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Creates a tower that spins and spawns projectiles.
     */
    @Test
    void gameOne() {
        try {
            factory = new GameFactory();
            gameEngine = factory.createGame();
            state = factory.createState();
            ILevelDefinition level = factory.createLevel();

            state.addDefinedAgent(createTownHall("home"));
            state.addDefinedAgent(createBarbarian("barb"));
            level.addAgent("home", 300, 300, 0, new ArrayList<>());
            level.addAgent("barb", 200, 380, 0, new ArrayList<>());
            level.addAgent("barb", 500, 380, 0, new ArrayList<>());
            //level.addAgent("barb", 500, 300, 0, new ArrayList<>());
            level.addAgent("barb", 200, 220, 0, new ArrayList<>());
            level.addAgent("barb", 500, 220, 0, new ArrayList<>());

            state.addLevel(level);
            gameEngine.setState(state);
            //DAVIDCOMMENTgameEngine.saveState(CLASH_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private IAgentDefinition createTownHall(String name) throws ConditionDoesNotExistException, ReflectionException, ActionDoesNotExistException {
        List<IActionDecisionDefinition> actionDecisions = new ArrayList<>();
        List<IPropertyDefinition> properties = new ArrayList<>();
        var prop = factory.createProperty("health", 100.0);
        var team = factory.createProperty("team", GOOD_GUYS);
        properties.add(prop);
        properties.add(team);
        return factory.createAgent(0, 0, 50, 50 ,1, name, "clash/TH10.png", actionDecisions, properties);
    }

    private IAgentDefinition createBarbarian(String name) throws ActionDoesNotExistException, ReflectionException, ConditionDoesNotExistException {
        List<IActionDecisionDefinition> actionDecisions = new ArrayList<>();
        Map<String, Object> teamCheckParams = new HashMap<>();
        teamCheckParams.put("property", "team");
        teamCheckParams.put("value", GOOD_GUYS);
        Map<String, Object> moveParams = new HashMap<>();
        moveParams.put("angle", 0.0);
        moveParams.put("speed", 10.0);
        IActionDefinition move = factory.createAction("MoveAtRelativeAngle", moveParams);
        List<IConditionDefinition> moveConditions = new ArrayList<>();
        moveConditions.add(factory.createCondition("DoOnceWithSelf", new HashMap<>()));
        IActionDefinition point = factory.createAction("PointToAgent", new HashMap<>());
        List<IConditionDefinition> pointConditions = new ArrayList<>();
        pointConditions.add(factory.createCondition("PropertyEqualTo", teamCheckParams));
        pointConditions.add(factory.createCondition("Closest", new HashMap<>()));
        actionDecisions.add(factory.createActionDecision(point, pointConditions));
        actionDecisions.add(factory.createActionDecision(move, moveConditions));
        List<IPropertyDefinition> properties = new ArrayList<>();
        var prop = factory.createProperty("health", 20.0);
        var team = factory.createProperty("team", BAD_GUYS);
        properties.add(prop);
        properties.add(team);
        return factory.createAgent(0, 0, 10, 10 ,0, name, "clash/Barbarian.png", actionDecisions, properties);
    }

}
