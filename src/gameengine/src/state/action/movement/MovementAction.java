package state.action.movement;

import state.IRequiresBaseAgent;
import state.action.Action;
import state.agent.Agent;

import java.util.Map;

/**
 * Class to define how an agent should move.
 * @author David Miron
 */
public abstract class MovementAction extends Action implements IRequiresBaseAgent {

   protected double speed;
   protected Agent baseAgent;

   public MovementAction(Map<String, Object> params) {
      super(params);
   }

   @Override
   public void injectBaseAgent(Agent agent) {
      this.baseAgent = agent;
   }

}
