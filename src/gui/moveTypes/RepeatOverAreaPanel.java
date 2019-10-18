package gui.moveTypes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Utils.IntUtils;
import extensions.JIntegerFieldMinMax;

public class RepeatOverAreaPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	fields for this panel
	private JTextField xDensityField;
	private JLabel xDensityLabel;
	private JTextField yDensityField;
	private JLabel yDensityLabel;
	private JTextField xRepeatField;
	private JLabel xRepeatLabel;
	private JTextField yRepeatField;
	private JLabel yRepeatLabel;
	
//	Limits to above fields.
	private static int _repeatMinValue = 0;
	private static int _repeatMaxValue = 99;
	private static int _repeatMaxLength = IntUtils.length(_repeatMaxValue);
	private static int _densityMinValue = 5;
	private static int _densityMaxValue = 9999;
	private static int _densityMaxLength = IntUtils.length(_densityMaxValue);
	
//	initial values for fields.
	private String repeatInit = "1";
	private String densityInit = "50";
	
	public RepeatOverAreaPanel() {
		xRepeatField = new JIntegerFieldMinMax(repeatInit, _repeatMaxLength, _repeatMinValue, _repeatMaxValue);
		xRepeatLabel = new JLabel("X Repeat: ");
		xRepeatLabel.setLabelFor(xRepeatField);
		
		xDensityField = new JIntegerFieldMinMax(densityInit, _densityMaxLength, _densityMinValue, _densityMaxValue);
		xDensityLabel = new JLabel("X Density (px): ");
		xDensityLabel.setLabelFor(xDensityField);
		
		yRepeatField = new JIntegerFieldMinMax(repeatInit, _repeatMaxLength, _repeatMinValue, _repeatMaxValue);
		yRepeatLabel = new JLabel("Y Repeat: ");
		yRepeatLabel.setLabelFor(yRepeatField);
		
		yDensityField = new JIntegerFieldMinMax(densityInit, _densityMaxLength, _densityMinValue, _densityMaxValue);
		yDensityLabel = new JLabel("Y density (px): ");
		yDensityLabel.setLabelFor(yDensityField);
		
		layoutComponents();
	}
	public void layoutComponents() {

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = gc.weighty = 1.0;

		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 8, 0, 0);
		
		gc.gridy = 0;
		
		// ////////// Row ///////////////////////////////////
		gc.gridx = 0;
		add(xRepeatLabel, gc);

		gc.gridx++;
		add(xRepeatField, gc);
		
		gc.gridx++;
		add(xDensityLabel, gc);
		
		gc.gridx++;
		add(xDensityField, gc);

		// //////////Row ///////////////////////////////////
		gc.gridy++;
		
		gc.gridx = 0;
		add(yRepeatLabel, gc);

		gc.gridx++;
		add(yRepeatField, gc);
		
		gc.gridx++;
		add(yDensityLabel, gc);

		gc.gridx++;
		add(yDensityField, gc);
	}
//	getters and setters for private fields
	public JTextField getxDensityField() {
		return xDensityField;
	}
	public void setxDensityField(JTextField xDensityField) {
		this.xDensityField = xDensityField;
	}
	public JTextField getyDensityField() {
		return yDensityField;
	}
	public void setyDensityField(JTextField yDensityField) {
		this.yDensityField = yDensityField;
	}
	public JTextField getxRepeatField() {
		return xRepeatField;
	}
	public void setxRepeatField(JTextField xRepeatField) {
		this.xRepeatField = xRepeatField;
	}
	public JTextField getyRepeatField() {
		return yRepeatField;
	}
	public void setyRepeatField(JTextField yRepeatField) {
		this.yRepeatField = yRepeatField;
	}
}
