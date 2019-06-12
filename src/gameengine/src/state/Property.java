package state;

import authoring.IPropertyDefinition;

import java.io.Serializable;

public class Property<T> implements IPropertyDefinition<T>, Serializable {

    private String name;
    private T value;

    public Property(String name, T value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }
}
