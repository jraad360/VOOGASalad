package authoring;

import authoring.exception.PropertyDoesNotExistException;

import java.util.List;

/**
 * Interface for the authoring environment to use to define an agent
 * @author David Miron
 */
public interface IAgentDefinition {

    double getX();
    void setX(double x);

    double getY();
    void setY(double y);

    int getWidth();
    void setWidth(int width);

    int getHeight();
    void setHeight(int height);

    String getImageURL();
    void setImageURL(String imageURL);

    String getName();
    void setName(String name);

    List<? extends IActionDecisionDefinition> getActionDecisions();
    void removeActionDecision(int index);
    void addActionDecision(IActionDecisionDefinition actionDecision);

    List<? extends IPropertyDefinition> getProperties();
    void removeProperty(String name);
    void addProperty(IPropertyDefinition property);
    <T> void setProperty(String name, T value)  throws PropertyDoesNotExistException;
    Object getPropertyValue(String name);
}
