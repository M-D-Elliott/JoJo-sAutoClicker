package app;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Test extends PositionsObject {
	private JFrame frame;
	
    public Test() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                frame = new JFrame("Testing");
                frame.setUndecorated(true);
                frame.setBackground(new Color(0, 0, 0, 0));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                Point mouseCoords = tryGetMousePosition();
                frame.setLocation(mouseCoords.x, mouseCoords.y);
                frame.setVisible(true);
            }
        });
    }
    
    public void hide() {
    	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public class TestPane extends JPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public TestPane() {
            setOpaque(false);
            setLayout(new GridBagLayout());
            JLabel label = new JLabel("O");
            label.setFont(label.getFont().deriveFont(Font.BOLD, 64));
            label.setForeground(Color.WHITE);
            add(label);
        }

    }
}