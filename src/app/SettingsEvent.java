package app;

import java.io.Serializable;
import java.util.EventObject;

import interfaces.MoveType;

public class SettingsEvent extends EventObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private int delay;
	private int clicks;
	private boolean infiniteClicks;
	private int xRepeat;
	private int xDensity;
	private int xSign;
	private int yRepeat;
	private int yDensity;
	private int ySign;
	private boolean xyInvert;
	private MoveType moveType;
	private boolean exactMove;
	private boolean flow;
	private boolean graphics;

	public SettingsEvent(Object source, int delay, int clicks, boolean infiniteClicks, int xRepeat, int xDensity,
			int xSign, int yRepeat, int yDensity, int ySign, boolean xyInvert, MoveType moveType, boolean exactMove, boolean flow,
			boolean graphics) {
		super(source);
		this.delay = delay;
		this.clicks = clicks;
		this.infiniteClicks = infiniteClicks;
		this.xRepeat = xRepeat;
		this.xDensity = xDensity;
		this.xSign = xSign;
		this.yRepeat = yRepeat;
		this.yDensity = yDensity;
		this.ySign = ySign;
		this.xyInvert = xyInvert;
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

	public int getxDensity() {
		return xDensity;
	}

	public int getxSign() {
		return xSign;
	}

	public int getyRepeat() {
		return yRepeat;
	}

	public int getyDensity() {
		return yDensity;
	}

	public int getySign() {
		return ySign;
	}

	public boolean isxyInvert() {
		return xyInvert;
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

	public boolean isGraphics() {
		return graphics;
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

	public void setxDensity(int xDensity) {
		this.xDensity = xDensity;
	}

	public void setxSign(int xSign) {
		this.xSign = xSign;
	}

	public void setyRepeat(int yRepeat) {
		this.yRepeat = yRepeat;
	}

	public void setyDensity(int yDensity) {
		this.yDensity = yDensity;
	}

	public void setySign(int ySign) {
		this.ySign = ySign;
	}

	public void setxyInvert(boolean xyInvert) {
		this.xyInvert = xyInvert;
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

	public void setGraphics(boolean graphics) {
		this.graphics = graphics;
	}

}
