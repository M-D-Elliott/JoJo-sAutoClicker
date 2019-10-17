package gui;

import java.util.EventListener;

public interface ControlsListener extends EventListener {
	public void startEventOccurred(int sleep);
	public void endEventOccurred();
	public void moveTypeToggleEventOccured();
	public void switchImageEventOccured();
	public void controlPressEventOccurred();
	public void controlReleaseEventOccurred();
}
