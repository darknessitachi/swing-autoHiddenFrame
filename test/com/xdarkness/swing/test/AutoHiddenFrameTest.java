package com.xdarkness.swing.test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.xdarkness.swing.autohidden.AutoHiddenFrame;

/**
 * �Զ����ش������
 * @author Darkness 
 * create on 2010-11-26 ����02:02:14
 * @version 1.0
 * @since JDF 1.0
 */
public class AutoHiddenFrameTest extends AutoHiddenFrame {

	private static final long serialVersionUID = -455700849275575191L;

	public AutoHiddenFrameTest(){
		this.setTitle("Music");
		this.setSize(228, 720);
		this.setMoveFrames(100);// �����Զ����ش����ƶ���֡ס����ֵԽ���ٶ�Խ��
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				AutoHiddenFrameTest shell = new AutoHiddenFrameTest();
				shell.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				shell.setVisible(true);
			}
		});
	}
}
