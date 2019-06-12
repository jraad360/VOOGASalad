package authoring;

public interface IObjectiveDefinition {

    IObjectiveOutcomeDefinition getOutcome();
    void setOutcome(IObjectiveOutcomeDefinition outcome);

    IObjectiveConditionDefinition getCondition();
    void setCondition(IObjectiveConditionDefinition condition);

}
