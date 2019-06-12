package state.action.destroy;

import engine.event.events.RemoveAgentEvent;
import state.IRequiresBaseAgent;
import state.action.Action;
import state.agent.Agent;

import java.util.Map;

/**
 * Action used to remove the given agent from the list of current Agents in the Level.
 * The removal will occur after updating all of the agents, so it will not result in any NullPointerExceptions
 * or interference with any of the other actions to be run.
 * @author:Luke_Truitt
 * @author Jorge Raad
 */
public class DestroyAgent extends Action implements IRequiresBaseAgent {

    private Agent baseAgent;

    /**
     * This Action is meant to remove any agent it is called upon from the level.
     * @param params
     */
    public DestroyAgent(Map<String, Object> params) {
        super(params);
    }

    protected void destroyAgent(Agent agent) {
        eventMaster.triggerRemoveAgentEvent(new RemoveAgentEvent(agent));
    }

    @Override
    public void injectBaseAgent(Agent agent) {
        this.baseAgent = agent;
    }

    /**
     * Tells the eventMaster to queue this agent for removal from the level.
     * @param agent - agent to be removed
     */
    @Override
    public void execute(Agent agent, double deltaTime) throws CloneNotSupportedException {
        destroyAgent(agent);
    }
}
