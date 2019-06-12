package state.action;

import state.IRequiresBaseAgent;
import state.agent.AgentUtils;
import state.agent.Agent;

import java.util.Map;

/**
 * Action that changes the direction of the agent to face the given Agent.
 * @author Jorge Raad
 */
public class PointToAgent extends Action implements IRequiresBaseAgent {

    private Agent baseAgent;

    public PointToAgent(Map<String, Object> params) {
        super(params);
    }

    @Override
    public void injectBaseAgent(Agent agent) {
        this.baseAgent = agent;
    }

    /**
     * Updates the baseAgent's direction to be facing the given Agent
     * @param agent - agent to be pointed to
     * @param deltaTime
     * @throws CloneNotSupportedException
     */
    @Override
    public void execute(Agent agent, double deltaTime) throws CloneNotSupportedException {
        baseAgent.setDirection(AgentUtils.getAngleBetween(baseAgent, agent));
    }
}
