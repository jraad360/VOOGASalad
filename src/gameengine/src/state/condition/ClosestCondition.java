package state.condition;

import state.IRequiresBaseAgent;
import state.agent.Agent;
import java.util.List;
import java.util.Map;

/**
 * Class to that narrows down a group of agents to the single closest agent.
 * @author Jorge Raad
 */
public class ClosestCondition extends Condition implements IRequiresBaseAgent {

    private Agent baseAgent;

    public ClosestCondition(Map<String, Object> params) {
        super(params);
    }

    @Override
    public void injectBaseAgent(Agent agent) {
        this.baseAgent = agent;
    }

    @Override
    public List<Agent> getValid(List<Agent> agents) {
        // if list has 0 or one agents, then return same list.
        if (agents.size() <= 1) {
            return agents;
        }
        for (int k = 0; k < agents.size() - 1; k++) {
            agents.remove(getFartherAgent(agents.get(k), agents.get(k+1)));
        }
        return agents;
    }

    private Agent getFartherAgent(Agent a, Agent b){
        double aDistance = baseAgent.calculateDistance(a);
        double bDistance = baseAgent.calculateDistance(b);
        if(aDistance >= bDistance) return a;
        return b;
    }

}
