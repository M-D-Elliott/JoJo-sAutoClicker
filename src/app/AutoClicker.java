package app;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

import callbacks.BooleanCallback;
import callbacks.Callback;
import callbacks.acceptsTwoIntCallback;
import gui.SettingsEvent;

public class AutoClicker {
	private Robot robot;
	
//	delay between clicks in milliseconds.
	private int delay = 100;
//	total number of clicks. This result of this number will be doubled if mouse action is 2, or double click.
	private int totalClicks = 1;
//	stores the number of clicks per cycle. One for single click, two for double
	private int clickType = 1;
//	repeat over distance in pixels on x-axis
	private int xRepeat = 0;
	private int xDensity = 50;
//	stores the repeat position for x.
	private int _xRepeatCurrent = 0;
//	repeat over distance in pixels on y-axis
	private int yRepeat = 0;
	private int yDensity = 50;
//	stores the repeat position for y.
	private int _yRepeatCurrent = 0;
	
	private coord x = new coord();
	private coord y = new coord();
	
//	how long to sleep before clicking starts, 300 ms initially.
	private int sleep = 300;
//	which button on the mouse to rapidly press.
	private int button = InputEvent.BUTTON1_MASK;
	
//	stores the move type, none if mouse will not move
	private MoveType moveType = MoveType.NONE;
	
//	a variable to allow user to interrupt loop when true.
	private boolean _continue = true;
//	a boolean to later calculate if x or y repeat is used.
	private boolean _flow = true;
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
		_exactMove = (_exactMove && moveType != MoveType.NONE);
		_flow = (_flow && _exactMove);
		// get some positional data about the mouse if it needs to move. 
		if(moveType == MoveType.REPEATOVERAREA) {
//			System.out.println("repeat");
//			store the initial cursor position
			Point mouseCoords = MouseInfo.getPointerInfo().getLocation();
			x.init = (int)mouseCoords.getX();
			y.init = (int)mouseCoords.getY();
//			determines the retrace value for flow mode.
			x.retrace = xRepeat * xDensity;
			y.retrace = yRepeat * yDensity;
			_xRepeatCurrent = 0;
			_yRepeatCurrent = 0;
		}
		
		// store the CallBacks that will be used in the AutoClicker and to move the mouse if needed.  will get emptyCallBack() if unneeded
		Callback clickCallback = getClickCallback();
		BooleanCallback durationCallback = getDurationCallback();
//		if the addThreads value is greater then zero, add x threads.
		startAdditionalAutoClickThreads(clickCallback, durationCallback);
//		collect a callback for the mouse move function between each click, empty callback if no move is used.
		Callback mouseMoveCallback = getMouseMoveTypeCallback();
		Callback mouseFinalPositionCallback = getMouseMoveCallbackWithArgs(x.init, y.init);
//		set the continue to true so the loop will commence.
		_continue = true;
		
//		calls the AutoClick function with previously determined CallBacks.
		autoClick(clickCallback, durationCallback, mouseMoveCallback);
//		if repeat was used return to initial position.
		mouseFinalPositionCallback.callback();
	}
	
	// execute auto click using various callbacks.
	public void autoClick(Callback clickCallback, BooleanCallback durationCallback, Callback mouseMoveCallback) {
		clickCallback.callback();
		if (durationCallback.callback()) {
			mouseMoveCallback.callback();
			autoClick(clickCallback, durationCallback, mouseMoveCallback);
		} else {
			end("Terminated");
		}
	}
//	*-*-*-*-*-*-*-*-*-Callback types-*-*-*-*-*-*-*-*-*-*
//	--Types of functions used to construct larger functions, such as autoclick--
//	---------Click Types -----------
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
	
