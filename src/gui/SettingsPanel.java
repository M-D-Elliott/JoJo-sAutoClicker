package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JPanel;

import app.MoveType;

public class SettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClickSettingsPanel clickSettingsPanel;
	private MoveSettingsPanel moveSettingsPanel;
	private MoveTypeSettingsPanel moveTypeSettingsPanel;
	
//	private ObjectSaver profileSaver;
	String settingsFileName = "/profiles.txt";

	public SettingsPanel() {
		Dimension dim = getPreferredSize();
		dim.height = 200;
		dim.width = 480;
		setPreferredSize(dim);
		
		clickSettingsPanel = new ClickSettingsPanel();
		moveSettingsPanel = new MoveSettingsPanel();
		
		moveTypeSettingsPanel = new MoveTypeSettingsPanel();

		layoutComponents();
	}
	
	public void layoutComponents() {

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = gc.weighty = 1.0;

		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 8, 0, 8);
		
		// ////////// row ///////////////////////////////////
		gc.gridx = 0;
		gc.gridy = 0;
		add(clickSettingsPanel, gc);
		
		// //////////row ///////////////////////////////////
		gc.gridy++;
		
		add(moveSettingsPanel, gc);
		
		// //////////row ///////////////////////////////////
		gc.gridy++;
		
		add(moveTypeSettingsPanel, gc);
	}

	public SettingsEvent getEvent() {
		int delay = Integer.parseInt(clickSettingsPanel.getDelayField().getText());
		int clicks = Integer.parseInt(clickSettingsPanel.getClicksField().getText());
		boolean isInfiniteClicks = clickSettingsPanel.getInfiniteClicksCheck().isSelected();
		MoveType moveType = moveTypeSettingsPanel.getMoveType();
		int yRepeat = Integer.parseInt(moveTypeSettingsPanel.getRepeatOverAreaPanel().getyRepeatField().getText());
		int xRepeat = Integer.parseInt(moveTypeSettingsPanel.getRepeatOverAreaPanel().getxRepeatField().getText());
		int xDensity = Integer.parseInt(moveTypeSettingsPanel.getRepeatOverAreaPanel().getxDensityField().getText());
		int yDensity = Integer.parseInt(moveTypeSettingsPanel.getRepeatOverAreaPanel().getyDensityField().getText());
		boolean isFlow = moveSettingsPanel.getFlowCheck().isSelected();
		boolean isExactMove = moveSettingsPanel.getExactMoveCheck().isSelected();
		boolean isGraphics = clickSettingsPanel.getGraphicsCheck().isSelected();
		return new SettingsEvent(this, delay, clicks, isInfiniteClicks, xRepeat, yRepeat, xDensity, yDensity, moveType, isExactMove, isFlow, isGraphics);
	}
	
	public void setEvent(SettingsEvent e) {
		clickSettingsPanel.getDelayField().setText(Integer.toString(e.getDelay()));
		clickSettingsPanel.getClicksField().setText(Integer.toString(e.getClicks()));
		clickSettingsPanel.getInfiniteClicksCheck().setSelected(e.isInfiniteClicks());
		clickSettingsPanel.enableClicksField();
		moveTypeSettingsPanel.setMoveType(e.getMoveType());
		moveTypeSettingsPanel.getRepeatOverAreaPanel().getxRepeatField().setText(Integer.toString(e.getxRepeat()));
		moveTypeSettingsPanel.getRepeatOverAreaPanel().getxDensityField().setText(Integer.toString(e.getxDensity()));
		moveTypeSettingsPanel.getRepeatOverAreaPanel().getyRepeatField().setText(Integer.toString(e.getyRepeat()));
		moveTypeSettingsPanel.getRepeatOverAreaPanel().getyDensityField().setText(Integer.toString(e.getyDensity()));
		moveSettingsPanel.getFlowCheck().setSelected(e.isFlow());
		moveSettingsPanel.getExactMoveCheck().setSelected(e.isExactMove());
		moveSettingsPanel.enableFlowCheck();
		clickSettingsPanel.getGraphicsCheck().setSelected(e.isGraphics());
	}

	public void saveSettings(SettingsEvent settings) {
        try
        {
            FileOutputStream fos = new FileOutputStream(getAbsolutePath() + settingsFileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(settings);
            oos.close();
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}

	public void loadSettings() {
        SettingsEvent settings = null;
        try
        {
            FileInputStream fis = new FileInputStream(getAbsolutePath() + settingsFileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            settings = (SettingsEvent)ois.readObject();
            ois.close();
            setEvent(settings);
        }
        catch (Exception e)
        {
//            e.printStackTrace();
        	System.out.println("File not found");
        }
	}
	
	public String getAbsolutePath() {
		Path currentRelativePath = Paths.get("");
		return currentRelativePath.toAbsolutePath().toString();
	}
	
//	getters and setters
	public MoveSettingsPanel getMoveSettingsPanel() {
		return moveSettingsPanel;
	}

	public void setMoveSettingsPanel(MoveSettingsPanel moveSettingsPanel) {
		this.moveSettingsPanel = moveSettingsPanel;
	}

	public MoveTypeSettingsPanel getMoveTypeSettingsPanel() {
		return moveTypeSettingsPanel;
	}

	public void setMoveTypeSettingsPanel(MoveTypeSettingsPanel moveTypeSettingsPanel) {
		this.moveTypeSettingsPanel = moveTypeSettingsPanel;
	}
	
}