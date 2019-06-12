package state.condition;

import state.IRequiresBaseAgent;
import state.agent.Agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Condition that given a list with at least one agent, returns a list with only one entry, the baseAgent. When given a
 * list with no Agents, it returns that same empty list. This is to be used when an Action should act upon the baseAgent,
 * such as Property-changing Actions. This can also be used to narrow down the number of times an Action should be run
 * to just once, whether or not the baseAgent is used.
 * @author Jorge Raad
 */
public class DoOnceWithSelfCondition extends Condition implements IRequiresBaseAgent {
    private Agent baseAgent;
    public DoOnceWithSelfCondition(Map<String, Object> params) {
        super(params);
    }

    @Override
    public List<Agent> getValid(List<Agent> agents) {
        // TODO : handle case of only one agent on screen
        if (agents.size() > 0){
            agents = new ArrayList<>();
            agents.add(baseAgent);
        }
//        // pass a dummy agent always, that way the action will always occur once, even if the given list is empty.
//        // This means that other conditions such as interval must be checked afterwards
//        agents = new ArrayList<>();
//        agents.add(this.baseAgent);
        return agents;
    }

    @Override
    public void injectBaseAgent(Agent agent) {
        this.baseAgent = agent;
    }
}
