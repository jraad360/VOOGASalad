package authoring;

import java.beans.PropertyChangeListener;

public interface IAttributeDefinition {

    String getName();
    void setName(String name);

    double getValue();
    void setValue(double value);

}
