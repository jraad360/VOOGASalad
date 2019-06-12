package authoring;

import engine.Game;
import engine.Level;
import engine.event.GameEventMaster;
import authoring.exception.ActionDoesNotExistException;
import authoring.exception.ConditionDoesNotExistException;
import authoring.exception.IncorrectParametersException;
import authoring.exception.ReflectionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import state.LevelState;
import state.Property;
import state.State;
import state.action.Action;
import state.actiondecision.ActionDecision;
import state.agent.Agent;
import state.attribute.Attribute;
import state.condition.Condition;
import state.objective.Objective;
import state.objective.objectivecondition.ObjectiveCondition;
import state.objective.objectiveoutcome.ObjectiveOutcome;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Factory to create everything needed to define and save a game
 * @author David Miron
 */
public class GameFactory {

    public static final String CONDITION_DEFINITIONS_FILE = "conditions.xml";
    public static final String ACTION_DEFINITIONS_FILE = "actions.xml";
    public static final String OBJECTIVE_CONDITION_DEFINITIONS_FILE = "objective-conditions.xml";
    public static final String OBJECTIVE_OUTCOME_DEFINITIONS_FILE = "objective-outcomes.xml";

    public static final String CONDITION_CLASSNAMES_FILE = "conditions.properties";
    public static final String ACTION_CLASSNAMES_FILE = "actions.properties";
    public static final String OBJECTIVE_CONDITION_CLASSNAMES_FILE = "objective-conditions.properties";
    public static final String OBJECTIVE_OUTCOME_CLASSNAMES_FILE = "objective-outcomes.properties";

    private GameEventMaster eventMaster;

    private List<AvailableCondition> availableConditions;
    private List<AvailableAction> availableActions;
    private List<AvailableObjectiveCondition> availableObjectiveConditions;
    private List<AvailableObjectiveOutcome> availableObjectiveOutcomes;

    private Properties conditionClasses;
    private Properties actionClasses;
    private Properties objectiveConditionClasses;
    private Properties objectiveOutcomeClasses;

    private List<Agent> masterDefinedAgents;

    private int currentAgentID;

    public GameFactory() throws ParserConfigurationException, SAXException, IOException {
        currentAgentID = 0;
        this.eventMaster = new GameEventMaster();
        XMLToAvailableObjectsParser parser = new XMLToAvailableObjectsParser();
        availableConditions = parser.getNameFieldsList(CONDITION_DEFINITIONS_FILE);
        availableActions = parser.getNameFieldsList(ACTION_DEFINITIONS_FILE);
        availableObjectiveConditions = parser.getNameFieldsList(OBJECTIVE_CONDITION_DEFINITIONS_FILE);
        availableObjectiveOutcomes = parser.getNameFieldsList(OBJECTIVE_OUTCOME_DEFINITIONS_FILE);

        this.conditionClasses = new Properties();
        this.actionClasses = new Properties();
        this.objectiveConditionClasses = new Properties();
        this.objectiveOutcomeClasses = new Properties();

        conditionClasses.load(getClass().getClassLoader().getResourceAsStream(CONDITION_CLASSNAMES_FILE));
        actionClasses.load(getClass().getClassLoader().getResourceAsStream(ACTION_CLASSNAMES_FILE));
        objectiveConditionClasses.load(getClass().getClassLoader().getResourceAsStream(OBJECTIVE_CONDITION_CLASSNAMES_FILE));
        objectiveOutcomeClasses.load(getClass().getClassLoader().getResourceAsStream(OBJECTIVE_OUTCOME_CLASSNAMES_FILE));

        masterDefinedAgents = new ArrayList<>();
    }

    public List<AvailableCondition> getAvailableConditions() {
        return this.availableConditions;
    }

    public List<AvailableAction> getAvailableActions() {
        return this.availableActions;
    }

    public List<AvailableObjectiveCondition> getAvailableObjectiveConditions() {
        return this.availableObjectiveConditions;
    }

    public List<AvailableObjectiveOutcome> getAvailableObjectiveOutcomes() {
        return this.availableObjectiveOutcomes;
    }

    /**
     * Create a default game, with no agents or objectives
     * @return A default game
     */
    public IGameDefinition createGame() {
        return new Game();
    }

    /**
     * Create a default state, with no levels
     * @return A default state
     */
    public IStateDefinition createState() {
        return new State(masterDefinedAgents);
    }

    /**
     * Create a default level, with no agents defined
     * @return A default level
     */
    public ILevelDefinition createLevel() {
        Level level = new Level(masterDefinedAgents);
        level.injectGameEventMaster(eventMaster);
        return level;
    }

    /**
     * Create an agent with a list of action decisions and properties
     * @param actionDecisions The action decisions
     * @param properties The properties
     * @return The new agent
     */
    public IAgentDefinition createAgent(int x, int y, int width, int height, double direction, String name, String imageURL,
                                        List<? extends IActionDecisionDefinition> actionDecisions,
                                        List<? extends IPropertyDefinition> properties) {
        return new Agent(currentAgentID++, x, y, width, height, direction, name, imageURL, actionDecisions, properties);
    }

    /**
     * Create an Attribute
     * @param name of the Attribute - unique identifier
     * @param value of the attribute
     * @return The new agent
     */
    public IAttributeDefinition createAttribute(String name, double value) {
        return new Attribute(name, value);
    }

