package engine.event.events;

import engine.event.GameEvent;
import engine.event.GameEventType;
import state.agent.Agent;

/**
 * GameEvent that should result in the removal of an agent from the list of agents
 * @author David Miron
 */
public class RemoveAgentEvent extends GameEvent {

    private Agent agent;

    /**
     * Create a RemoveAgentEvent
     * @param agent The agent to remove
     */
    public RemoveAgentEvent(Agent agent) {
        this.agent = agent;
        this.type = GameEventType.REMOVE_AGENT;
    }

    /**
     * Get the agent to remove
     * @return The agent to remove
     */
    public Agent getAgent() {
        return this.agent;
    }

}
