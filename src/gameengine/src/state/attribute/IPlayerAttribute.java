package state.attribute;

import java.beans.PropertyChangeListener;

/**
 * @author Luke Truitt
 * These are things like Health, Money, Settings, etc.
 */
public interface IPlayerAttribute {

    // Returns the specified name of the attribute (ie "Money")
    String getName();
    // Returns the value of the attribute (ie 34)
    double getValue();
    void addPropertyChangeListener(PropertyChangeListener listener);
}
