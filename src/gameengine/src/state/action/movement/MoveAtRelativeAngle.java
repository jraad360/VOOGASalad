package state.action.movement;

import state.Property;
import state.agent.Agent;

import java.util.Map;

/**
 * Allows an agent to move at a constant angle, relative to its orientation
 * @author Jamie Palka
 * @author David Miron
 * @author Luke Truitt
 * @author Jorge Raad
 */
public class MoveAtRelativeAngle extends MovementAction {

    /* The angle to move at, relative to the current orientation, in degrees.
     *              0 means forwards, positive angle means move to the right, and negative angle means left.
     *              ex. angle = 45 means always move forwards and to the right, -90 means always move left.
     */
    private double angle;

    /**
     * The MoveAtRelativeAngle Action results in the agent moving at this angle with relative to its direction, in
     * degrees. An angle of 0 means move forwards, positive angle means move to the right, and negative angle means left.
     *  Ex: angle = -90 means always move left, perpendicular to where it is pointing. angle = 180 means always move backwards.
     *
     * It does not actually act upon any of the agents passed in, the presence of an agent being passed in simply is
     * necessary for it to know that it should be carried out. Because we normally want this action to occur once, not
     * once per agent in the list passed in to the ActionDecision, this action is normally paired with a DoOnceWithSelf condition.
     * @param params
     */
    public MoveAtRelativeAngle(Map<String, Object> params) {
        super(params);
    }

    @Override
    public void setParams(Map<String, Object> params) {
        this.angle = (Double) params.get("angle");
        this.speed = (Double) params.get("speed");
    }

    /**
     * Move the baseAgent at the set angle with respect to its direction.  It uses the speed set in the Movement Action's
     * parameters unless it is overwritten by a "speed" Property in the Agent.
     *
     * @param agent Should be ignored
     */
    @Override
    public void execute(Agent agent, double deltaTime) {
        double currentSpeed;
        try{
            currentSpeed = (double)baseAgent.getProperty("speed");
        }
        catch(Exception e){
            currentSpeed = speed;
        }
        // possibly apply some kind of multiplier or something from properties idk
        //var properties = agent.getProperties();
        double newAbsoluteAngle = baseAgent.getDirection() - angle;
        double xVel = currentSpeed*Math.cos(Math.PI/180*newAbsoluteAngle);
        double yVel = currentSpeed*Math.sin(Math.PI/180*newAbsoluteAngle);
        baseAgent.setLocation(xVel*deltaTime + baseAgent.getX(), baseAgent.getY() - yVel*deltaTime);
    }
}
