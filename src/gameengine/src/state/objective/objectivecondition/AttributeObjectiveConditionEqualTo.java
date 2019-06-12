package state.objective.objectivecondition;

import state.State;
import state.objective.ObjectiveUtils;

import java.util.Map;

/**
 * @author Jamie Palka
 * Class to define the ObjectiveConditions within the game which are triggered by the value of an attribute of a user
 * being equal to a target value.
 */
public class AttributeObjectiveConditionEqualTo extends AttributeObjectiveCondition {

    public AttributeObjectiveConditionEqualTo(Map<String, Object> params) {
        super(params);
    }

    /**
     * Returns true if the given attribute has a value equal to the target value.
     */
    public boolean evaluate(State state) {

        attribute = ObjectiveUtils.getAttributeFromName(state, attributeName);

        return ((state.getCurrentLevelInt() == level || level == -1) && attribute.getValue() == targetValue);
    }
}
