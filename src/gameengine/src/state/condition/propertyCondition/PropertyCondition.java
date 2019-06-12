package state.condition.propertyCondition;

import state.agent.Agent;
import state.condition.Condition;

import java.util.List;
import java.util.Map;

public abstract class PropertyCondition extends Condition {

    protected Comparable value;
    protected String propertyName;

    public PropertyCondition(Map<String, Object> params) {
        super(params);
    }

    @Override
    public void setParams(Map<String, Object> params) {
        this.propertyName = (String) params.get("property");
        this.value = (Comparable) params.get("value");
    }

    @Override
    public abstract List<Agent> getValid(List<Agent> agents);
}
