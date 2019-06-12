package state.condition;


import authoring.GameFactory;
import authoring.IConditionDefinition;
import state.IRequiresBaseAgent;
import state.agent.Agent;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Abstract class to represent some condition for a group of agents
 * @author David Miron
 */
public abstract class Condition implements IConditionDefinition, Serializable, Cloneable {

    private String name;
    private Map<String, Object> params;

    public Condition(Map<String, Object> params) {
        this.params = params;
        setParams(params);
    }

    @Override
    public void setParams(Map<String, Object> params) {
        // Do nothing
        // This method should be overridden by subclasses that need parameters
    }

    @Override
    public Map<String, ? extends Object> getParams() {
        return this.params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Filter out agents based on some condition
     * @param agents The agents to filter
     * @return A new version of the list of agents, filtered based on some condition
     */
    public abstract List<Agent> getValid(List<Agent> agents);

    @Override
    public Condition clone() throws CloneNotSupportedException{
        Condition clonedCondition = (Condition)super.clone();
//        if (IRequiresBaseAgent.class.isAssignableFrom(this.getClass())){
//            ((IRequiresBaseAgent)clonedCondition).injectBaseAgent(clonedBaseAgent);
//        }
        return clonedCondition;
    }

}
