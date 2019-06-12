package Panes;


import javafx.scene.control.Button;

/**
 * @author: Mary Gooneratne
 * Main game scene settings button
 */

public class SettingsButton extends Button {
   private static String SETTINGS_BUTTON_STYLE = "settings-button";

   public SettingsButton(){
      super();
      this.getStyleClass().add(SETTINGS_BUTTON_STYLE);
   }
}
