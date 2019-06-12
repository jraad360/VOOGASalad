package state.condition;

import state.IRequiresBaseAgent;
import state.agent.Agent;
import state.agent.Agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A Condition that narrows down the given list of agents to only include the agents that are currently intersecting
 * with the baseAgent.
 * @author Jorge Raad
 */

//TODO: fix collision code so zombies coming in from right hit the pea
public class CollisionCondition extends Condition implements IRequiresBaseAgent {

    private Agent baseAgent;

    public CollisionCondition(Map<String, Object> params) {
        super(params);
    }

    @Override
    public void injectBaseAgent(Agent agent) {
        this.baseAgent = agent;
    }

    @Override
    public List<Agent> getValid(List<Agent> agents) {
        return getCollidingAgents(agents);
    }

    protected List<Agent> getCollidingAgents(List<Agent> agents) {
        List<Agent> newAgents = new ArrayList<>();
        for (Agent agent: agents){
            if(baseAgent.isColliding(agent)){
                newAgents.add(agent);
            }
        }
        return newAgents;
    }
}
