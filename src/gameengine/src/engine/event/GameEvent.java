package engine.event;

/**
 * Abstract class that is all events, does some operation on the state
 * @author David Miron
 */
public abstract class GameEvent {

    protected GameEventType type;

    public GameEventType getType() {
        return this.type;
    }

}
