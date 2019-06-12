package Panes;

import Agent.AgentView;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

import java.util.List;

public class MapPane extends Pane {
   private static double WIDTH = 1000;
   private static double HEIGHT = 1000;

   private Canvas myBackgroundCanvas;
   private double myHeight;
   private double myWidth;

   public MapPane(){
      super();
      this.myHeight = HEIGHT;
      this.myWidth = WIDTH;
      //this.initBackground();
   }

   public void addAgent(AgentView agentView){
      this.getChildren().add(agentView);
   }

   public void addAgents(List<AgentView> agentViews){
      for(AgentView a: agentViews){
         this.addAgent(a);
      }
   }

   public void removeAgent(AgentView agentView)
   {
      this.getChildren().remove(agentView);
   }

   public void clearAgents(){
      this.getChildren().removeAll();
   }

   private void initBackground(){
      this.myBackgroundCanvas = new Canvas(myWidth,myHeight);
      this.getChildren().add(myBackgroundCanvas);
   }

}