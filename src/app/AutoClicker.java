package app;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

import callbacks.AcceptsTwoIntCallback;
import callbacks.BooleanCallback;
import callbacks.Callback;
import callbacks.ReturnsIntAcceptsObjectCallback;
import gui.SettingsEvent;

public class AutoClicker extends PositionsObject {
	private Robot robot;
	
//	delay between clicks in milliseconds.
	private int delay = 100;
//	total number of clicks. This result of this number will be doubled if mouse action is 2, or double click.
	private int totalClicks = 1;
//	stores the number of clicks per cycle. One for single click, two for double
	private int clickType = 1;
	
//	how long to sleep before clicking starts, 300 ms initially.
	private int sleep = 300;
//	which button on the mouse to rapidly press.
	private int button = InputEvent.BUTTON1_MASK;
	
//	stores the move type, none if mouse will not move
	private MoveType moveType = MoveType.NONE;
	
//	a variable to allow user to interrupt loop when true.
	private boolean _continue = false;

//	a boolean to hold whether infinite clicks mode is active.
	private boolean _infiniteClicks = true;
//	if this is on, the mouse move function will loop until its position matches the prescribed position exactly.
	private boolean _exactMove = true;
//	determines the number of additional threads to run an auto clicker on.
	private int addClickThreads = 0;
	
	
//	constructor
	public AutoClicker() {
		try {
			robot = new Robot();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start(){
//		start with a short delay before clicking, based on the internal sleep variable;
		sleep();
		
//		decide whether to use double or single clicks
		Callback clickCallback = (clickType == 2) ? () -> doubleClick() : () -> singleClick();
		
//		decide if clicks are infinite or finite
		BooleanCallback durationCallback = (_infiniteClicks) ? () -> infiniteClicks() : () -> finiteClicks();
		
		_exactMove = (_exactMove && moveType != MoveType.NONE);
		_flow = (_flow && _exactMove);

		AcceptsTwoIntCallback moveMouseCallback;
		Callback moveMouseTypeCallback = () -> emptyCallback();
		// get some positional data about the mouse if it needs to move. 
		ReturnsIntAcceptsObjectCallback retraceCallback = super.getRetraceCallback();
		if(moveType == MoveType.NONE) {
			moveMouseCallback = (int x, int y) -> emptyCallback();
		} else {
			moveMouseCallback = (_exactMove) 
					? (int x, int y) -> moveMouseExact(x, y)
					: (int x, int y) -> moveMouse(x, y); 
			if(moveType == MoveType.REPEATOVERAREA) {
				moveMouseTypeCallback = () -> repeatMouseOverArea(retraceCallback, moveMouseCallback);
			//	store the initial cursor position
				Point mouseCoords = tryGetMousePosition();
				x.init = x.curr = mouseCoords.x;
				y.init = y.curr = mouseCoords.y;
				x.repeatCurrent = 0;
				y.repeatCurrent = 0;
			} 
		}

//		set the continue to true so the loop will commence.
		_continue = true;
		
//		if the addThreads value is greater then zero, add x threads.
		startAdditionalAutoClickThreads(clickCallback, durationCallback);
		
//		calls the AutoClick function with previously determined CallBacks.
		autoClick(clickCallback, durationCallback, moveMouseTypeCallback);
		
//		if repeat was used return to initial position.
		moveMouseCallback.callback(retraceCallback.callback(x), retraceCallback.callback(y));
	}
	
	// execute auto click using various callbacks.
	public void autoClick(Callback clickCallback, BooleanCallback durationCallback, Callback mouseMoveTypeCallback) {
		while(durationCallback.callback()) {
			clickCallback.callback();
			mouseMoveTypeCallback.callback();
		}
	}
	
//	private void repeatMouseOverArea(ReturnsIntAcceptsObjectCallback retraceCallback, AcceptsTwoIntCallback moveMouseCallback) {
//		Point mouseCoords = tryGetMousePosition();
//		x.curr = mouseCoords.x;
//		y.curr = mouseCoords.y;
//		x.init = retraceCallback.callback(x);
//		y.init = retraceCallback.callback(y);
//		super.repeatPositionOverArea();
//		moveMouseCallback.callback(x.curr, y.curr);
//	}
//	
	private void repeatMouseOverArea(ReturnsIntAcceptsObjectCallback retraceCallback, AcceptsTwoIntCallback moveMouseCallback) {
		Point mouseCoords = tryGetMousePosition();
		x.curr = mouseCoords.x;
		y.curr = mouseCoords.y;
		super.repeatPositionOverArea(retraceCallback);
		moveMouseCallback.callback(x.curr, y.curr);
	}
	
//	*-*-*-*-*-*-*-*-*-Callback types-*-*-*-*-*-*-*-*-*-*
//	--Types of functions used to construct larger functions, such as autoclick--
//	---------Click Type Callbacks-----------
//	perform a single click.
	private void singleClick(){
		try {
			robot.mousePress(button);
//			robot.delay(1);
			robot.mouseRelease(button);
			robot.delay(delay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	perform a double click.
	private void doubleClick(){
		try {
			robot.mousePress(button);
//			robot.delay(1);
			robot.mouseRelease(button);
			robot.delay(2);
			robot.mousePress(button);
//			robot.delay(1);
			robot.mouseRelease(button);
			robot.delay(delay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	-------------Duration Type Callbacks------------
	private boolean finiteClicks() {
		totalClicks--;
		return totalClicks > 0 && _continue;
	}
	
	private boolean infiniteClicks() {
		return _continue;
	}
	
//	----------Move Type Callbacks-------------
	private void moveMouse(int xDestination, int yDestination) {
		try {
			robot.mouseMove(xDestination, yDestination);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void moveMouseExact(int xDestination, int yDestination) {
		Point mouseCoords = tryGetMousePosition();
		while(mouseCoords.x != xDestination || mouseCoords.y != yDestination) {
			moveMouse(xDestination, yDestination);
			mouseCoords = tryGetMousePosition();
		}
	}
	
//	-------------Generic Callbacks------------
	// provides a null-type for callbacks.
	private void emptyCallback() {}
	
//	*-*-*-*-*-*-*-*-*-Callback assigners-*-*-*-*-*-*-*-*-*-*
	
	private void startAdditionalAutoClickThreads(Callback clickCallback, BooleanCallback durationCallback) {
		if(addClickThreads > 0) {
			Callback moveCallback = (() ->emptyCallback());
			Callback staticAutoClickCallback = (() -> autoClick(clickCallback, durationCallback, moveCallback));
//			add x threads.
			startAdditionalThreads(staticAutoClickCallback, addClickThreads);
		}
	}
	
//	*-*-*-*-*-*-*-*-*-Misc functions-*-*-*-*-*-*-*-*-*-*
	
	private void startAdditionalThreads(Callback callback, int addThreads) {
		for (int i = 0;i < addThreads; i++) {
		    Thread thread = new Thread() {
			    public void run() {
			    	callback.callback();
			    }
		    };
		    thread.start();
		}
	}
	
	private void sleep() {
		try {
			Thread.sleep(sleep);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	*-*-*-*-*-*-*-*-*-Getters and Setters-*-*-*-*-*-*-*-*-*-*
	
	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getClicks() {
		return totalClicks;
	}

	public void setClicks(int clicks) {
		this.totalClicks = clicks;
	}

	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	public boolean is_continue() {
		return _continue;
	}

	public void set_continue(boolean _continue) {
		this._continue = _continue;
	}

	public boolean is_flow() {
		return _flow;
	}

	public void set_flow(boolean _flow) {
		this._flow = _flow;
	}

	public boolean is_infiniteClicks() {
		return _infiniteClicks;
	}

	public void set_infiniteClicks(boolean _infiniteClicks) {
		this._infiniteClicks = _infiniteClicks;
	}

	public void set_settings(SettingsEvent e) {
		delay = e.getDelay();
		totalClicks = e.getClicks();
		_infiniteClicks = e.isInfiniteClicks();
		x.repeat = e.getxRepeat();
		x.density = e.getxDensity();
		y.repeat = e.getyRepeat();
		y.density = e.getyDensity();
		moveType = e.getMoveType();
		_exactMove =  e.isExactMove();
		_flow = e.isFlow();
	}
}
