package Panes;

import javafx.scene.image.ImageView;

/**
 * @author: Mary Gooneratne
 * Front end object to display health attribute
 */

public class HealthBar extends ImageView {
   private static String HEALTH_BAR_STYLE = "health-bar";
   private int health;

   public HealthBar(){
      super();
      this.getStyleClass().add("health-bar");
   }

}
