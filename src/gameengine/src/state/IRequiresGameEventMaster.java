package state;

import engine.event.GameEventMaster;

public interface IRequiresGameEventMaster {
    void injectGameEventMaster(GameEventMaster eventMaster);
}