//	----------Move types-------------
	private void moveMouse(int xDestination, int yDestination) {
		try {
			robot.mouseMove(xDestination, yDestination);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void moveMouseExact(int xDestination, int yDestination) {
		try {
			robot.mouseMove(xDestination, yDestination);
			Point mouseCoords = MouseInfo.getPointerInfo().getLocation();
			if(mouseCoords.x != xDestination || mouseCoords.y != yDestination) {
				moveMouseExact(xDestination, yDestination);
			}
		} catch (Exception e) {
			e.printStackTrace();
			moveMouseExact(xDestination, yDestination);
		}
	}
	
//	move the mouse over an applied area.
	private void moveMouseOverArea(acceptsTwoIntCallback moveMouseCallback) {
		Point mouseCoords = MouseInfo.getPointerInfo().getLocation();
		int xDestination = 0;
		int yDestination = 0;
		if(_xRepeatCurrent < xRepeat) {
			_xRepeatCurrent++;
			xDestination = mouseCoords.x + xDensity;
			yDestination = mouseCoords.y;
		} else if(_yRepeatCurrent < yRepeat) {
			_yRepeatCurrent++;
			_xRepeatCurrent = 0;
			x.curr = mouseCoords.x;
			xDestination = getRetrace(x);
			yDestination = mouseCoords.y + yDensity;
		} else {
			_yRepeatCurrent = 0;
			_xRepeatCurrent = 0;
			x.curr = mouseCoords.x;
			y.curr = mouseCoords.y;
			xDestination = getRetrace(x);
			yDestination = getRetrace(y);
		}
		moveMouseCallback.callback(xDestination, yDestination);
	}
	
//	-------------Duration types------------
	private boolean finiteClicks() {
		totalClicks--;
		return totalClicks > 0 && _continue;
	}
	
	private boolean infiniteClicks() {
		return _continue;
	}
	
	private int getRetrace(coord c) {
		if(_flow) {
			return c.curr - c.retrace;
		} else {
			return c.init;
		}
	}
	
	// provides a null-type for callbacks.
	private void emptyCallback() {
	}
	
//	*-*-*-*-*-*-*-*-*-Callback assigners and other methods-*-*-*-*-*-*-*-*-*-*
//	--bookkeeping functions used to hold callback assignment events--
	
//	gather a callback for a single or double click, single is default
	private Callback getClickCallback() {
		if(clickType == 2) {
			return (() -> doubleClick());
		} else {
			return (() -> singleClick());
		}
	}
//	decide whether to do finite or infinite clicks.
	private BooleanCallback getDurationCallback() {
		if(_infiniteClicks) {
			return (()->infiniteClicks());
		} else {
			return (()->finiteClicks());
		}
	}
	
	private Callback getMouseMoveTypeCallback() {
//		store a callback for the mouse return, also blank if move is not used.
		if(moveType == MoveType.REPEATOVERAREA) {
			return (()-> moveMouseOverArea(getMouseMoveCallback()));
		} else {
			return (()-> emptyCallback());
		}
	}
	
	private acceptsTwoIntCallback getMouseMoveCallback() {
		if(_exactMove) {
			return ((int xDestination, int yDestination) -> {moveMouseExact(xDestination, yDestination); });
		} else {
			return ((int xDestination, int yDestination) -> {moveMouse(xDestination, yDestination); });
		}
	}
	
	private Callback getMouseMoveCallbackWithArgs(int xDestination, int yDestination) {
		if(moveType != MoveType.NONE) {
			if(_exactMove) {
				return (() -> {moveMouseExact(xDestination, yDestination); });
			} else {
				return (() -> {moveMouse(xDestination, yDestination); });
			}
		}
		return (()->emptyCallback());
	}
	
	private void startAdditionalAutoClickThreads(Callback clickCallback, BooleanCallback durationCallback) {
		if(addClickThreads > 0) {
			Callback moveCallback = (() ->emptyCallback());
			Callback staticAutoClickCallback = (() -> autoClick(clickCallback, durationCallback, moveCallback));
//			add x threads.
			startAdditionalThreads(staticAutoClickCallback, addClickThreads);
		}
	}
	
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
	
	private void end(String message) {
		System.out.println(message);
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

	public int getxRepeat() {
		return xRepeat;
	}

	public void setxRepeat(int xRepeat) {
		this.xRepeat = xRepeat;
	}

	public int getxDensity() {
		return xDensity;
	}

	public void setxDensity(int xDensity) {
		this.xDensity = xDensity;
	}

	public int getyRepeat() {
		return yRepeat;
	}

	public void setyRepeat(int yRepeat) {
		this.yRepeat = yRepeat;
	}

	public int getyDensity() {
		return yDensity;
	}

	public void setyDensity(int yDensity) {
		this.yDensity = yDensity;
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
		xRepeat = e.getxRepeat();
		xDensity = e.getxDensity();
		yRepeat = e.getyRepeat();
		yDensity = e.getyDensity();
		moveType = e.getMoveType();
		_exactMove =  e.isExactMove();
		_flow = e.isFlow();
	}
}

class coord {
//	stores the initial value of the coordinate.
	public int init;
//	stores the coordinate retrace value when returning to the initial value
	public int retrace;
//	stores the current value of the coordinate
	public int curr;
}
