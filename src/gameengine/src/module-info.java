module gameengine {
    requires java.desktop;
    requires data;
    exports state;
    exports authoring;
    exports engine;
    exports state.objective;
    exports state.attribute;
    exports state.action;
    exports state.actiondecision;
    exports state.agent;
}
