package state.objective.objectiveoutcome;

import state.State;

import java.util.Map;

/**
 * @author Jamie Palka
 * Class to define the functionality of moving to the next level.
 */
public class NextLevelOutcome extends ObjectiveOutcome {

    public NextLevelOutcome(Map<String, Object> params) { super(params); }

    public String execute(State state) {
        state.nextLevel();
        return null;
    }

}
