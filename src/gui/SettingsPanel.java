package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import app.MoveType;

public class SettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClickSettingsPanel clickSettingsPanel;
	private MoveSettingsPanel moveSettingsPanel;

	public SettingsPanel() {
		Dimension dim = getPreferredSize();
		dim.height = 320;
		setPreferredSize(dim);
		
		clickSettingsPanel = new ClickSettingsPanel();
		moveSettingsPanel = new MoveSettingsPanel();

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
		add(clickSettingsPanel, gc);
		
		// //////////Second row ///////////////////////////////////
		gc.gridy++;
		
		add(moveSettingsPanel, gc);
	}

	public SettingsEvent getEvent() {
		int delay = Integer.parseInt(clickSettingsPanel.getDelayField().getText());
		int clicks = Integer.parseInt(clickSettingsPanel.getClicksField().getText());
		boolean isInfiniteClicks = clickSettingsPanel.getInfiniteClicksCheck().isSelected();
		int yRepeat = Integer.parseInt(moveSettingsPanel.getyRepeatField().getText());
		int xRepeat = Integer.parseInt(moveSettingsPanel.getxRepeatField().getText());
		int xDensity = Integer.parseInt(moveSettingsPanel.getxDensityField().getText());
		int yDensity = Integer.parseInt(moveSettingsPanel.getyDensityField().getText());
		MoveType moveType = moveSettingsPanel.getMoveType();
		boolean isFlow = moveSettingsPanel.getFlowCheck().isSelected();
		boolean isExactMove = moveSettingsPanel.getExactMoveCheck().isSelected();
		return new SettingsEvent(this, delay, clicks, isInfiniteClicks, xRepeat, yRepeat, xDensity, yDensity, moveType, isFlow, isExactMove);
	}

	public MoveSettingsPanel getMoveSettingsPanel() {
		return moveSettingsPanel;
	}

	public void setMoveSettingsPanel(MoveSettingsPanel moveSettingsPanel) {
		this.moveSettingsPanel = moveSettingsPanel;
	}
	
	
}