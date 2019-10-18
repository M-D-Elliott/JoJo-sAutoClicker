package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class MoveSettingsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	establishing panel fields and labels
	private JCheckBox flowCheck;
	private JLabel flowLabel;
	private JCheckBox exactMoveCheck;
	private JLabel exactMoveLabel;
	
	private boolean flowInit = true;
	private boolean exactMoveInit = true;
	public MoveSettingsPanel() {
		super();
		Dimension dim = getPreferredSize();
		dim.height = 120;
		dim.width = 470;
		setPreferredSize(dim);
		
		flowCheck = new JCheckBox();
		flowCheck.setSelected(flowInit);
		flowLabel = new JLabel("Flowing mouse: ");
		flowLabel.setLabelFor(flowCheck);
		
		exactMoveCheck = new JCheckBox();
		exactMoveCheck.setSelected(exactMoveInit);
		exactMoveLabel = new JLabel("Exact move: ");
		exactMoveLabel.setLabelFor(exactMoveCheck);
		
		exactMoveCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				enableFlowCheck();
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
		
		gc.gridy = 0;
		
		// //////////Row ///////////////////////////////////
		
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
	
	public void enableFlowCheck() {
		boolean enabled = exactMoveCheck.isSelected();
		flowCheck.setEnabled(enabled);
		flowLabel.setEnabled(enabled);
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
