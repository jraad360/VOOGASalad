package state.action.movement;

import state.IRequiresPaths;
import state.agent.Agent;
import state.agent.AgentUtils;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

/**
 * @author David Miron
 * @author Jorge Raad
 * Allows an agent to move on a pre-defined path.
 */
public class MoveOnPointPath extends MovementAction implements IRequiresPaths {

    public static final String PATHS_PROPERTY_NAME = "pathName";

    private boolean hasDoneFirstExecute;
    private Map<String, List<Point2D>> paths;

    private List<Point2D> points;
    private int currPoint;

    private static final int TOLERANCE = 2;

    public MoveOnPointPath(Map<String, Object> params) {
        super(params);
    }

    @Override
    public void injectPaths(Map<String, List<Point2D>> paths) {
        this.paths = paths;
    }

    @Override
    public void setParams(Map<String, Object> params) {
        speed = (Double) params.get("speed");
        currPoint = 0;
        hasDoneFirstExecute = false;
    }

    /**
     * Move the baseAgent on a pre-defined path outlined by points.
     * @param agent The agent to move to.
     */
    @Override
    public void execute(Agent agent, double deltaTime) {
        double currentSpeed;
        try {
            currentSpeed = (double) baseAgent.getProperty("speed");
        } catch (Exception e) {
            currentSpeed = speed;
        }

        try {
            if (!hasDoneFirstExecute) {
                String pathName = (String) baseAgent.getProperty(PATHS_PROPERTY_NAME);
                points = paths.get(pathName);
                hasDoneFirstExecute = true;
            }

            if (distance(baseAgent.getX(), baseAgent.getY(), points.get(currPoint)) < TOLERANCE) {
                currPoint++;
            }

            if (currPoint >= points.size()) {
                return;
            }

            Point2D p = points.get(currPoint);
            Point2D a = new Point2D.Double(baseAgent.getX(), baseAgent.getY());
            double angle = AgentUtils.getAngleBetween(a, p);
            double vx = currentSpeed * Math.cos(angle);
            double vy = -currentSpeed * Math.sin(angle);
            baseAgent.setLocation(baseAgent.getX() + vx * deltaTime,
                    baseAgent.getY() + vy * deltaTime);
        }
        catch(Exception e){
            currentSpeed = 0;
        }
    }

    private double distance(double x1, double y1, Point2D point2) {
        return Math.sqrt(Math.pow(point2.getX() - x1, 2) +
                         Math.pow(point2.getY() - y1, 2));
    }
}
