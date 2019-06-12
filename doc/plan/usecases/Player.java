public class Store extends Scene {
    private Map<AgentState, int> availableAgents;
    private EngineHandler gameEngine;

    public Store(){
        this.availableAgents = gameEngine.getStore();
    }

    protected void buyItem(int itemIndex) throws InsufficientFundsException{
        if(this.canBuy()){
            gameEngine.buyItem(availableAgents.keySet.get(itemIndex));
        }
        else {
            throw InsufficientFundsExceptions();
        }
    }

    private boolean canBuy(int itemIndex){
        return gameEngine.canBuy(availableAgent.get(itemIndex));
    }

}

public class Level extends Scene {
    private GameMap map;
    private List<AgentView> agents;
    private int id;
    private EngineHandler gameEngine;

    public Level(){
        this.map = gameEngine.getMap(id);
        this.agents = gameEngine.getLevel(id);
    }
}