package state.condition.propertyCondition;

import state.agent.Agent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertyGreaterThanOrEqualToCondition extends PropertyCondition {

    /**
     * Create an IntervalCondition
     */
    public PropertyGreaterThanOrEqualToCondition(Map<String, Object> params) {
        super(params);
    }

    /**
     * Return all agents if some interval has passed, or else return none
     * @param agents The agents to filter
     * @return All the agents if some interval has passed, or else an empty list
     */
    @Override
    public List<Agent> getValid(List<Agent> agents) {
        return agents.stream().filter(agent -> (agent.getProperty(this.propertyName) != null && this.value.compareTo(agent.getProperty(this.propertyName)) <= 0)).collect(Collectors.toList());
    }

}
