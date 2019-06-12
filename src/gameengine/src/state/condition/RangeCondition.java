package state.condition;

import state.IRequiresBaseAgent;
import state.agent.Agent;

import java.util.List;
import java.util.Map;

/**
 * Condition that narrows the given list of agents to only include those within a given range.
 * @author Jorge Raad
 */
public class RangeCondition extends Condition implements IRequiresBaseAgent {

    private Agent baseAgent;
    private double range;

    public RangeCondition(Map<String, Object> params) {
        super(params);
    }

    @Override
    public void injectBaseAgent(Agent agent) {
        this.baseAgent = agent;
    }

    @Override
    public void setParams(Map<String, Object> params) {
        range = (Double)params.get("range");
    }

    @Override
    public List<Agent> getValid(List<Agent> agents) {
        for(int k = 0; k < agents.size(); k++){
            if(baseAgent.calculateDistance(agents.get(k)) > range){
                agents.remove(agents.get(k));
            }
        }
        return agents;
    }
}
