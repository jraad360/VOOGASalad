package state.attribute;

import authoring.IAttributeDefinition;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * @author Luke Truitt
 * @author Jamie Palka
 * Entire Attribute, used by Engine and Author
 */
public class Attribute implements IPlayerAttribute, Serializable, IAttributeDefinition {
    private String name;
    private double value;
    private PropertyChangeSupport pcs;

    public Attribute(String name, double value) {
        this.name = name;
        this.value = value;
        pcs = new PropertyChangeSupport(this);
    }

    public String getName() {
        return this.name;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void setName(String name) {
        var oldName = this.name;
        this.name = name;
        pcs.firePropertyChange("name", oldName, name);
    }

    public void setValue(double value) {
        var oldValue = this.value;
        this.value = value;
        pcs.firePropertyChange("value", oldValue, value);
    }
}
