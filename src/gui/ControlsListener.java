package gui;

import java.util.EventListener;

public interface ControlsListener extends EventListener {
	public void startEventOccurred(int sleep);
	public void endEventOccurred();
	public void iconSwitchEventOccured();
	public void ctrlEventOccurred();
	public void moveTypeSwitchEventOccured();
}
