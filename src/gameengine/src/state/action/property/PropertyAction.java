package state.action.property;

import state.IRequiresBaseAgent;
import state.action.Action;
import state.agent.Agent;

import java.util.Map;

public abstract class PropertyAction extends Action implements IRequiresBaseAgent {
    protected Agent baseAgent;

    public PropertyAction(Map<String, Object> params) {
        super(params);
    }

    @Override
    public void injectBaseAgent(Agent agent) {
        this.baseAgent = agent;
    }
}
