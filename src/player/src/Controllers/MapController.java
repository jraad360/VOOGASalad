package Controllers;

import Agent.AgentView;
import Panes.MapPane;
import state.agent.IPlayerAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapController{
   private List<AgentView> agentViews;
   private MapPane mapPane;
   private HashMap<IPlayerAgent, AgentView> agentMap = new HashMap<>();

   public MapController(List<IPlayerAgent> playerAgents){
      this.agentViews = new ArrayList<>();
      this.mapPane = new MapPane();
      this.addAgents(playerAgents);
   }

   public void addAgent(IPlayerAgent playerAgent){
      AgentView agentView = new AgentView(playerAgent);
      this.agentViews.add(agentView);
      this.mapPane.addAgent(agentView);
      this.agentMap.put(playerAgent, agentView);
   }

   public void addAgents(List<IPlayerAgent> playerAgents){
      for(IPlayerAgent playerAgent: playerAgents){
         this.addAgent(playerAgent);
      }
   }

   public void removeAgent(IPlayerAgent playerAgent)
   {
      AgentView agentToRemove = this.agentMap.get(playerAgent);
      this.agentViews.remove(agentToRemove);
      this.mapPane.removeAgent(agentToRemove);
      this.agentMap.remove(playerAgent);

   }
   public MapPane getPane(){
      return this.mapPane;
   }
}