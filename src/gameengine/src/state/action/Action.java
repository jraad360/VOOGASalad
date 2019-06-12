package state.action;

import authoring.IActionDefinition;
import authoring.exception.PropertyDoesNotExistException;
import engine.event.GameEventMaster;
import state.IRequiresBaseAgent;
import state.IRequiresGameEventMaster;
import state.agent.Agent;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

/**
 * An abstract class to define the common functionality of Actions.
 * @author Jamie Palka
 * @author David Miron
 */

public abstract class Action implements IActionDefinition, IRequiresGameEventMaster, Serializable, Cloneable {

    protected GameEventMaster eventMaster;
    private Map<String, Object> params;
    private String name;

    public Action(Map<String, Object> params) {
        this.params = params;
        setParams(params);
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        // Do nothing
        // This method should be overridden by subclasses that need parameters
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Inject the GameEventMaster to an action
     * @param eventMaster The Event Master
     */
    public void injectGameEventMaster(GameEventMaster eventMaster) {
        this.eventMaster = eventMaster;
    }

    /**
     * @param agent
     * For some movements, the baseAgent's destination/direction is defined by the agent parameter.
     * For other movements, the agent parameter is ignored and the point passed into the constructor is used.
     * For example, the movements defined within collisions.
     * For all other actions, the baseAgent's action is directed toward the agent parameter.
     * For example, an agent spawned will go in the direction of the agent parameter or
     * a melee action will be executed on the agent parameter.
     */
    public abstract void execute(Agent agent, double deltaTime) throws CloneNotSupportedException, PropertyDoesNotExistException;
    // TODO assumption in comment correct?

    @Override
    public Action clone() throws CloneNotSupportedException{
        Action clonedAction = (Action)super.clone();
//        if (IRequiresBaseAgent.class.isAssignableFrom(this.getClass())){
//            ((IRequiresBaseAgent)clonedAction).injectBaseAgent(clonedBaseAgent);
//        }
        return clonedAction;
    }

}
