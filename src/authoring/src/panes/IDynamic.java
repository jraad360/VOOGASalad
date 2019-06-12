package panes;

import java.util.List;

/**
 * Internal interface that allows for communication between the agent inventory and the x attribute editor.
 *
 */
public interface IDynamic {

    /*
     * If we create an agent and the agent is clicked, the attribute panel needs to diplay the agent's attributes.
     */
    List<Object> getAttributes();

    /**
     * We'll also need to set the attributes as well
     */
    void setAttributes(List<Object> attributes);
}

