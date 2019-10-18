package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import app.BrowserOpener;
import extensions.JGradientButton;

public class ControlsPanel extends JPanel implements NativeKeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JGradientButton startButton;
	private JGradientButton endButton;
	private ControlsListener controlsListener;
	
	private int controlKey = NativeKeyEvent.VC_CONTROL;
	private int startKey = NativeKeyEvent.VC_F2;
	private int endKey = NativeKeyEvent.VC_F3;
	private int toggleMoveTypeKey = NativeKeyEvent.VC_A;
	private int switchImageKey = NativeKeyEvent.VC_F;
	
	private boolean controlKeyActive = false;
	private boolean startKeyActive = false;
	private boolean endKeyActive = false;
	private boolean toggleMoveTypeKeyActive = false;
	private boolean switchImageKeyActive = false;
	
	private JLabel useControlToolTip;
	
//	private variables to set a web link to update the app.
	private JLabel currentVersionWebLinkLabel = new JLabel();
	private String currentVersionGitURL = "https://github.com/M-D-Elliott/JoJo-sAutoClicker/blob/master/jojoAutoClicker.exe";
	private String currentVersionMessage = "Get the current version!";
	
	public ControlsPanel() {
		Dimension dim = getPreferredSize();
		dim.height = 80;
		setPreferredSize(dim);
		
		startButton = new JGradientButton("Start (CTRL + F2)");
		endButton = new JGradientButton("End (CTRL + F3)");
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (controlsListener != null) {
					controlsListener.startEventOccurred(1500);
				}
			}
		});
		
		endButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (controlsListener != null) {
					controlsListener.endEventOccurred();
				}
			}
		});
		
		startButton.setBackground(Color.GREEN);
		endButton.setBackground(Color.RED);
		
		TitledBorder innerBorder = BorderFactory.createTitledBorder("Controls");
		innerBorder.setTitleColor(Color.BLUE);
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);

		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
//		adds a little tooltip to advertise the use of control + a
		useControlToolTip = new JLabel("CTRL + A toggles move type");
		
//		creates a web link to my github repo for this project
		BrowserOpener.goWebsite(currentVersionWebLinkLabel, currentVersionGitURL, currentVersionMessage);
		
		layoutComponents();
	}
	
	public void layoutComponents() {

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = gc.weighty = 1.0;

		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		
		// ////////// First row ///////////////////////////////////
		gc.gridx = 0;
		gc.gridy = 0;
		add(startButton, gc);
		
		gc.gridx++;
		add(endButton, gc);
		
		// //////////row ///////////////////////////////////
		gc.gridy++;
		
		gc.gridx = 0;
		add(useControlToolTip, gc);
		
		gc.gridx++;
		add(currentVersionWebLinkLabel, gc);
	}

	public void setControlsListener(ControlsListener controlsListener) {
		this.controlsListener = controlsListener;
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(e.getKeyCode() == NativeKeyEvent.VC_ESCAPE && !endKeyActive) {
			controlsListener.endEventOccurred();
			endKeyActive = true;
		}
		else if(!controlKeyActive) {
			if(e.getKeyCode() == controlKey) {
				controlsListener.controlPressEventOccurred();
				controlKeyActive = true;
			}

		}
		else if (controlKeyActive) {
			if (e.getKeyCode() == startKey && !startKeyActive) {
				controlsListener.startEventOccurred(200);
				startKeyActive = true;
			} else if (e.getKeyCode() == endKey && !endKeyActive) {
				controlsListener.endEventOccurred();
				endKeyActive = true;
			} else if (e.getKeyCode() == switchImageKey && !switchImageKeyActive) {
				controlsListener.switchImageEventOccured();
				switchImageKeyActive = true;
			} else if (e.getKeyCode() == toggleMoveTypeKey && !toggleMoveTypeKeyActive) {
				controlsListener.moveTypeToggleEventOccured();
				toggleMoveTypeKeyActive = true;
			}
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
 		if(e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
			endKeyActive = false;
		}
 		else if(e.getKeyCode() == controlKey) {
			controlsListener.controlReleaseEventOccurred();
			controlKeyActive = false;
		}
		else if(e.getKeyCode() == startKey) {
			startKeyActive = false;
		}
		else if(e.getKeyCode() == endKey) {
			endKeyActive = false;
		}
		else if(e.getKeyCode() == switchImageKey) {
			switchImageKeyActive = false;
		}
		else if(e.getKeyCode() == toggleMoveTypeKey) {
			toggleMoveTypeKeyActive = false;
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
