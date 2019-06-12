package state;

import java.io.Serializable;
import java.util.List;

public class AgentReference implements Cloneable, Serializable {


    private String name;
    private double x;
    private double y;
    private double direction;
    private List<Property> instanceProperties;

    public AgentReference(String name, double x, double y, double direction, List<Property> instanceProperties) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.instanceProperties = instanceProperties;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setInstanceProperties(List<Property> instanceProperties) {
        this.instanceProperties = instanceProperties;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    public List<Property> getInstanceProperties() {
        return instanceProperties;
    }
}
