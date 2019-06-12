package authoring;

/**
 * Interface to represent a single property
 * @author David Miron
 */
public interface IPropertyDefinition<T> {
    String getName();
    void setName(String name);
    T getValue();
    void setValue(T value);
}
