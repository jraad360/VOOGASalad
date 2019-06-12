/**
 * Internal interface that allows for communication between the agent inventory and the x attribute editor.
 *
 */
public interface IDynamic {

    /*
     * If we create an agent and the agent is clicked, the attribute panel needs to diplay the agent's attributes.
     */
    public List<Object> getAttributes();

    /**
     * We'll also need to set the attributes as well
     */
    public void setAttributes(List<Object>);


}