    /**
     * Create an action decision with an action and list of conditions
     * @param action The action
     * @param conditions The conditions
     * @return The new action decision
     */
    public IActionDecisionDefinition createActionDecision(IActionDefinition action,
                                                          List<? extends IConditionDefinition> conditions) {
        return new ActionDecision((Action)action, (List<Condition>)conditions);
    }

    /**
     * Create an action
     * @param name The name of the action, specified in xml file
     * @param params The parameters of that action, should match fields in xml file
     * @return The action object
     */
    public IActionDefinition createAction(String name, Map<String, Object> params) throws ActionDoesNotExistException,
            ReflectionException {

        if (!nameFieldsExists(availableActions, name))
            throw new ActionDoesNotExistException();

        Action a = instantiateClass(actionClasses.getProperty(name), params);
        a.setName(name);
        a.injectGameEventMaster(eventMaster);
        return a;
    }

    /**
     * Create a condition
     * @param name The name of the condition, specified in xml file
     * @param params The parameters of that condition, should match fields in xml file
     * @return The condition object
     */
    public IConditionDefinition createCondition(String name, Map<String, Object> params) throws ConditionDoesNotExistException,
            ReflectionException {

        if (!nameFieldsExists(availableConditions, name))
            throw new ConditionDoesNotExistException();

        // TODO: Check that parameters given are correct

        Condition c = instantiateClass(conditionClasses.getProperty(name), params);
        c.setName(name);
        return c;
    }

    /**
     * Create an objective with a condition and an outcome
     * @param condition The condition
     * @param outcome The outcome
     * @return The new outcome
     */
    public IObjectiveDefinition createObjective(IObjectiveConditionDefinition condition,
                                                          IObjectiveOutcomeDefinition outcome) {
        return new Objective((ObjectiveCondition) condition, (ObjectiveOutcome) outcome);
    }

    public IObjectiveConditionDefinition createObjectiveCondition(String name, Map<String, Object> params) throws ObjectiveConditionDoesNotExistException,
            ReflectionException {
        if (!nameFieldsExists(availableObjectiveConditions, name))
            throw new ObjectiveConditionDoesNotExistException();

        ObjectiveCondition oc = instantiateClass(objectiveConditionClasses.getProperty(name), params);
        oc.setName(name);
        return oc;
    }

    public IObjectiveOutcomeDefinition createObjectiveOutcome(String name, Map<String, Object> params) throws ObjectiveOutcomeDoesNotExistException,
            ReflectionException {
        if (!nameFieldsExists(availableObjectiveOutcomes, name))
            throw new ObjectiveOutcomeDoesNotExistException();

        ObjectiveOutcome oo = instantiateClass(objectiveOutcomeClasses.getProperty(name), params);
        oo.setName(name);
        return oo;
    }

    /**
     * Create a property
     * @param name The name of the property
     * @param value The value of the property
     * @param <T> The type of the property
     * @return The property
     */
    public <T> IPropertyDefinition createProperty(String name, T value) {
        return new Property(name, value);
    }

    private <T> T instantiateClass(String className, Map<String, Object> params) throws ReflectionException {

        try {
            Class clazz = Class.forName(className);
            Constructor constructor = clazz.getConstructor(Map.class);
            return (T)constructor.newInstance(params);

        } catch (Exception e) {
            throw new ReflectionException(e);
        }

    }

    private boolean nameFieldsExists(List<? extends AvailableNameFields> list, String name) {
        for (AvailableNameFields anf: list) {
            if (anf.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private class XMLToAvailableObjectsParser {

        private DocumentBuilder documentBuilder;

        public XMLToAvailableObjectsParser() throws ParserConfigurationException {
            this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }

        public <T extends AvailableNameFields> List<T> getNameFieldsList(String file) throws SAXException, IOException {

            Document doc = documentBuilder.parse(getClass().getClassLoader().getResourceAsStream(file));
            NodeList nodes = doc.getChildNodes().item(0).getChildNodes();
            List<T> nameFieldsList = new ArrayList<>();

            for (int i = 0; i < nodes.getLength(); i++) {
                String name = "";
                List<Field> fields = new ArrayList<>();
                Node node = nodes.item(i);

                if (node.getNodeName().equals("#text"))
                    continue;

                NodeList childNodes = node.getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node n = childNodes.item(j);
                    if (n.getNodeName().equals("name")) {
                        name = n.getTextContent();
                    } else if (n.getNodeName().equals("fields")) {
                        fields = parseFieldsList(n);
                    }
                }
                nameFieldsList.add((T)new AvailableNameFields(name, fields));
            }
            return nameFieldsList;
        }

        private List<Field> parseFieldsList(Node fieldsNode) {
            NodeList children = fieldsNode.getChildNodes();
            List<Field> fields = new ArrayList<>();
            for (int i = 0; i < children.getLength(); i++) {
                Node node = children.item(i);
                if (node.getNodeName().equals("#text"))
                    continue;
                String fieldName = node.getTextContent();
                String fieldType = node.getNodeName();
                fields.add(new Field(fieldName, fieldType));
            }
            return fields;
        }

    }

}
