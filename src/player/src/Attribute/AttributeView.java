package Attribute;
/**
 * Attribute front-end class
 * @author Joanna Li
 */

import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.text.Text;
import state.attribute.IPlayerAttribute;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AttributeView extends Text implements PropertyChangeListener {
    private String name;
    private double value;
    //private LongProperty property = new SimpleLongProperty(0);

    public AttributeView(IPlayerAttribute attribute){
        super();
        this.init(attribute);
        attribute.addPropertyChangeListener(this);
        this.textProperty().setValue(name+ ": " + this.value);
    }

    public void init(IPlayerAttribute attribute){
        this.name = attribute.getName();
        // FIX THIS

        this.value = (attribute.getValue());
        attribute.addPropertyChangeListener(this);

    }

    private void setName(String name) {
        this.name = name;
    }

    private void setValue(double value) {
        this.value = value;
    }

    public void propertyChange(PropertyChangeEvent e) {
        if(e.getPropertyName().equals("name")) {
            this.setName((String) e.getNewValue());
        } else if(e.getPropertyName().equals("value")) {
            this.setValue((Double) e.getNewValue());
        }
        this.textProperty().setValue(name + ": " + this.value);
    }



}
