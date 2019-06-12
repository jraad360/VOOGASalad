package state.action.property;

import authoring.exception.PropertyDoesNotExistException;
import state.IRequiresBaseAgent;
import state.action.Action;
import state.agent.Agent;

import java.util.Map;

/**
 * @Author:Luke_Truitt
 * @author Jorge Raad
 * Increases the value of a numerical Property in an Agent by the specified amount.
 */
public class DecrementProperty extends PropertyAction {

    private String propertyName;
    private double amount;

    public DecrementProperty(Map<String, Object> params) {
        super(params);
    }

    public void setParams(Map<String, Object> params) {
        this.propertyName = (String)params.get("property");
        this.amount = (Double)params.get("value");
    }

    /**
     * Decreases the value of the given Property by the amount specified in setParams. If the agent does not
     * have the specified property, then it is left unaffected.
     * @param agent - agent whose property is to be decreased
     * @param deltaTime - amount of time that has passed since the last Level step
     */
    @Override
    public void execute(Agent agent, double deltaTime) {
        try{
            double current_value = (double) agent.getProperty(propertyName);
            current_value -= amount;
            agent.setProperty(propertyName, current_value);
            System.out.println("DAMAGE. " + agent.getName() + "'s HP: " + current_value);
        }
        catch(PropertyDoesNotExistException e) {
            // DO NOTHING, if doesn't exist, don't overwrite
        }
    }
}
