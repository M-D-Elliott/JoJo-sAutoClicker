package app;

import java.awt.MouseInfo;
import java.awt.Point;

import callbacks.ReturnsIntAcceptsObjectCallback;

public class PositionsObject {
	protected boolean _flow = true;
	
	protected repeatedCoord x = new repeatedCoord();
	protected repeatedCoord y = new repeatedCoord();

	protected void repeatPositionOverArea() {
		if(x.repeatCurrent < x.repeat) {
			x.repeatCurrent++;
			x.curr = x.init + x.density * x.repeatCurrent;
			y.curr = y.init + y.density * y.repeatCurrent;
		} else if(y.repeatCurrent < y.repeat) {
			x.repeatCurrent = 0;
			y.repeatCurrent++;
			x.curr = x.init;
			y.curr = y.init + y.density * y.repeatCurrent;
		} else {
			x.repeatCurrent = 0;
			y.repeatCurrent = 0;
			x.curr = x.init;
			y.curr= y.init;
		}
	}
	
	protected void repeatPositionOverArea(ReturnsIntAcceptsObjectCallback retraceCallback) {
		if(x.repeatCurrent < x.repeat) {
			x.curr += x.density;
			x.repeatCurrent++;
		} else if(y.repeatCurrent < y.repeat) {
			x.curr = retraceCallback.callback(x);
			y.curr += y.density;
			x.repeatCurrent = 0;
			y.repeatCurrent++;
		} else {
			x.curr = retraceCallback.callback(x);
			y.curr= retraceCallback.callback(y);
			x.repeatCurrent = 0;
			y.repeatCurrent = 0;
		}
	}
	
	protected ReturnsIntAcceptsObjectCallback getRetraceCallback() {
		if(_flow) {
			return ((Object o) -> {return flowRetrace((repeatedCoord)o); });
		}
		return ((Object o) -> { return standardRetrace((repeatedCoord)o); });
	}

	private int flowRetrace(repeatedCoord rC) {
		return rC.curr - rC.density * rC.repeatCurrent;
	}
	
	private int standardRetrace(repeatedCoord rC) {
		return rC.init;
	}
	
	protected Point tryGetMousePosition() {
		Point mouseCoords = null;
		while(mouseCoords == null) {
			try {
				mouseCoords = MouseInfo.getPointerInfo().getLocation();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return mouseCoords;
	}
}

class repeatedCoord {
//	stores the initial value of the coordinate.
	public int init;
//	stores the current value of the coordinate
	public int curr;
//	repeat over distance in pixels on x-axis
	protected int repeat = 0;
//	stores the repeat position for x.
	protected int repeatCurrent = 0;
//	stores the density of the coordinate.
	protected int density = 50;
}