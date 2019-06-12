package state.condition;

import state.IRequiresBaseAgent;
import state.agent.Agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is to be used as a true-false Condition. It allows the checking of whether or not an
 * Action should be run or not. If it is to run (there is no collision), the Action associated with the
 * Condition will only run once, since the list of Agents is replaced with a list of size 1.
 * @author Jorge Raad
 */
public class IsNotCollidingCondition extends Condition implements IRequiresBaseAgent {
    public IsNotCollidingCondition(Map<String, Object> params) {
        super(params);
    }

    private Agent baseAgent;

    @Override
    public List<Agent> getValid(List<Agent> agents) {
        for(Agent a : agents){
            if(baseAgent.isColliding(a)){
                return new ArrayList<>();
            }
        }
        agents = new ArrayList<>();
        agents.add(baseAgent);
        return agents;
    }

    @Override
    public void injectBaseAgent(Agent agent) {
        this.baseAgent = agent;
    }
}
