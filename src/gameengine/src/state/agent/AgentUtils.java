package state.agent;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;

/**
 * Class with static methods to be frequently used elsewhere in gameengine.
 * @author Jorge Raad
 * @author Luke Truitt
 */
public class AgentUtils {
    // code from deepClone referenced from Alvin Alexander, http://alvinalexander.com

    /**
     * Get the angle between two agent1 and agent2, in reference to straight left, using unit circle notation
     * @param agent1 The first agent
     * @param agent2 The second agent
     * @return The angle between the two agents
     */
    public static double getAngleBetween(Agent agent1, Agent agent2) {
        Point2D a = new Point2D.Double(agent1.getX(), agent1.getY());
        Point2D b = new Point2D.Double(agent2.getX(), agent2.getY());
        return getAngleBetween(a, b);
    }

    /**
     * Get the angle between two points, in reference to straight left, using unit circle notation.
     * @param a The first point
     * @param b The second point
     * @return The angle between the two points
     */
    public static double getAngleBetween(Point2D a, Point2D b){

        double dx = b.getX() - a.getX();
        dx = dx == 0 ? 1 : dx;
        double dy = a.getY() - b.getY();
        var theta = Math.atan(Math.abs(dy)/Math.abs(dx)) * 180/Math.PI;
        if(dx<=0&&dy>=0) {
            theta = 180 - theta;
        } else if(dx<=0&&dy<=0) {
            theta = 180 + theta;
        } else if(dx>=0&&dy>=0) {
            //theta = theta;
        } else if(dx>=0&&dy<=0) {
            theta = 360 - theta;
        }
        return theta;
    }

    /**
     * This method makes a "deep clone" of any Java object it is given.
     * @author Alvin Alexander, http://alvinalexander.com
     */
    public static Object deepClone(Object object) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }

    /**
     * Creates the rectangle representing the bounding box for the agent. Public because the level needs to handle the
     * removal of agents if they are out of bounds
     * @param agent
     * @return Rectangle representing agent bounds
     */
    public static Rectangle createBoundingRect(Agent agent) {
        int xTopLeft = (int)(agent.getX() - (agent.getWidth() / 2));
        int yTopLeft = (int)(agent.getY() - (agent.getHeight() / 2));
        return new Rectangle(xTopLeft, yTopLeft, agent.getWidth(), agent.getHeight());
    }
}
