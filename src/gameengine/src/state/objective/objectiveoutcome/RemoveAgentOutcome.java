package state.objective.objectiveoutcome;

import state.State;
import state.agent.Agent;
import state.objective.ObjectiveUtils;
import state.objective.objectiveoutcome.ObjectiveOutcome;

import java.util.List;
import java.util.Map;

/**
 * @author Jamie Palka
 * @author Luke Truitt
 * Class to define the functionality of removing an agent.
 */
public class RemoveAgentOutcome extends ObjectiveOutcome {

    private Agent agent;
    protected String agentIdentifierProperty; // property NAME by which to choose agents

    public RemoveAgentOutcome(Map<String, Object> params) { super(params); }

    @Override
    public void setParams(Map<String, Object> params) {
        super.setParams(params);
        this.agentIdentifierProperty = (String) params.get("agentIdentifierProperty");
    }

    public String execute(State state) {

        List<Agent> agentList = ObjectiveUtils.getRelevantAgents(state, agentIdentifierProperty);

        for (Agent agent : agentList) {
            state.getCurrentLevel().removeAgent(agent);
        }
        return null;
    }
}
