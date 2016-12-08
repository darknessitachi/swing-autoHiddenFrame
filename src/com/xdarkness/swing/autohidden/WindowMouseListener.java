package com.xdarkness.swing.autohidden;

import java.awt.Container;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Ϊ�����������¼�������
 * 
 * @author Darkness
 * @version 1.0
 * @since JDF 1.0
 */
public class WindowMouseListener extends MouseAdapter implements  
        ActionListener, WindowFocusListener {  
    private Timer timer;  
  
    private AutoHiddenFrame frame;  
  
    Container container;  
  
    public WindowMouseListener(AutoHiddenFrame a) {  
        frame = a;  
        container = frame.getContentPane();  
        container.addMouseListener(this);  
        // ע�������������ContentPane��,��Ϊ���ǿ��ԼӴ�����Insets�������������뿪��������  
        frame.addWindowFocusListener(this);  
        // ע��һ��������������������  
        timer = new Timer(2000, this);  
        timer.setRepeats(false);  
    }  
  
    public void mouseEntered(MouseEvent e) {  
        // ��������,����ʾ����  
        if (frame.getStates() == AHFBodyState.HIDDEN) {  
            frame.moveToVisible();  
        }
  
    }  
  
    public void mouseExited(MouseEvent e) {  
        // ������뿪,������ʱ��  
        if (frame.getStates() == AHFBodyState.CANHIDD) {  
            if (!container.contains(e.getPoint())) {  
                 System.out.println(timer.getDelay() / 1000 + "����Զ����ش���!");  
                timer.restart();  
            }  
        }  
  
    }  
  
    public void actionPerformed(ActionEvent e) {  
        // ��ʱ������,�������ǲ��ǻ��ڴ˴�������,���ٵĻ�,�ٿ�ʼ����  
        Point p = MouseInfo.getPointerInfo().getLocation();  
        SwingUtilities.convertPointFromScreen(p, container);  
        if (!container.contains(p)  
                && frame.getStates() == AHFBodyState.CANHIDD) {  
            frame.moveToHidden();  
        } 
    }  
  
    public void windowGainedFocus(WindowEvent e) {  
        // �õ�����������ǲ����ڴ�����  
        Point p = MouseInfo.getPointerInfo().getLocation();  
        SwingUtilities.convertPointFromScreen(p, container);  
        if (container.contains(p)  
                && frame.getStates() == AHFBodyState.HIDDEN) {  
            frame.moveToVisible();  
        }  
    }  
  
    public void windowLostFocus(WindowEvent e) {  
        // ʧȥ����,������ʱ��  
        if (frame.getStates() == AHFBodyState.CANHIDD) {  
            System.out.println("2����Զ����ش���!");  
            timer.restart();  
        }  
    }  
}  
