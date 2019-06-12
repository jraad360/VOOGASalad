package engine.event;

import engine.event.events.AddAgentEvent;
import engine.event.events.RemoveAgentEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class to handle all game events
 * @author David Miron
 */
public class GameEventMaster {

    private List<Consumer<RemoveAgentEvent>> removeAgentListeners;
    private List<Consumer<AddAgentEvent>> addAgentListeners;

    public GameEventMaster() {
        this.removeAgentListeners = new ArrayList<>();
        this.addAgentListeners = new ArrayList<>();
    }

    public void addRemoveAgentListener(Consumer<RemoveAgentEvent> listener) {
        removeAgentListeners.add(listener);
    }

    public void triggerRemoveAgentEvent(RemoveAgentEvent event) {
        for (Consumer<RemoveAgentEvent> listener: removeAgentListeners)
            listener.accept(event);
    }

    public void addAddAgentListener(Consumer<AddAgentEvent> listener) {
        addAgentListeners.add(listener);
    }

    public void triggerAddAgentEvent(AddAgentEvent event) {
        for (Consumer<AddAgentEvent> listener: addAgentListeners)
            listener.accept(event);
    }

}
