package state.action;

import state.IRequiresBaseAgent;
import state.agent.Agent;

import java.util.Map;

/**
 * Action that allows the spinning of an Agent at the given angular speed.
 * @author Jorge Raad
 */
public class Spin extends Action implements IRequiresBaseAgent {

    private Agent baseAgent;
    private double angularSpeed;

    public Spin(Map<String, Object> params) {
        super(params);
    }

    @Override
    public void setParams(Map<String, Object> params) {
        this.angularSpeed = (Double) params.get("angularSpeed");
    }

    @Override
    public void injectBaseAgent(Agent agent) {
        this.baseAgent = agent;
    }

    /**
     * Updates the baseAgent's direction according the angular speed and amount of time passed.
     * @param agent - ignored
     * @param deltaTime - amount of time between Level's step method calls
     * @throws CloneNotSupportedException
     */
    @Override
    public void execute(Agent agent, double deltaTime) throws CloneNotSupportedException {
        baseAgent.setDirection(baseAgent.getDirection() + angularSpeed*deltaTime);
    }
}
