package state.action.property;

import authoring.IPropertyDefinition;
import authoring.exception.PropertyDoesNotExistException;
import state.Property;
import state.action.Action;
import state.agent.Agent;

import java.util.Map;

/**
 * Sets the value of the Property of the specified Agent to the indicated value. If the Property does not currently
 * exist in the Agent, then it will be created. If it does, its value will be overwritten.
 * @author Jorge Raad
 */
public class SetProperty extends Action {

    private String property;
    private double value;

    public SetProperty(Map<String, Object> params) {
        super(params);
    }

    public void setParams(Map<String, Object> params) {
        this.property = (String)params.get("property");
        this.value = (Double)params.get("value");
    }

    /**
     * Sets the value of the Property of the specified Agent to the indicated value.
     * @param agent - Agent to be changed
     * @param deltaTime
     * @throws CloneNotSupportedException
     */
    @Override
    public void execute(Agent agent, double deltaTime) throws CloneNotSupportedException {
        agent.overwriteProperty(new Property(property, value));
    }
}
