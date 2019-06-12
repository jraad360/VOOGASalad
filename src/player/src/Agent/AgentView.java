package Agent;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import state.agent.Agent;
import state.agent.IPlayerAgent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author: Mary Gooneratne
 * @author: Joanna Li
 * @author: Luke_Truitt
 * Frontend object for Agents
 */
public class AgentView extends ImageView implements PropertyChangeListener {

   private static String AGENT_STYLE = "demo-agent";
   private double health;
   private double direction;
   private String url;

   private String listen;


   public AgentView(IPlayerAgent playerAgent){
      super();
      this.init(playerAgent);
      this.getStyleClass().add(AGENT_STYLE);
      playerAgent.addPropertyChangeListener(this);

   }

   public void init(IPlayerAgent playerAgent){
      this.url = "file:" + playerAgent.getImageURL();
      this.setImage(new Image(this.url));
      System.out.println("x and y on frontend" + playerAgent.getX() + " " + playerAgent.getY());
      this.setX(playerAgent.getX());
      this.setY(playerAgent.getY());
      this.setFitHeight(playerAgent.getHeight());
      this.setFitWidth(playerAgent.getWidth());
      this.setRotate(playerAgent.getDirection());
   }


   public void propertyChange(PropertyChangeEvent e) {
      if (e.getPropertyName().equals("x")) {
         System.out.println("x from prop change" +e.getNewValue());
         this.setX((Double) e.getNewValue());
      } else if(e.getPropertyName().equals("y")) {
         System.out.println("y from prop change" +e.getNewValue());
         this.setY((Double) e.getNewValue());
      } else if(e.getPropertyName().equals("imageUrl")) {
         this.setImage(new Image((String) e.getNewValue()));
      } else if(e.getPropertyName().equals("width")) {
         this.setFitWidth((Double) e.getNewValue());
      } else if(e.getPropertyName().equals("height")) {
         this.setFitHeight((Double) e.getNewValue());
      } else if(e.getPropertyName().equals("direction")) {
         this.setRotate((Double) e.getNewValue());
      }

   }
}