package com.xdarkness.swing.autohidden;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * 为窗体添加控制窗口是否隐藏的监听器 判断窗体的位置，更新显示、隐藏的点，及窗体的状态
 * 
 * @author Darkness
 * @version 1.0
 * @since JDF 1.0
 * 
 */
public class WindowLocationListener extends ComponentAdapter {

	public static final int HIDDEN_BOUND = 3; // 当窗体进入到屏幕边缘3像素以内就可以隐藏
	public static final int VISIBLE_BOUND = 5; // 当窗体隐藏后要有5像素的部分露出来,不能完全隐藏

	AutoHiddenFrame frame;

	public WindowLocationListener(AutoHiddenFrame a) {
		frame = a;
		frame.addComponentListener(this);
	}

	public void componentMoved(ComponentEvent e) {
		checkAutoHiddenState(frame);// 当窗体移动就调用检查方法;
	}

	/**
	 * 判断窗体的位置，更新显示、隐藏的点，及窗体的状态
	 * 
	 * @param frame
	 */
	public static void checkAutoHiddenState(AutoHiddenFrame frame) {

		// 当窗体状态不是隐藏的,再进行检查
		if (frame.getStates() == AHFBodyState.HIDDEN) {
			return;
		}

		// 首先获得屏幕的大小和窗体的坐标
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		Point hiddenPoint = frame.getLocation();
		Point visiblePoint = null;
		boolean canhidden = false;

		System.out.println("hiddenPoint before:" + hiddenPoint);
		
		// 当窗体位于左边边缘
		if (hiddenPoint.x <= HIDDEN_BOUND) {
			hiddenPoint.move(VISIBLE_BOUND - frame.FWidth, hiddenPoint.y);
			visiblePoint = new Point(0, hiddenPoint.y);
			canhidden = true;
		}
		// 当窗体位于上边
		else if (hiddenPoint.y <= HIDDEN_BOUND) {
			hiddenPoint.setLocation(hiddenPoint.x, VISIBLE_BOUND
					- frame.FHeight);
			visiblePoint = new Point(hiddenPoint.x, 0);
			canhidden = true;
		}
		// 当窗体位于右边
		else if (hiddenPoint.x + frame.getWidth() >= screenSize.width
				- HIDDEN_BOUND) {
			hiddenPoint.setLocation(screenSize.width - VISIBLE_BOUND,
					hiddenPoint.y);
			visiblePoint = new Point(screenSize.width - frame.FWidth,
					hiddenPoint.y);
			canhidden = true;
		}
		System.out.println("hiddenPoint after:" + hiddenPoint);
		if (canhidden) {
			// 如果符合以上几种情况的一种就可以隐藏
			frame.setVisiblePoint(visiblePoint);
			frame.setHiddenPoint(hiddenPoint);
			frame.setStates(AHFBodyState.CANHIDD);
			System.out.println("进入可隐藏区域!");
		} else {
			// 如果不可以隐藏,那就是离开了边缘了
			if (frame.getStates() == AHFBodyState.CANHIDD) {
				System.out.println("离开可应藏区域!");
			}
			frame.setVisiblePoint(frame.getLocation());
			frame.setStates(AHFBodyState.NORMAL);
		}
	}
}
