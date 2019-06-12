package state.objective.objectiveoutcome;

import authoring.IObjectiveOutcomeDefinition;
import state.State;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Jamie Palka
 * Abstract class to define functions necessary for all ObjectiveOutcomes.
 */
abstract public class ObjectiveOutcome implements IObjectiveOutcomeDefinition, Serializable {

    // name that corresponds to the value of the OBJECTIVE_IDENTIFICATION_PROPERTY in params (from authoring) that
    // the target agent has
    public static final String OBJECTIVE_IDENTIFICATION_PROPERTY_PARAMS = "objectiveIdentificationValue";

    protected Map<String, Object> params;
    protected String name;

    public ObjectiveOutcome(Map<String, Object> params) {
        this.params = params;
        setParams(params);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.name = (String) params.get("name");
        // ObjectiveOutcome classes that need parameters will implement this
    }

    /**
     * Executes the ObjectiveOutcome on the respective state.
     * @param state the current state which the outcome will be executed on.
     */
    abstract public String execute(State state);

}
