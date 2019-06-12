package engine;

import authoring.*;
import authoring.exception.ActionDoesNotExistException;
import authoring.exception.ConditionDoesNotExistException;
import authoring.exception.ReflectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import state.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class PlantsVsZombiesTest {
    public static final String GAME_FILE_NAME = "resources/gamefiles/pvzTest.xml";
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
     * Creates ten Agents all moving in the +x direction with the same speed.
     */
    @Test
    void setUpOne() {
        try {
            ILevelDefinition level = factory.createLevel();

            state.addDefinedAgent(createZombie("zombie"));

            for(int k = 0; k < AGENT_NUM; k++){
                level.addAgent("zombie", 10 + 20 * k, 10, 0, new ArrayList<Property>());
            }
            state.addLevel(level);
            gameEngine.setState(state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a tower that spins and spawns projectiles.
     */
    @Test
    void setupTwo() {
        try {
            ILevelDefinition level = factory.createLevel();

            state.addDefinedAgent(createPea("pea"));
            state.addDefinedAgent(createPeashooter("peashooter", "pea"));

            level.addAgent("peashooter", 200, 200, 0, new ArrayList<Property>());

            state.addLevel(level);
            gameEngine.setState(state);
            gameEngine.saveState(new File("/Users/davidmiron/Desktop/testgame"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates five peashooters that shoot at five zombies
     */
    @Test
    void setupThree() {
        try {
            ILevelDefinition level = factory.createLevel();

            state.addDefinedAgent(createPea("pea"));
            state.addDefinedAgent(createPeashooter("peashooter", "pea"));
            state.addDefinedAgent(createZombie("zombie"));

            for (int i = 0; i < 5; i++) {
                level.addAgent("peashooter", 50, 50 + 100 * i, 0, new ArrayList<Property>());
            }

            for (int i = 0; i < 5; i++) {
                level.addAgent("zombie", 350, 50 + 100 * i, 180, new ArrayList<Property>());
            }

            state.addLevel(level);
            gameEngine.setState(state);
            //DAVIDCOMMENTgameEngine.saveState(GAME_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a pea on a zombie.
     */
    @Test
    void setupFour() {
        try {
            ILevelDefinition level = factory.createLevel();

            state.addDefinedAgent(createPea("pea"));
            state.addDefinedAgent(createZombie("zombie"));

            level.addAgent("pea", 50, 50, 0, new ArrayList<Property>());
            level.addAgent("zombie", 50, 50, 0, new ArrayList<Property>());

            state.addLevel(level);
            gameEngine.setState(state);
            //DAVIDCOMMENTgameEngine.saveState(GAME_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * One peashooter and one zombieshooter
     */
    @Test
    void setUpFive(){
        try {
            ILevelDefinition level = factory.createLevel();

            state.addDefinedAgent(createPea("pea"));
            state.addDefinedAgent(createZombie("zombie"));
            state.addDefinedAgent(createPeashooter("peashooter", "pea"));
            state.addDefinedAgent(createPeashooter("zombieshooter", "zombie"));

            level.addAgent("peashooter", 50, 50, 0.0, new ArrayList<Property>());
            level.addAgent("zombieshooter", 50, 150, 0.0, new ArrayList<Property>());

            state.addLevel(level);
            gameEngine.setState(state);
            //DAVIDCOMMENTgameEngine.saveState(GAME_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a peashooter and a zombieshooter
     */
    @Test
    void setUpSix(){
        try {
            ILevelDefinition level = factory.createLevel();

            state.addDefinedAgent(createPea("pea"));
            state.addDefinedAgent(createZombie("zombie"));
            state.addDefinedAgent(createPeashooter("peashooter", "pea"));
            state.addDefinedAgent(createPeashooter("zombieshooter", "zombie"));
            level.addAgent("peashooter", 50, 50, 0, new ArrayList<Property>());
            level.addAgent("zombieshooter", 50, 250, 0, new ArrayList<Property>());
            state.addLevel(level);
            gameEngine.setState(state);
            //DAVIDCOMMENTgameEngine.saveState(GAME_FILE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private IAgentDefinition createZombie(String name) throws ActionDoesNotExistException, ReflectionException, ConditionDoesNotExistException {
        double initHealth = 10.0; // 10 hits from a pea
        double damage = 1.0;
        String team = "badGuys";
        String otherTeam = "goodGuys";

        // list of ADs
        List<IActionDecisionDefinition> AD = new ArrayList<>();

        List<IConditionDefinition> cond3 = new ArrayList<>();
        Map<String, Object> healthCheckParams = new HashMap<>();
        healthCheckParams.put("property", "health");
        healthCheckParams.put("value", 0.0);
        cond3.add(factory.createCondition("PropertyLessThanOrEqualTo", healthCheckParams));
        AD.add(factory.createActionDecision(factory.createAction("DestroyAgent", new HashMap<>()), cond3));

        // create attack
        List<IConditionDefinition> cond2 = new ArrayList<>();
        cond2.add(factory.createCondition("Collision", new HashMap<>()));
        Map<String, Object> teamCheckParams = new HashMap<>();
        teamCheckParams.put("property", "team");
        teamCheckParams.put("value", otherTeam);
        cond2.add(factory.createCondition("PropertyEqualTo", teamCheckParams));
        Map intervalParams = new HashMap();
        intervalParams.put("interval", 2.0);
        cond2.add(factory.createCondition("Interval", intervalParams));
        Map<String, Object> damageParams = new HashMap<>();
        damageParams.put("value", damage);
        damageParams.put("property", "health");
        AD.add(factory.createActionDecision(
                factory.createAction("DecrementProperty", damageParams), cond2));

        // create Movement AD
        Map<String, Object> moveParams = new HashMap<>();
        moveParams.put("angle", 0.0);
        moveParams.put("speed", 10.0);
        IActionDefinition move = factory.createAction("MoveAtRelativeAngle", moveParams);
        List<IConditionDefinition> zombieMoveConditions = new ArrayList<>();
        zombieMoveConditions.add(factory.createCondition("PropertyEqualTo", teamCheckParams));
        zombieMoveConditions.add(factory.createCondition("IsNotColliding", new HashMap<>()));
        zombieMoveConditions.add(factory.createCondition("DoOnceWithSelf", new HashMap<>()));
        AD.add(factory.createActionDecision(move, zombieMoveConditions));

        // create properties
        List<IPropertyDefinition> properties = new ArrayList<>();
        properties.add(factory.createProperty("health", initHealth));
        properties.add(factory.createProperty("team", team));

        //create zombie
        return factory.createAgent(0, 0, 30, 30,
                180, name, "pvz/zombie.gif", AD, properties);
    }

    private IAgentDefinition createPeashooter(String name, String projectileName) throws ActionDoesNotExistException, ReflectionException, ConditionDoesNotExistException {
        String team = "goodGuys";
        double initHealth = 20.0; // 4 zombie bites

        // MAKING peashooter
        List<IActionDecisionDefinition> AD2 = new ArrayList<>();

        //making spawn action
        Map<String, Object> spawnParams = new HashMap<>();
        spawnParams.put("agent", projectileName);
        IActionDefinition spawnAction = factory.createAction("SpawnAgentInitialDirection", spawnParams);
        List<IConditionDefinition> conditions = new ArrayList<>();
        Map condParams = new HashMap();
        condParams.put("interval", 5.0);
        conditions.add(factory.createCondition("DoOnceWithSelf", condParams));
        conditions.add(factory.createCondition("Interval", condParams));
        IActionDecisionDefinition spawnAD = factory.createActionDecision(spawnAction, conditions);
        AD2.add(spawnAD);

        List<IConditionDefinition> cond3 = new ArrayList<>();
        cond3.add(factory.createCondition("DoOnceWithSelf", new HashMap<>()));
        Map<String, Object> healthCheckParams = new HashMap<>();
        healthCheckParams.put("property", "health");
        healthCheckParams.put("value", 0.0);
        cond3.add(factory.createCondition("PropertyLessThanOrEqualTo", healthCheckParams));
        AD2.add(factory.createActionDecision(factory.createAction("DestroyAgent", new HashMap<>()), cond3));

        // add properties
        List<IPropertyDefinition> properties = new ArrayList<>();
        properties.add(factory.createProperty("team", team));
        properties.add(factory.createProperty("health", initHealth));

        return factory.createAgent(50, 50, 20, 20,
                0,name, "pvz/peashooter.gif", AD2, properties);
    }

    private IAgentDefinition createPea(String name) throws ConditionDoesNotExistException, ReflectionException, ActionDoesNotExistException {
        String team = "goodGuys";
        String otherTeam = "badGuys";
        double damage = 1.0;

        // MAKING PROJECTILE
        // making move action
        Map<String, Object> moveParams = new HashMap<>();
        moveParams.put("angle", 0.0);
        moveParams.put("speed", 1000.0);

        List<IActionDecisionDefinition> AD1 = new ArrayList<>();
        List<IConditionDefinition> cond1 = new ArrayList<>();

        cond1.add(factory.createCondition("DoOnceWithSelf", new HashMap<>()));
        AD1.add(factory.createActionDecision(
                factory.createAction("MoveAtRelativeAngle", moveParams), cond1));

        // DISAPPEAR ON COLLISION
        List<IConditionDefinition> cond2 = new ArrayList<>();
        cond2.add(factory.createCondition("Collision", new HashMap<>()));

        Map<String, Object> teamCheckParams = new HashMap<>();
        teamCheckParams.put("property", "team");
        teamCheckParams.put("value", otherTeam);
        IConditionDefinition teamCheck = factory.createCondition("PropertyEqualTo", teamCheckParams);

        cond2.add(teamCheck);
        AD1.add(factory.createActionDecision(
                factory.createAction("DestroyAgent", new HashMap<>()), cond2));

        // use the same conditions as the previous action because disappearance and damage should always happen together
        Map<String, Object> damageParams = new HashMap<>();
        damageParams.put("value", damage);
        damageParams.put("property", "health");
        AD1.add(factory.createActionDecision(
                factory.createAction("DecrementProperty", damageParams), cond2));

        // add properties
        List<IPropertyDefinition> properties = new ArrayList<>();
        properties.add(factory.createProperty("team", team));

        return factory.createAgent(500, 500, 6, 6,
                0,name, "pvz/pea.gif", AD1, properties);
    }
}