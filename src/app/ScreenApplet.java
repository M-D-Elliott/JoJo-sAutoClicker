package app;

import java.applet.Applet;
import java.awt.Graphics;

public class ScreenApplet extends Applet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paint (Graphics g)
    {
//		System.out.println(g);
        g.drawString("hello",40,30);
    }
}
