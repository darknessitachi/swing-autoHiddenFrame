package com.xdarkness.swing.autohidden;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Ϊ������ӿ��ƴ����Ƿ����صļ����� �жϴ����λ�ã�������ʾ�����صĵ㣬�������״̬
 * 
 * @author Darkness
 * @version 1.0
 * @since JDF 1.0
 * 
 */
public class WindowLocationListener extends ComponentAdapter {

	public static final int HIDDEN_BOUND = 3; // ��������뵽��Ļ��Ե3�������ھͿ�������
	public static final int VISIBLE_BOUND = 5; // ���������غ�Ҫ��5���صĲ���¶����,������ȫ����

	AutoHiddenFrame frame;

	public WindowLocationListener(AutoHiddenFrame a) {
		frame = a;
		frame.addComponentListener(this);
	}

	public void componentMoved(ComponentEvent e) {
		checkAutoHiddenState(frame);// �������ƶ��͵��ü�鷽��;
	}

	/**
	 * �жϴ����λ�ã�������ʾ�����صĵ㣬�������״̬
	 * 
	 * @param frame
	 */
	public static void checkAutoHiddenState(AutoHiddenFrame frame) {

		// ������״̬�������ص�,�ٽ��м��
		if (frame.getStates() == AHFBodyState.HIDDEN) {
			return;
		}

		// ���Ȼ����Ļ�Ĵ�С�ʹ��������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		Point hiddenPoint = frame.getLocation();
		Point visiblePoint = null;
		boolean canhidden = false;

		System.out.println("hiddenPoint before:" + hiddenPoint);
		
		// ������λ����߱�Ե
		if (hiddenPoint.x <= HIDDEN_BOUND) {
			hiddenPoint.move(VISIBLE_BOUND - frame.FWidth, hiddenPoint.y);
			visiblePoint = new Point(0, hiddenPoint.y);
			canhidden = true;
		}
		// ������λ���ϱ�
		else if (hiddenPoint.y <= HIDDEN_BOUND) {
			hiddenPoint.setLocation(hiddenPoint.x, VISIBLE_BOUND
					- frame.FHeight);
			visiblePoint = new Point(hiddenPoint.x, 0);
			canhidden = true;
		}
		// ������λ���ұ�
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
			// ����������ϼ��������һ�־Ϳ�������
			frame.setVisiblePoint(visiblePoint);
			frame.setHiddenPoint(hiddenPoint);
			frame.setStates(AHFBodyState.CANHIDD);
			System.out.println("�������������!");
		} else {
			// �������������,�Ǿ����뿪�˱�Ե��
			if (frame.getStates() == AHFBodyState.CANHIDD) {
				System.out.println("�뿪��Ӧ������!");
			}
			frame.setVisiblePoint(frame.getLocation());
			frame.setStates(AHFBodyState.NORMAL);
		}
	}
}
