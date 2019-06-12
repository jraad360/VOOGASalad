package state.objective;

import state.Property;
import state.State;
import state.agent.Agent;
import state.attribute.Attribute;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveUtils {

    //name of objective OBJECTIVE_IDENTIFICATION_PROPERTY in an Agent's properties list
    public static final String OBJECTIVE_IDENTIFICATION_PROPERTY = "objectiveID";

//    public static Agent getAgentFromObjectiveIdentificationPropertyValue(State state, String objectiveIdentificationPropertyValue) {
//        Agent significantAgent = null;
//        for(Agent agent : state.getCurrentAgents()) {
//            if(((Comparable) agent.getPropertyValue(OBJECTIVE_IDENTIFICATION_PROPERTY)).compareTo(objectiveIdentificationPropertyValue) == 0) {
//                significantAgent = agent;
//            }
//        }
//        return significantAgent;
//    }

    /**
     * Returns a list of the agents that have a property with the property name
     */
    public static List<Agent> getRelevantAgents(State state, String propertyName) {

        List<Agent> agents = new ArrayList<Agent>();

        for(Agent agent : state.getCurrentAgents()) {

            long count = agent.getProperties().stream().filter(property->property.getName().equals(propertyName)).count();
            if(count != 0) {
                agents.add(agent);
            }
        }

        return agents;

    }

    public static Attribute getAttributeFromName(State state, String attributeName) {

        for(Attribute attribute : state.getCurrentAttributes()) {
            if (attribute.getName().equals(attributeName)) {
                return attribute;
            }
        }
        return null;
    }
}
