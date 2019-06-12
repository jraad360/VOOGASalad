package authoring;

import java.util.List;

/**
 * Interface for authoring environment to use to define an action
 * @author David Miron
 */
public interface IActionDecisionDefinition {

    /**
     * Returns an IActionDefinition representing the Action held by the IActionDecisionDefinition.
     * This is to be used by the Authoring Environment to display and allow the setting of the parameters
     * required by the Action.
     * @return IActionDefinition
     */
    IActionDefinition getAction();

    /**
     * Sets the Action of the IActionDefinition to the given one. This is to be used by the Authoring Environment
     * to set the ActionDecision's Action before the user has defined the Action's parameters.
     * @param actionDefinition
     */
    void setAction(IActionDefinition actionDefinition);

    /**
     * Returns the IConditionDefinitions representing the Conditions held by the IActionDecisionDefinition.
     * This is to be used by the Authoring Environment to display and allow the setting of the parameters
     * required by all the Conditions set for the IActionDecisionDefinition.
     * @return
     */
    List<? extends IConditionDefinition> getConditions();

    /**
     * To be used by the Authoring Environment to remove the Condition specified by the index.
     * @param index
     */
    void removeCondition(int index);

    /**
     * To be used by the Authoring Environment to add the Condition specified.
     * @param conditionDefinition
     */
    void addCondition(IConditionDefinition conditionDefinition);


}
