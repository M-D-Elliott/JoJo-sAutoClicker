package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import app.JoJoCharacter;
import app.WeightedItem;
import extensions.JGradientButton;

public class ControlsPanel extends JPanel implements NativeKeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JGradientButton startButton;
	private JGradientButton endButton;
	private ControlsListener controlsListener;
	private List<WeightedItem> imagesRepo = new ArrayList<WeightedItem>();
	
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
	
	public void fillCharacterRepo() {
		this.imagesRepo.add(new JoJoCharacter("Johnathan Joestar", "jojo1", 0.2));
		this.imagesRepo.add(new JoJoCharacter("Joseph Joestar", "jojo2", 0.2));
		this.imagesRepo.add(new JoJoCharacter("Jotero Kujo", "jojo3", 0.2));
		this.imagesRepo.add(new JoJoCharacter("Old Joseph", "jojo4", 0.05));
		this.imagesRepo.add(new JoJoCharacter("young Johnathan Joestar", "jojo5", 0.05));
		this.imagesRepo.add(new JoJoCharacter("young Johnathan Joestar", "jojo6", 0.05));
		this.imagesRepo.add(new JoJoCharacter("Dio Brando", "dio", 0.05));
		this.imagesRepo.add(new JoJoCharacter("Erina", "erina", 0.1));
		this.imagesRepo.add(new JoJoCharacter("Baron Zepelli", "baron", 0.15));
		this.imagesRepo.add(new JoJoCharacter("Monkey", "monkey", 0.1));
		this.imagesRepo.add(new JoJoCharacter("Anne", "anne1", 0.075));
		this.imagesRepo.add(new JoJoCharacter("Anne", "anne2", 0.001));
		this.imagesRepo.add(new JoJoCharacter("Anne", "anne3", 0.001));
		this.imagesRepo.add(new JoJoCharacter("Von Stroheim", "vonstroheim1", 0.05));
		this.imagesRepo.add(new JoJoCharacter("Von Stroheim", "vonstroheim2", 0.05));
		this.imagesRepo.add(new JoJoCharacter("Von Stroheim", "vonstroheim3", 0.07));
	}
	
	public JoJoCharacter getWeightedRandomCharacter() {
		return (JoJoCharacter) getWeightedRandomItem(this.imagesRepo);
	}
	
	
	private WeightedItem getWeightedRandomItem(List<WeightedItem> list) {
		// Compute the total weight of all items together
		double totalWeight = 0.0d;
		for (WeightedItem i : list)
		{
		    totalWeight += i.getWeight();
		}
		// Now choose a random item
		int randomIndex = -1;
		double random = Math.random() * totalWeight;
		for (int i = 0; i < list.size(); ++i)
		{
		    random -= list.get(i).getWeight();
		    if (random <= 0.0d)
		    {
		        randomIndex = i;
		        break;
		    }
		}
		return list.get(randomIndex);
	}
}
