package Store;

import javafx.scene.control.Button;

/**
 * @author: Mary Gooneratne
 * Main game scene store button
 */
public class StoreButton extends Button {
   private static String STORE_BUTTON_STYLE = "store-button";

   public StoreButton(){
      super();
      this.getStyleClass().add(STORE_BUTTON_STYLE);
   }
}
