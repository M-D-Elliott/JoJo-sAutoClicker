package Utils;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;

public class ComponentUtils {
	  public static final int getComponentIndex(Component component) {
	    if (component != null && component.getParent() != null) {
	      Container c = component.getParent();
	      for (int i = 0; i < c.getComponentCount(); i++) {
	        if (c.getComponent(i).equals(component))
	          return i;
	      }
	    }
	
	    return -1;
	  }
	  public static void getComponentTreePosition(Component c, ArrayList<Integer> pos) {
	    if (c.getParent() == null) {
	      return;
	    }
	
	    getComponentTreePosition(c.getParent(), pos);
	
	    pos.add(new Integer(c.getParent().getComponentCount() - getComponentIndex(c)));
	  }
}
