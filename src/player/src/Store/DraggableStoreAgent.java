package Store;

import javafx.scene.image.ImageView;

public class DraggableStoreAgent extends ImageView {

   private static final int SIZE = 60;
   private int storeIndex;
   private double myStartSceneX, myStartSceneY;
   private double myStartXOffset, myStartYOffset;

   public DraggableStoreAgent(String imgUrl, int index) {
      super("file:" + imgUrl);
      this.setFitWidth(SIZE);
      this.setFitHeight(SIZE);
      this.storeIndex = index;
   }
   public double getStartX(){
      return myStartXOffset;
   }

   public double getStartY(){
      return myStartYOffset;
   }

   public void setMyStartXOffset(double x) {
      myStartXOffset = x;
   }

   public void setMyStartYOffset(double y) {
      myStartYOffset = y;
   }

   public double getMyStartSceneX() { return myStartSceneX; }

   public double getMyStartSceneY() { return myStartSceneY; }

   public void setMyStartSceneX(double x) {
      myStartSceneX = x;
   }

   public void setMyStartSceneY(double y) {
      myStartSceneY = y;
   }
   public int getStoreIndex(){ return this.storeIndex; }

}
