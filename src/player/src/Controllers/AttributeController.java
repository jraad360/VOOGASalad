package Controllers;

import Panes.AttributePane;
/**
 * Attribute controller
 * @author Joanna Li
 */
import javafx.beans.property.LongProperty;
import javafx.scene.layout.Pane;
import state.attribute.Attribute;
import state.attribute.IPlayerAttribute;
import Attribute.AttributeView;
import java.util.ArrayList;
import java.util.List;

public class AttributeController {
   List<IPlayerAttribute> attributes;
   AttributePane attributePane;


   public AttributeController(List<IPlayerAttribute> attributes) {
      this.attributes = new ArrayList<>();
      this.attributePane = new AttributePane();
      this.addAttributes(attributes);
   }

   public void addAttribute(IPlayerAttribute attribute)
   {
      AttributeView att = new AttributeView(attribute);
      attributePane.addAttribute(att);
      attributes.add(attribute);
   }

  public void addAttributes(List<IPlayerAttribute> attributes)
  {
     for (IPlayerAttribute att: attributes)
        addAttribute(att);
  }

   public AttributePane getPane(){
      return this.attributePane;
   }

   /*public LongProperty createProperty(IPlayerAttribute playerAttribute)
   {
      String name = playerAttribute.getName();
      Class longProp;
      LongProperty prop;

      try{

      }
   }*/

}
