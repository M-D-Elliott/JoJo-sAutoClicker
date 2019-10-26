package app;

import java.awt.MouseInfo;
import java.awt.Point;

import callbacks.ReturnsIntAcceptsObjectCallback;

public class PositionsObject {
	protected boolean _flow = true;
	
	protected RepeatedCoord x = new RepeatedCoord();
	protected RepeatedCoord y = new RepeatedCoord();
	
	protected boolean xyInvert = false;
	
	protected byte _tries = 9;
	
	protected void repeatPositionOverArea() {
		ReturnsIntAcceptsObjectCallback standardRetrace = (Object o) -> { return initRetrace((RepeatedCoord)o); };
		repeatPositionOverArea(standardRetrace);
	}
	
	protected void repeatPositionOverArea(ReturnsIntAcceptsObjectCallback retraceCallback) {
		repeatPositionOverArea(x, y, retraceCallback);
	}
	
	protected void repeatPositionOverArea(RepeatedCoord rCInner, RepeatedCoord rCOuter, ReturnsIntAcceptsObjectCallback retraceCallback) {
		if(rCInner.repeatCurrent < rCInner.repeat) {
			rCInner.curr += rCInner.density;
			rCInner.repeatCurrent++;
		} else if(rCOuter.repeatCurrent < rCOuter.repeat) {
			rCInner.curr = retraceCallback.callback(rCInner);
			rCOuter.curr += rCOuter.density;
			rCInner.repeatCurrent = 0;
			rCOuter.repeatCurrent++;
		} else {
			rCInner.curr = retraceCallback.callback(rCInner);
			rCOuter.curr= retraceCallback.callback(rCOuter);
			rCInner.repeatCurrent = 0;
			rCOuter.repeatCurrent = 0;
		}
	}
	
	protected ReturnsIntAcceptsObjectCallback getRetraceCallback() {
		if(_flow) {
			return (Object o) -> { return relativeRetrace((RepeatedCoord)o); };
		}
		return (Object o) -> { return initRetrace((RepeatedCoord)o); };
	}

	private int relativeRetrace(RepeatedCoord rC) {
		return rC.curr - rC.density * rC.repeatCurrent;
	}
	
	private int initRetrace(RepeatedCoord rC) {
		return rC.init;
	}
	
	protected Point tryGetMousePosition() {
		return tryGetMousePosition(_tries);
	}
	
	protected Point tryGetMousePosition(byte tries) {
		while(tries > 0) {
			try {
				Point mouseCoords = MouseInfo.getPointerInfo().getLocation();
				return mouseCoords;
			} catch (Exception e) {
				e.printStackTrace();
			}
			tries--;
		}
		return new Point(0,0);
	}
}

class RepeatedCoord {
//	stores the initial value of the coordinate.
	protected int init;
	
//	stores the current value of the coordinate
	protected int curr;
	
//	stores the density of the coordinate.
	protected int density = 50;
	
//	repeat over distance in pixels on x-axis
	protected byte repeat = 0;
	
//	stores the repeat position for x.
	protected byte repeatCurrent = 0;
	
//	stores positive or negative sign of coord
	protected byte sign = 1;
	
//	stores max/min values of coord if needed.
	protected int min = 0;
	protected int max = 0;
}