public interface IMapPane {

    /**
     * can be used by other parts of the authoring enviroment to set the background
     */
    public void setBackground(String imageURL);

    /*
     * Used to add agent to a list of agents on the map for record
     */
    public void addAgent(Agent agent);

    /*
     * Can be used to obtain agents set on the map for the authoring environment
     */
    public List<Agent> getAgents();

}