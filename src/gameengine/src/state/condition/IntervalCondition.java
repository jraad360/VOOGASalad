package state.condition;

import state.agent.Agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class to return valid agents based on some timing interval
 * @author David Miron
 */
public class IntervalCondition extends Condition {

    private double intervalMillis;
    private long lastOccurrence;

    /**
     * Create an IntervalCondition
     */
    public IntervalCondition(Map<String, Object> params) {
        super(params);
        this.lastOccurrence = 0;
    }

    @Override
    public void setParams(Map<String, Object> params) {
        this.intervalMillis = (Double)params.get("interval") * 1000;
    }

    /**
     * Return all agents if some interval has passed, or else return none
     * @param agents The agents to filter
     * @return All the agents if some interval has passed, or else an empty list
     */
    @Override
    public List<Agent> getValid(List<Agent> agents) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastOccurrence > intervalMillis) {
            lastOccurrence = currentTime;
            return agents;
        } else {
            return new ArrayList<>();
        }
    }
}
