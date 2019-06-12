package state;

import state.agent.Agent;

public interface IRequiresBaseAgent {
    void injectBaseAgent(Agent agent);
}
