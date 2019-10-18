package gui.moveTypes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class NonePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextPane textPane;

	public NonePanel(String text) {
		super();

		Color bg = this.getBackground();
		Font font = new Font("Times New Roman", Font.BOLD, 20);
		
		textPane = new JTextPane();
		textPane.setBackground(bg);
		textPane.setText(text);
		textPane.setFont(font);
		
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		add(textPane);
	}
	
	public NonePanel() {
		this("Use no movement");
	}
}
