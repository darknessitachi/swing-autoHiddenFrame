package com.xdarkness.swing.autohidden;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Darkness
 * @version 1.0
 * @since JDF 1.0
 */
public class AutoHiddenFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	protected GraphicsConfiguration translucencyCapableGC;// 图形环境

	private AHFBodyState state = AHFBodyState.NORMAL; // 窗体的状态,让它初始化为普通状态
	private Point hiddenPoint; // 隐藏窗体时,窗体的位置
	private Point visiblePoint; // 窗体处于显示状态时的位置

	public AutoHiddenFrame() {
		// 获取系统图形环境
		translucencyCapableGC = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		
		setContentPane(new JPanel(new BorderLayout()) {
			private static final long serialVersionUID = 1L;

			public Insets getInsets() {
				return new Insets(3, 3, 3, 3);
			}
		}); // 替换掉原来的ContentPane,换上一个带有Insets的,至于为什么去看WindowMouseListener类

		new WindowLocationListener(this);
		new WindowMouseListener(this);
		//WindowLocationListener.checkAutoHiddenState(this);// 刚出来就检查一下窗体的位置
		
	}
	
	protected int FWidth;
	protected int FHeight;
	
	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		
		FWidth = width;
		FHeight = height;
	}

	/**
	 * @param newState
	 *            新的状态 一定要是此类中定义的3中状态之一
	 */
	public void setStates(AHFBodyState newState) {
		state = newState;
	}

	/*
	 * 返回状态,注意此方法和setStates方法区别与JFrame中的setState()和getState()方法
	 */
	public AHFBodyState getStates() {
		return state;
	}

	/*
	 * 设置要显示时窗体的坐标
	 */
	public void setVisiblePoint(Point point) {
		visiblePoint = point;
	}

	/*
	 * 设置要隐藏是窗体的坐标
	 */
	public void setHiddenPoint(Point point) {
		hiddenPoint = point;
	}

	public void moveToVisible() {
		if (visiblePoint != null) {
			WindowMover.moveToPoint(this, visiblePoint);
			setStates(AHFBodyState.CANHIDD);
		}
	}

	public void moveToHidden() {
		if (hiddenPoint != null) {
			WindowMover.moveToPoint(this, hiddenPoint);
			setStates(AHFBodyState.HIDDEN);
		}
	}
	
	public void setMoveFrames(int frames){
		WindowMover.FRAMES = frames;
	}

}

/**
 * 窗体状态
 * @author Darkness
 * @version 1.0
 * @since JDF 1.0
 */
class AHFBodyState {
	private String bodyState;

	AHFBodyState(String bodyState) {
		this.bodyState = bodyState;
	}

	public static final AHFBodyState NORMAL = new AHFBodyState("nomal"); // 窗体的普通状态
	public static final AHFBodyState CANHIDD = new AHFBodyState("can hid"); // 窗体位于屏幕边缘,可以隐藏的状态
	public static final AHFBodyState HIDDEN = new AHFBodyState("hidden"); // 窗体处于隐藏状态

	public String toString() {
		return bodyState;
	}
}