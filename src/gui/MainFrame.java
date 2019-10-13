package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import app.AutoClicker;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SettingsPanel settingsPanel;
	private ControlsPanel controlsPanel;
	private AutoClicker autoClicker = new AutoClicker();
	
	public boolean isRunning = false;
	
	public MainFrame() {
		//Labels the application.
		super("Jojo's AutoClicker");
		//establishes border layout.
		setLayout(new BorderLayout());

		//establishes window size and disables window resizing.
		Dimension dim = new Dimension(490, 270);
		setMinimumSize(dim);
		setSize(dim);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	    this.setLocationRelativeTo(null);
		
		//establishes components.
		settingsPanel = new SettingsPanel();
		controlsPanel = new ControlsPanel();
		add(settingsPanel, BorderLayout.CENTER);
		add(controlsPanel, BorderLayout.SOUTH);
	    
	    // fills the character repo for the getJoJoCharacter() function.
		controlsPanel.fillCharacterRepo();
	    
		//gets then sets an icon for this application.
		this.getIcon();

		controlsPanel.setControlsListener(new ControlsListener() {

			@Override
			public void startEventOccurred(int sleep) {
//				System.out.println("start");
				if(!isRunning) {
					isRunning = true;
					autoClicker.setSleep(sleep);
					autoClicker.set_settings(settingsPanel.getEvent());
					autoClicker.start();
					isRunning = false;
				}
			}

			@Override
			public void endEventOccurred() {
//				System.out.println("end");
				autoClicker.set_continue(false);
			}

			@Override
			public void iconSwitchEventOccured() {
//				System.out.println("icon");
				getIcon();
				
			}

			@Override
			public void ctrlEventOccurred() {
//				System.out.println("ctrl");
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
	
	private void getIcon() {
		URL url = getClass().getResource("img/" + controlsPanel.getWeightedRandomCharacter().getImageName() + ".jpg");
		
		ImageIcon img = new ImageIcon(url);
		this.setIconImage(img.getImage());
	}
}
