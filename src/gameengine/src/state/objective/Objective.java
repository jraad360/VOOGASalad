package state.objective;

import authoring.IObjectiveConditionDefinition;
import authoring.IObjectiveDefinition;
import authoring.IObjectiveOutcomeDefinition;
import state.State;
import state.objective.objectivecondition.ObjectiveCondition;
import state.objective.objectiveoutcome.ObjectiveOutcome;

import java.io.Serializable;

public class Objective implements IObjectiveDefinition, Serializable {

    private ObjectiveCondition condition;
    private ObjectiveOutcome outcome;

    public Objective(ObjectiveCondition condition, ObjectiveOutcome outcome) {
        this.condition = condition;
        this.outcome = outcome;
    }

    public void execute(State state) {

        if(condition.evaluate(state)) {
            outcome.execute(state);
        }
    }

    public IObjectiveOutcomeDefinition getOutcome() { return outcome; }
    public void setOutcome(IObjectiveOutcomeDefinition outcome) { this.outcome = (ObjectiveOutcome) outcome; }

    public IObjectiveConditionDefinition getCondition() { return condition; }
    public void setCondition(IObjectiveConditionDefinition condition) { this.condition = (ObjectiveCondition) condition; }
}

