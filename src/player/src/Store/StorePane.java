package Store;

import frontend_objects.DraggableAgentView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import state.agent.IPlayerAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Mary Gooneratne
 * Pane showing store after store button pressed
 */

public class StorePane extends HBox {
   private List<StoreItem> storeItems;

   public StorePane(){
   }

   public StorePane(List<IPlayerAgent> options){
      this.storeItems = new ArrayList<>();
      this.updateInventory(options);
      System.out.println("Number of store pane options...." + options.size());
   }

   private void updateChildren(){
      this.getChildren().removeAll();
      for(StoreItem item: storeItems) {
         this.getChildren().add(item);
      }
   }

   public void updateInventory(List<IPlayerAgent> agents){
      for(int i = 0; i < agents.size(); i++){
         this.storeItems.add(new StoreItem(agents.get(i), i));
      }
      this.updateChildren();
   }

   public List<StoreItem> getStoreItems(){
      return this.storeItems;
   }

   public void reset(int index){
      this.storeItems.get(index).reset();;
   }
}
