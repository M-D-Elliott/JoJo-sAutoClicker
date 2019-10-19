package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import app.AutoClicker;
import app.JoJoCharacter;
import app.Test;
import app.WeightedItem;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String appTitle = "JoJo's AutoClicker";
	
//	establishes panels
	private SettingsPanel settingsPanel;
	private ControlsPanel controlsPanel;
	private AutoClicker autoClicker = new AutoClicker();
	
//	establishes list which contains jojocharacter objects for getting a random icon.
	WeightedItem[] jojoList;
	
//	graphical display test class, not used currently
	private Test test;
	
	public boolean isRunning = false;
	
	public MainFrame() {
		//Labels the application.
		super(appTitle);
		//establishes border layout.
		setLayout(new BorderLayout());

		//establishes window size and disables window resizing.
		Dimension dim = new Dimension(490, 340);
		setMinimumSize(dim);
		setSize(dim);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	    this.setLocationRelativeTo(null);
	    pack();
		
		//establishes components.
		settingsPanel = new SettingsPanel();
		controlsPanel = new ControlsPanel();
		add(settingsPanel, BorderLayout.CENTER);
		add(controlsPanel, BorderLayout.SOUTH);
	    
	    // fills the character repo for the getJoJoCharacter() function.
		fillCharacterRepo();
	    
		//gets then sets an icon for this application.
		this.setIconImage(getRandomIcon());
		
//		loads the settings from the settings txt file.
		settingsPanel.loadSettings();
		
		controlsPanel.setControlsListener(new ControlsListener() {

			@Override
			public void startEventOccurred(int sleep) {
				System.out.println("start");
				if(!isRunning) {
					isRunning = true;
					autoClicker.setSleep(sleep);
					autoClicker.set_settings(settingsPanel.getEvent());
					Thread thread = new Thread() {
						public void run() {
							autoClicker.start();
							isRunning = false;
						}
					};
				    thread.start();
				}
			}

			@Override
			public void endEventOccurred() {
				autoClicker.set_continue(false);
			}

			@Override
			public void switchImageEventOccured() {
//				System.out.println("icon");
				setIconImage(getRandomIcon());
				
			}

			@Override
			public void moveTypeToggleEventOccured() {
				endEventOccurred();
				settingsPanel.getMoveTypeSettingsPanel().toggleMoveTypeTabWithNone();
			}

			@Override
			public void controlPressEventOccurred() {
				if(settingsPanel.getEvent().isGraphics()) {
					test = new Test();
				}
			}
			
			@Override
			public void controlReleaseEventOccurred() {
				if(test != null) {
					test.hide();
				}
			}
		});
		
//		saves the settings in the settings panel to a txt file for later use.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	settingsPanel.saveSettings(settingsPanel.getEvent());
                System.exit(0);
            }
        });
		
		//establishes global key listener.
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
		//turns off logger for jnativehook
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		logger.setUseParentHandlers(false);
		
		//sets controlsPanel as the nativekeylistener.
		GlobalScreen.addNativeKeyListener(controlsPanel);
	}
	
	public void fillCharacterRepo() {
		jojoList = new WeightedItem[] {
//			new JoJoCharacter("test", "avdol", 10),
			new JoJoCharacter("Anne", "anne1", 0.075),
			new JoJoCharacter("Anne", "anne2", 0.001),
			new JoJoCharacter("Anne", "anne3", 0.001),
			new JoJoCharacter("Avdol", "avdol", 0.12),
			new JoJoCharacter("Baron Zepelli", "baron", 0.15),
			new JoJoCharacter("Caesar", "caesar", 0.075),
			new JoJoCharacter("Dio Brando", "dio", 0.05),
			new JoJoCharacter("Erina", "erina", 0.1),
			new JoJoCharacter("Johnathan Joestar", "jojo1", 0.2),
			new JoJoCharacter("Joseph Joestar", "jojo2", 0.2),
			new JoJoCharacter("Jotero Kujo", "jojo3", 0.2),
			new JoJoCharacter("Monkey", "monkey", 0.1),
			new JoJoCharacter("Old Joseph", "jojo4", 0.05),
			new JoJoCharacter("Speedwagon", "speedwagon", 0.15),
			new JoJoCharacter("Star Platinum", "avdol", 0.05),
			new JoJoCharacter("Von Stroheim", "vonstroheim1", 0.05),
			new JoJoCharacter("Von Stroheim", "vonstroheim2", 0.05),
			new JoJoCharacter("Von Stroheim", "vonstroheim3", 0.07),
			new JoJoCharacter("young Johnathan Joestar", "jojo5", 0.05),
			new JoJoCharacter("young Johnathan Joestar", "jojo6", 0.05),
		};
	}
	
	public JoJoCharacter getWeightedRandomCharacter() {
		return (JoJoCharacter)getWeightedRandomItem(Arrays.asList(jojoList));
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
	
	private Image getRandomIcon() {
		JoJoCharacter jojoCharacter = getWeightedRandomCharacter();
		URL url = getClass().getResource("/img/" + jojoCharacter + ".jpg");
		ImageIcon img = new ImageIcon(url);
		return img.getImage();
	}
}
