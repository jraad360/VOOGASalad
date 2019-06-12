package engine.event.events;

import engine.event.GameEvent;
import engine.event.GameEventType;
import state.AgentReference;
import state.agent.Agent;

/**
 * Class to represent an event to create a new agent
 * @author David Miron
 */
public class AddAgentEvent extends GameEvent {

    private AgentReference agentReference;

    /**
     * Create a AddAgentEvent
     * @param agent The agent to add
     */
    public AddAgentEvent(AgentReference agentReference) {
        this.agentReference = agentReference;
    }

    public AgentReference getAgentReference() {
        return agentReference;
    }
}
