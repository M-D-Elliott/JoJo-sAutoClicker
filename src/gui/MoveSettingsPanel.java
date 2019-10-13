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

public class MoveSettingsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	establishing panel fields and labels
	private JTextField xDensityField;
	private JLabel xDensityLabel;
	private JTextField yDensityField;
	private JLabel yDensityLabel;
	private JTextField xRepeatField;
	private JLabel xRepeatLabel;
	private JTextField yRepeatField;
	private JLabel yRepeatLabel;
	private JCheckBox flowCheck;
	private JLabel flowLabel;
	private JCheckBox exactMoveCheck;
	private JLabel exactMoveLabel;
	
//	private JRadioButton repeatOverAreaRadio;
//	private JLabel repeatOverAreaLabel;
//	private JRadioButton noMoveRadio;
//	private JLabel noMoveLabel;
	
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
	private boolean flowInit = true;
	private boolean exactMoveInit = true;
	public MoveSettingsPanel() {
		super();
		Dimension dim = getPreferredSize();
		dim.height = 100;
		setPreferredSize(dim);
		xRepeatField = new JIntegerFieldMinMax(repeatInit, _repeatMaxLength, _repeatMinValue, _repeatMaxValue);
		xRepeatLabel = new JLabel("X Repeat: ");
		xRepeatLabel.setDisplayedMnemonic(KeyEvent.VK_X);
		xRepeatLabel.setLabelFor(xRepeatField);
		
		xDensityField = new JIntegerFieldMinMax(densityInit, _densityMaxLength, _densityMinValue, _densityMaxValue);
		xDensityLabel = new JLabel("X Density (px): ");
		xDensityLabel.setLabelFor(xDensityField);
		
		yRepeatField = new JIntegerFieldMinMax(repeatInit, _repeatMaxLength, _repeatMinValue, _repeatMaxValue);
		yRepeatLabel = new JLabel("Y Repeat: ");
		yRepeatLabel.setDisplayedMnemonic(KeyEvent.VK_Y);
		yRepeatLabel.setLabelFor(yRepeatField);
		
		yDensityField = new JIntegerFieldMinMax(densityInit, _densityMaxLength, _densityMinValue, _densityMaxValue);
		yDensityLabel = new JLabel("Y density (px): ");
		yDensityLabel.setLabelFor(yDensityField);
		
		flowCheck = new JCheckBox();
		flowCheck.setSelected(flowInit);
		flowLabel = new JLabel("Flowing mouse: ");
		flowLabel.setLabelFor(flowCheck);
		
		exactMoveCheck = new JCheckBox();
		exactMoveCheck.setSelected(exactMoveInit);
		exactMoveLabel = new JLabel("Exact move: ");
		exactMoveLabel.setLabelFor(exactMoveCheck);
//		
//		repeatOverAreaRadio = new JRadioButton();
//		repeatOverAreaLabel = new JLabel();
//		noMoveRadio = new JRadioButton();
//		noMoveLabel = new JLabel();
		
		exactMoveCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean enabled = exactMoveCheck.isSelected();
				flowCheck.setEnabled(enabled);
				flowLabel.setEnabled(enabled);
			}
			
		});
		
		TitledBorder innerBorder = BorderFactory.createTitledBorder("Move Settings");
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
		
		// ////////// First row ///////////////////////////////////
		gc.gridx = 0;
		gc.gridy = 0;

		add(xRepeatLabel, gc);

		gc.gridx++;
		add(xRepeatField, gc);
		
		gc.gridx++;
		add(xDensityLabel, gc);
		
		gc.gridx++;
		add(xDensityField, gc);

		// //////////second row ///////////////////////////////////
		gc.gridy++;
		
		
		gc.gridx = 0;
		add(yRepeatLabel, gc);

		gc.gridx++;
		add(yRepeatField, gc);
		
		gc.gridx++;
		add(yDensityLabel, gc);

		gc.gridx++;
		add(yDensityField, gc);
		
		// //////////third row ///////////////////////////////////
		
		gc.gridy++;
		
		gc.gridx = 0;
		add(exactMoveLabel, gc);

		gc.gridx++;
		add(exactMoveCheck, gc);
		
		gc.gridx++;
		add(flowLabel, gc);

		gc.gridx++;
		add(flowCheck, gc);
	}
	
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
	public JCheckBox getFlowCheck() {
		return flowCheck;
	}
	public void setFlowCheck(JCheckBox flowCheck) {
		this.flowCheck = flowCheck;
	}

	public JCheckBox getExactMoveCheck() {
		return exactMoveCheck;
	}

	public void setExactMoveCheck(JCheckBox exactMoveCheck) {
		this.exactMoveCheck = exactMoveCheck;
	}
}
