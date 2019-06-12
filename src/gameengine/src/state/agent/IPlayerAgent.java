package state.agent;
import state.Property;

import java.beans.PropertyChangeListener;

public interface IPlayerAgent {
    double getX();
    double getY();
    String getImageURL();
    String getName();
    int getWidth();
    int getHeight();
    double getDirection();

    Object getProperty(String property);
    void addPropertyChangeListener(PropertyChangeListener listener);

    Object getPropertyValue(String name);
}
