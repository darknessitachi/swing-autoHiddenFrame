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
	
	protected GraphicsConfiguration translucencyCapableGC;// ͼ�λ���

	private AHFBodyState state = AHFBodyState.NORMAL; // �����״̬,������ʼ��Ϊ��ͨ״̬
	private Point hiddenPoint; // ���ش���ʱ,�����λ��
	private Point visiblePoint; // ���崦����ʾ״̬ʱ��λ��

	public AutoHiddenFrame() {
		// ��ȡϵͳͼ�λ���
		translucencyCapableGC = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		
		setContentPane(new JPanel(new BorderLayout()) {
			private static final long serialVersionUID = 1L;

			public Insets getInsets() {
				return new Insets(3, 3, 3, 3);
			}
		}); // �滻��ԭ����ContentPane,����һ������Insets��,����Ϊʲôȥ��WindowMouseListener��

		new WindowLocationListener(this);
		new WindowMouseListener(this);
		//WindowLocationListener.checkAutoHiddenState(this);// �ճ����ͼ��һ�´����λ��
		
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
	 *            �µ�״̬ һ��Ҫ�Ǵ����ж����3��״̬֮һ
	 */
	public void setStates(AHFBodyState newState) {
		state = newState;
	}

	/*
	 * ����״̬,ע��˷�����setStates����������JFrame�е�setState()��getState()����
	 */
	public AHFBodyState getStates() {
		return state;
	}

	/*
	 * ����Ҫ��ʾʱ���������
	 */
	public void setVisiblePoint(Point point) {
		visiblePoint = point;
	}

	/*
	 * ����Ҫ�����Ǵ��������
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
 * ����״̬
 * @author Darkness
 * @version 1.0
 * @since JDF 1.0
 */
class AHFBodyState {
	private String bodyState;

	AHFBodyState(String bodyState) {
		this.bodyState = bodyState;
	}

	public static final AHFBodyState NORMAL = new AHFBodyState("nomal"); // �������ͨ״̬
	public static final AHFBodyState CANHIDD = new AHFBodyState("can hid"); // ����λ����Ļ��Ե,�������ص�״̬
	public static final AHFBodyState HIDDEN = new AHFBodyState("hidden"); // ���崦������״̬

	public String toString() {
		return bodyState;
	}
}