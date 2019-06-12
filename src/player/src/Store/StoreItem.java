package Store;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import state.agent.IPlayerAgent;

/**
 * @author: Mary Gooneratne
 * @Author: Joanna Li
 * Main item storage for Store
 */
public class StoreItem extends VBox {
   private static String STORE_ITEM_STYLE = "store-item";
   private static String MONEY_ICON_STYLE = "money-icon";
   private static String ITEM_PRICE_STYLE = "item-price";

   private DraggableStoreAgent agent;
   private Text priceText;
   private Double price;
   String agentUrl;
   private int  index;


   public StoreItem(IPlayerAgent agent, int index){
      super();
      this.agentUrl = agent.getImageURL();
      this.agent = new DraggableStoreAgent(agentUrl, index);
      this.price = (Double)(agent.getProperty("price"));
      this.index = index;
      this.priceText = new Text(String.valueOf(price));
      this.setStyles();
      this.placeChildren();
   }
   private void placeChildren(){

      this.getChildren().addAll(this.agent, this.priceText);
   }

   private void setStyles(){
      this.priceText.getStyleClass().add("store-text");
      this.getStyleClass().add("store-item");

   }
   public DraggableStoreAgent getDraggableStoreAgent(){
      return agent;
   }

   public void reset(){
      System.out.println(this.getChildren().size());
      this.getChildren().removeAll(this.agent);
      this.agent = new DraggableStoreAgent(agentUrl, index);
      this.getChildren().add(0, this.agent);
   }


}
