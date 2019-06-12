package state.objective.objectiveoutcome;

import state.State;

import java.util.Map;

/**
 * @author Jamie Palka
 * Class to define the functionality of ending the game.
 */
public class GameOverOutcome extends ObjectiveOutcome {

    private static final String GAME_OVER_MESSAGE = "GAME OVER";

    public GameOverOutcome(Map<String, Object> params) {
        super(params);
    }

    public String execute(State state) {
        state.makeGameOver();
        return GAME_OVER_MESSAGE;
    }
}
