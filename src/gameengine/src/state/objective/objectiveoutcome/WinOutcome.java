package state.objective.objectiveoutcome;

import state.State;
import state.objective.objectiveoutcome.ObjectiveOutcome;

import java.util.Map;

/**
 * @author Jamie Palka
 * Class to define the functionality winning the game.
 */
public class WinOutcome extends ObjectiveOutcome {

    private static final String WIN_MESSAGE = "YOU WIN!";

    public WinOutcome(Map<String, Object> params) { super(params); }

    public String execute(State state) {
        state.win();
        return WIN_MESSAGE;
    }

}
