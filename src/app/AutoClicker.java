package app;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;

import callbacks.AcceptsTwoIntCallback;
import callbacks.BooleanCallback;
import callbacks.Callback;
import callbacks.ReturnsIntAcceptsObjectCallback;
import interfaces.MoveType;

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
//	a boolean to determine if mouse moving offscreen should be prevented/protected
	private boolean preventOffScreen = true;
	
	
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

//		stores a callback for the retrace function. if the object will move by user input, 
//		flow can be used so that the object retraces to a relative initial position.
//		Else retrace goes to initial mouse position, effectively locking the user mouse position.
		ReturnsIntAcceptsObjectCallback retraceCallback = super.getRetraceCallback();
		
//		stores a callback for the base move, be it one-try or exact.
		AcceptsTwoIntCallback moveMouseCallback;
		
//		stores a callback for the movetype, be it none, repeat over area, or specify coordinates;
		Callback moveMouseTypeCallback = () -> emptyCallback();
		if(moveType == MoveType.NONE || moveType == MoveType.SPECIFYCOORDINATES) {
			moveMouseCallback = (int x, int y) -> emptyCallback();
		} else {
			moveMouseCallback = (_exactMove) 
					? (int x, int y) -> moveMouseExact(x, y)
					: (int x, int y) -> moveMouse(x, y);
					
			//	store the initial cursor position
			Point mouseCoords = tryGetMousePosition();
			x.init = x.curr = mouseCoords.x;
			y.init = y.curr = mouseCoords.y;
			x.density = x.density * x.sign;
			y.density = y.density * y.sign;
			x.repeatCurrent = 0;
			y.repeatCurrent = 0;
			
//			if the user wishes to prevent off screen movement
			if(preventOffScreen) {
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				x.min = y.min = 0;
				x.max = screenSize.width;
				y.max = screenSize.height;
			}
			
//			check the move type and set callbacks accordingly.
			if(moveType == MoveType.REPEATOVERAREA) {
				moveMouseTypeCallback = 
						(xyInvert) 
						? () -> repeatMouseOverAreaInverse(retraceCallback, moveMouseCallback)
						: () -> repeatMouseOverArea(retraceCallback, moveMouseCallback);
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
	
	private boolean moveMouseExact(int xDestination, int yDestination) {
		return moveMouseExact(xDestination, yDestination, _tries);
	}
	
	private boolean moveMouseExact(int xDestination, int yDestination, byte tries) {
		Point mouseCoords = tryGetMousePosition();
		while((tries > 0)) {
			if((mouseCoords.x == xDestination && mouseCoords.y == yDestination)) {
				return true;
			}
			moveMouse(xDestination, yDestination);
			mouseCoords = tryGetMousePosition();
			tries--;
		}
		return false;
	}
	
	private void repeatMouseOverArea(ReturnsIntAcceptsObjectCallback retraceCallback, AcceptsTwoIntCallback moveMouseCallback) {
		Point mouseCoords = tryGetMousePosition();
		x.curr = mouseCoords.x;
		y.curr = mouseCoords.y;
		super.repeatPositionOverArea(x, y, retraceCallback);
		moveMouseCallback.callback(x.curr, y.curr);
	}
	
	private void repeatMouseOverAreaInverse(ReturnsIntAcceptsObjectCallback retraceCallback, AcceptsTwoIntCallback moveMouseCallback) {
		Point mouseCoords = tryGetMousePosition();
		x.curr = mouseCoords.x;
		y.curr = mouseCoords.y;
		super.repeatPositionOverArea(y, x, retraceCallback);
		moveMouseCallback.callback(x.curr, y.curr);
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
		
		x.density = e.getxDensity();
		x.repeat = (byte)e.getxRepeat();
		x.sign = (byte)e.getxSign();
		
		y.density = e.getyDensity();
		y.repeat = (byte)e.getyRepeat();
		y.sign = (byte)e.getySign();
		
		xyInvert = e.isxyInvert();
		moveType = e.getMoveType();
		_exactMove =  e.isExactMove();
		_flow = e.isFlow();
	}
}
