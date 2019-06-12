package state.condition.propertyCondition;

import state.Property;
import state.agent.Agent;
import state.condition.Condition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertyEqualToCondition extends PropertyCondition {

    /**
     * Create an IntervalCondition
     */
    public PropertyEqualToCondition(Map<String, Object> params) {
        super(params);
    }

    /**
     * Return all agents if some interval has passed, or else return none
     * @param agents The agents to filter
     * @return All the agents if some interval has passed, or else an empty list
     */
    @Override
    public List<Agent> getValid(List<Agent> agents) {
        List<Agent> list = agents.stream().filter(agent -> (this.value.equals(agent.getProperty(this.propertyName)))).collect(Collectors.toList());
        return list;
    }
}
