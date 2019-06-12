package authoring;

import java.util.Map;

/**
 * Interface that contains methods to define some object with a name and list of fields (parameters)
 * @author David Miron
 */
public interface INameFieldsDefinition {

    String getName();

    Map<String, ? extends Object> getParams();
    void setParams(Map<String, Object> params);
}
