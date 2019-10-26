package gui.moveTypes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
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
	private JTextField xRepeatField;
	private JLabel xRepeatLabel;
	private JCheckBox xSignCheck;
//	private JTextField xSignField;
//	private JLabel xSignLabel;
	private JTextField yDensityField;
	private JLabel yDensityLabel;
	private JTextField yRepeatField;
	private JLabel yRepeatLabel;
	private JCheckBox ySignCheck;
//	private JTextField ySignField;
//	private JLabel ySignLabel;
	
	private JCheckBox xyInvertCheck;
//	private JLabel xyInvertLabel;
	
//	Limits to above fields.
	private static int _repeatMinValue = 0;
	private static int _repeatMaxValue = 99;
	private static int _repeatMaxLength = IntUtils.length(_repeatMaxValue);
	private static int _densityMinValue = 5;
	private static int _densityMaxValue = 9999;
	private static int _densityMaxLength = IntUtils.length(_densityMaxValue);
//	private static String signInit = "1";
//	private static int _signMaxLength = 1;
//	private static int _signMaxValue = 1;
//	private static int _signMinValue = 0;
	private boolean invertInit = false;
	
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
		
		xSignCheck = new JCheckBox("Neg. X ", invertInit);
//		xSignField = new JIntegerFieldMinMax(signInit, _signMaxLength, _signMinValue, _signMaxValue);
//		xSignLabel = new JLabel("Neg. X ");
//		xSignLabel.setLabelFor(xSignField);
		
		yRepeatField = new JIntegerFieldMinMax(repeatInit, _repeatMaxLength, _repeatMinValue, _repeatMaxValue);
		yRepeatLabel = new JLabel("Y Repeat: ");
		yRepeatLabel.setLabelFor(yRepeatField);
		
		yDensityField = new JIntegerFieldMinMax(densityInit, _densityMaxLength, _densityMinValue, _densityMaxValue);
		yDensityLabel = new JLabel("Y density (px): ");
		yDensityLabel.setLabelFor(yDensityField);
		
		ySignCheck = new JCheckBox("Neg. Y ", invertInit);
//		ySignField = new JIntegerFieldMinMax(signInit, _signMaxLength, _signMinValue, _signMaxValue);
//		ySignLabel = new JLabel("Neg. Y ");
//		ySignLabel.setLabelFor(ySignField);
		
		xyInvertCheck = new JCheckBox("invert x/y ", invertInit);
//		xyInvertLabel = new JLabel();
//		xyInvertLabel.setLabelFor(xyInvertField);
		
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
		
		gc.gridx++;
		add(xSignCheck, gc);

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
		
		gc.gridx++;
		add(ySignCheck, gc);
		
		// //////////Row ///////////////////////////////////
		gc.gridy++;

		gc.gridx = 0;
		add(xyInvertCheck, gc);
	}
//	getters and setters for private fields
	public JTextField getxDensityField() {
		return xDensityField;
	}
	public JTextField getxRepeatField() {
		return xRepeatField;
	}
	public JTextField getyDensityField() {
		return yDensityField;
	}
	public JTextField getyRepeatField() {
		return yRepeatField;
	}
	public JCheckBox getxyInvertCheck() {
		return xyInvertCheck;
	}
	public void setxDensityField(JTextField xDensityField) {
		this.xDensityField = xDensityField;
	}
	public void setxRepeatField(JTextField xRepeatField) {
		this.xRepeatField = xRepeatField;
	}
	public void setyDensityField(JTextField yDensityField) {
		this.yDensityField = yDensityField;
	}
	public void setyRepeatField(JTextField yRepeatField) {
		this.yRepeatField = yRepeatField;
	}
	public void setxyInvertCheck(JCheckBox xyInvertCheck) {
		this.xyInvertCheck = xyInvertCheck;
	}
	public JCheckBox getxSignCheck() {
		return xSignCheck;
	}
	public JCheckBox getySignCheck() {
		return ySignCheck;
	}
	public void setxSignCheck(JCheckBox xSignCheck) {
		this.xSignCheck = xSignCheck;
	}
	public void setySignCheck(JCheckBox ySignCheck) {
		this.ySignCheck = ySignCheck;
	}

	
	
}
