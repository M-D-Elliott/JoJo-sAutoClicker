package gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Utils.ComponentUtils;
import app.MoveType;
import gui.moveTypes.NonePanel;
import gui.moveTypes.RepeatOverAreaPanel;

public class MoveTypeSettingsPanel extends JTabbedPane {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	NonePanel nonePanel;
	RepeatOverAreaPanel repeatOverAreaPanel;
	NonePanel specifyCoordinatesPanel;
	Dimension panelSize;
	
	private JPanel previousSelectedPanel;
	
	public MoveTypeSettingsPanel() {
		super();
		Dimension dim = getPreferredSize();
		dim.height = 100;
		dim.width = 470;
		setPreferredSize(dim);
		specifyCoordinatesPanel = new NonePanel("Not yet implemented. Sorry!");
		nonePanel = new NonePanel();
		nonePanel.setPreferredSize(dim);
		this.addTab("None", nonePanel);
		
		repeatOverAreaPanel = new RepeatOverAreaPanel();
		repeatOverAreaPanel.setPreferredSize(dim);
		this.addTab("Repeat Over Area", repeatOverAreaPanel);
		
		specifyCoordinatesPanel.setPreferredSize(dim);
		this.addTab("Specify Coordinates", specifyCoordinatesPanel);
		
		previousSelectedPanel = repeatOverAreaPanel;
	
	}

	public RepeatOverAreaPanel getRepeatOverAreaPanel() {
		return repeatOverAreaPanel;
	}
	
	public JPanel getSelectedJPanel() {
		return (JPanel)getSelectedComponent();
	}
	
	public void toggleMoveTypeTabWithNone() {
		JPanel currentSelection = getSelectedJPanel();
		if (currentSelection == nonePanel) {
			if(ComponentUtils.getComponentIndex(nonePanel) != ComponentUtils.getComponentIndex(previousSelectedPanel)) {
				setSelectedComponent(previousSelectedPanel);
			}
		} else {
			previousSelectedPanel = currentSelection;
			setSelectedComponent(nonePanel);
		}
	}
	
	public MoveType getMoveType() {
		JPanel currentSelection = getSelectedJPanel();
		if(currentSelection == repeatOverAreaPanel) {
			return MoveType.REPEATOVERAREA;
		} else if(currentSelection == specifyCoordinatesPanel) {
			return MoveType.NONE;
		}
		return MoveType.NONE;
	}
	
	public void setMoveType(MoveType moveType) {
		if(moveType == MoveType.REPEATOVERAREA) {
			setSelectedComponent(repeatOverAreaPanel);
		} else if(moveType == MoveType.SPECIFYCOORDINATES) {
			setSelectedComponent(specifyCoordinatesPanel);
		} else if(moveType == MoveType.NONE) {
			setSelectedComponent(nonePanel);
		}
	}
}
