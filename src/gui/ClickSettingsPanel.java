package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import Utils.IntUtils;
import extensions.JIntegerFieldMinMax;

public class ClickSettingsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	establishing panel fields and labels
	private JTextField delayField;
	private JLabel delayLabel;
	private JCheckBox infiniteClicksCheck;
	private JLabel infiniteClicksLabel;
	private JTextField clicksField;
	private JLabel clicksLabel;
	private JCheckBox graphicsCheck;
	private JLabel graphicsLabel;
	
//	Limits to above fields.
	private static int _delayMinValue = 1;
	private static int _delayMaxValue = 9999;
	private static int _delayMaxLength = IntUtils.length(_delayMaxValue);
	private static int _clicksMinValue = 1;
	private static int _clicksMaxValue = 999999;
	private static int _clicksMaxLength = IntUtils.length(_clicksMaxValue);
	
//	initial values for fields.
	private String delayInit = "10";
	private String clicksInit = "150";
	private boolean infiniteClicksInit = true;
	private boolean graphicsInit = false;
	public ClickSettingsPanel() {
		super();
		Dimension dim = getPreferredSize();
		dim.height = 120;
		setPreferredSize(dim);
		delayField = new JIntegerFieldMinMax(delayInit, _delayMaxLength, _delayMinValue, _delayMaxValue);
		delayLabel = new JLabel("Delay (ms): ");
		delayLabel.setDisplayedMnemonic(KeyEvent.VK_M);
		delayLabel.setLabelFor(delayField);
		
		infiniteClicksCheck = new JCheckBox();
		infiniteClicksCheck.setSelected(infiniteClicksInit);
		infiniteClicksLabel = new JLabel("Infinite Clicks: ");
		infiniteClicksLabel.setLabelFor(infiniteClicksCheck);
		
		clicksField = new JIntegerFieldMinMax(clicksInit, _clicksMaxLength, _clicksMinValue, _clicksMaxValue);
		clicksLabel= new JLabel("Clicks: ");
		clicksLabel.setDisplayedMnemonic(KeyEvent.VK_C);
		clicksLabel.setLabelFor(clicksField);
		clicksField.setEnabled(false);
		clicksLabel.setEnabled(false);
		
		infiniteClicksCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				enableClicksField();
			}
			
		});
		
		graphicsCheck = new JCheckBox();
		graphicsCheck.setSelected(graphicsInit);
		graphicsLabel = new JLabel("Display Graphics");
		graphicsLabel.setLabelFor(graphicsCheck);
		graphicsCheck.setEnabled(graphicsInit);
		graphicsLabel.setEnabled(graphicsInit);

		TitledBorder innerBorder = BorderFactory.createTitledBorder("Click Settings");
		innerBorder.setTitleColor(Color.BLUE);
		Border outerBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);

		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		layoutComponents();
	}
	
	public void layoutComponents() {

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = gc.weighty = 1.0;

		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 8, 0, 0);
		
		// ////////// row ///////////////////////////////////
		gc.gridx = 0;
		gc.gridy = 0;
		add(delayLabel, gc);

		gc.gridx++;
		add(delayField, gc);
		
		// //////////row ///////////////////////////////////
		
		gc.gridy++;
		
		gc.gridx = 0;
		add(infiniteClicksLabel, gc);

		gc.gridx++;
		add(infiniteClicksCheck, gc);
		
		gc.gridx++;
		add(clicksLabel, gc);

		gc.gridx++;
		add(clicksField, gc);
		
		// //////////row ///////////////////////////////////
		
		gc.gridy++;
		
		gc.gridx = 0;
		add(graphicsLabel, gc);

		gc.gridx++;
		add(graphicsCheck, gc);
		
	}
	
	public void enableClicksField() {
		boolean enabled = !infiniteClicksCheck.isSelected();
		clicksField.setEnabled(enabled);
		clicksLabel.setEnabled(enabled);
	}
	
	public JTextField getDelayField() {
		return delayField;
	}
	public void setDelayField(JTextField delayField) {
		this.delayField = delayField;
	}
	public JCheckBox getInfiniteClicksCheck() {
		return infiniteClicksCheck;
	}
	public void setInfiniteClicksCheck(JCheckBox infiniteClicksCheck) {
		this.infiniteClicksCheck = infiniteClicksCheck;
	}
	public JTextField getClicksField() {
		return clicksField;
	}
	public void setClicksField(JTextField clicksField) {
		this.clicksField = clicksField;
	}

	public JCheckBox getGraphicsCheck() {
		return graphicsCheck;
	}

	public void setGraphicsCheck(JCheckBox graphicsCheck) {
		this.graphicsCheck = graphicsCheck;
	}
	
	
}
