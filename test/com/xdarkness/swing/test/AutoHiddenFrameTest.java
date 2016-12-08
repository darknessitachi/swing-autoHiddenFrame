package com.xdarkness.swing.test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.xdarkness.swing.autohidden.AutoHiddenFrame;

/**
 * 自动隐藏窗体测试
 * @author Darkness 
 * create on 2010-11-26 下午02:02:14
 * @version 1.0
 * @since JDF 1.0
 */
public class AutoHiddenFrameTest extends AutoHiddenFrame {

	private static final long serialVersionUID = -455700849275575191L;

	public AutoHiddenFrameTest(){
		this.setTitle("Music");
		this.setSize(228, 720);
		this.setMoveFrames(100);// 设置自动隐藏窗体移动的帧住，数值越大，速度越慢
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
