package Controllers;
/**
 * Controller class to affect the Level
 * @author Joanna Li
 * @author Mary G
 * @author Luke Truitt
 */

import Level.IGameApplication;
import Panes.AttributePane;
import Panes.MapPane;
import Store.StoreItem;
import Store.StorePane;
import state.IPlayerLevelState;
import state.Property;
import state.agent.IPlayerAgent;
import state.agent.PlayerAgent;
import state.attribute.IPlayerAttribute;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class LevelController implements PropertyChangeListener {
   MapController mapController;
   AttributeController attributeController;
   StoreController storeController;
   IGameApplication gameApp;

   public LevelController(IPlayerLevelState state){
      this.mapController = new MapController(state.getImmutableAgents());
      this.attributeController = new AttributeController(state.getImmutableAttributes());
      this.storeController = new StoreController(state.getImmutableOptions());
      state.addPropertyChangeListener(this);
   }

   private ArrayList<IPlayerAgent> dummyStore(){
      PlayerAgent forStore = new PlayerAgent(10, 10, 10, 10, 10, "zombie", 10, "zombie.gif");
      PlayerAgent forStore2 = new PlayerAgent(10, 10, 10, 10, 10, "zombie", 10, "zombie.gif");
      Property dummy = new Property("price", 10);
      forStore.addProperty(dummy);
      forStore2.addProperty(dummy);
      ArrayList<IPlayerAgent> dummyOptions = new ArrayList<>();
      dummyOptions.add(forStore);
      dummyOptions.add(forStore2);
      return dummyOptions;
   }

   public void setGame(IGameApplication a)
   {
      gameApp = a;
   }



   public MapPane getMapPane(){
      return this.mapController.getPane();
   }

   public AttributePane getAttributePane(){
      return this.attributeController.getPane();
   }

   public StorePane getStorePane(){
      return this.storeController.getPane();
   }

   public List<StoreItem> getInventory(){
      return this.storeController.getInventory();
   }

   public void propertyChange(PropertyChangeEvent e){
      if(e.getPropertyName().equals("agentAdd"))
         this.mapController.addAgent((IPlayerAgent)(e.getNewValue()));
      else if (e.getPropertyName().equals("agentRemove"))
         this.mapController.removeAgent((IPlayerAgent)(e.getOldValue()));
      else if(e.getPropertyName().equals("attributeAdd"))
         this.attributeController.addAttribute((IPlayerAttribute)(e.getNewValue()));
      else if(e.getPropertyName().equals("attributeRemove")){}
      else if(e.getPropertyName().equals("Game Over"))
      {
        gameApp.getPrimaryStage().setScene(gameApp.getSplashScene().gameOver());
      }
      else if(e.getPropertyName().equals("Won"))
      {
         gameApp.getPrimaryStage().setScene(gameApp.getSplashScene().win());
      }
      else if(e.getPropertyName().equals("inventoryUpdate"))
         this.storeController.updateInventory((List<IPlayerAgent>)(e.getNewValue()));

   }
}
