package gui;

import java.io.Serializable;
import java.util.EventObject;

import app.MoveType;

public class SettingsEvent extends EventObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private int delay;
	private int clicks;
	private boolean infiniteClicks;
	private int xRepeat;
	private int yRepeat;
	private int xDensity;
	private int yDensity;
	private MoveType moveType;
	private boolean exactMove;
	private boolean flow;
	private boolean graphics;


	public SettingsEvent(Object source, int delay, int clicks, boolean infiniteClicks, int xRepeat, int yRepeat,
			int xDensity, int yDensity, MoveType moveType, boolean exactMove, boolean flow, boolean graphics) {
		super(source);
		this.delay = delay;
		this.clicks = clicks;
		this.infiniteClicks = infiniteClicks;
		this.xRepeat = xRepeat;
		this.yRepeat = yRepeat;
		this.xDensity = xDensity;
		this.yDensity = yDensity;
		this.moveType = moveType;
		this.exactMove = exactMove;
		this.flow = flow;
		this.graphics = graphics;
	}

	public int getDelay() {
		return delay;
	}
	
	public int getClicks() {
		return clicks;
	}

	public boolean isInfiniteClicks() {
		return infiniteClicks;
	}

	public int getxRepeat() {
		return xRepeat;
	}

	public int getyRepeat() {
		return yRepeat;
	}

	public int getxDensity() {
		return xDensity;
	}

	public int getyDensity() {
		return yDensity;
	}
	
	public MoveType getMoveType() {
		return moveType;
	}

	public boolean isExactMove() {
		return exactMove;
	}

	public boolean isFlow() {
		return flow;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}

	public void setInfiniteClicks(boolean infiniteClicks) {
		this.infiniteClicks = infiniteClicks;
	}

	public void setxRepeat(int xRepeat) {
		this.xRepeat = xRepeat;
	}

	public void setyRepeat(int yRepeat) {
		this.yRepeat = yRepeat;
	}

	public void setxDensity(int xDensity) {
		this.xDensity = xDensity;
	}

	public void setyDensity(int yDensity) {
		this.yDensity = yDensity;
	}

	public void setMoveType(MoveType moveType) {
		this.moveType = moveType;
	}

	public void setExactMove(boolean exactMove) {
		this.exactMove = exactMove;
	}

	public void setFlow(boolean flow) {
		this.flow = flow;
	}

	public boolean isGraphics() {
		return graphics;
	}

	public void setGraphics(boolean graphics) {
		this.graphics = graphics;
	}
	
	
	
}
