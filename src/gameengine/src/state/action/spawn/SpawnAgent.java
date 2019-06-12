package state.action.spawn;

import engine.event.events.AddAgentEvent;
import state.AgentReference;
import state.action.Action;
import state.agent.Agent;

import java.util.Map;

/**
 * Class to provide functionality of spawning an agent.
 * @author David Miron
 */
public abstract class SpawnAgent extends Action {

    public SpawnAgent(Map<String, Object> params) {
        super(params);
    }

    /**
     * Adds the specified agent to a queue of agents to be added at the end of the Level's step method.
     * @param agent
     */
    protected void spawnAgent(AgentReference agent) {
        eventMaster.triggerAddAgentEvent(new AddAgentEvent(agent));
    }

}
