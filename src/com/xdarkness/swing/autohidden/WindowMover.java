package com.xdarkness.swing.autohidden;

import java.awt.Point;  
import java.awt.event.ComponentListener;  
  
import javax.swing.JFrame;
  
/**
 * ������ʾ��Ҫ���ص��߳�
 * 
 * @author Darkness
 * @version 1.0
 * @since JDF 1.0
 */
public class WindowMover extends Thread {  
  
    public static void moveToPoint(JFrame dialog, Point targetPoint) {  
        new WindowMover(dialog, targetPoint).start();  
    }  
  
    protected static int FRAMES = 15; // ����ƶ����ٶ���֡  
  
    private JFrame window; // Ҫ�ƶ��Ĵ���  
  
    private Point point; // Ŀ������  
  
    private int index; // ��ǰ֡��  
  
    private int addedX; // ÿ���ƶ���X��������  
  
    private int addedY; // ÿ���ƶ���Y���������  
  
    ComponentListener[] componentListeners;// �������������  
  
    /* 
     * ����˽�еĹ��췽��,Ӧ���þ�̬����moveToPoint; 
     */  
    private WindowMover(JFrame window, Point targetPoint) {  
        this.window = window;  
        window.getGlassPane().setVisible(true);  
        // ���ô˴����GlassPaneΪ��ʾ��,����ֹ�������������¼�,�����¼�����  
  
        // ͬ��,�Ƴ��˴����ϵ����������,��ֹ�ٴδ��������ƶ��¼�  
        componentListeners = window.getComponentListeners();  
  
        for (ComponentListener cl : componentListeners) {  
            window.removeComponentListener(cl);  
        }  
  
        Point wl = window.getLocation();  
        point = targetPoint;  
        index = 0; // ��ʼ��֡��Ϊ0;  
  
        // ����ÿ���ƶ���  
        addedX = (point.x - wl.x) / FRAMES;  
        if (addedX == 0 && point.x != wl.x) {  
            addedX = point.x < wl.x ? -1 : 1;  
        }  
        addedY = (point.y - wl.y) / FRAMES;  
        if (addedY == 0 && point.y != wl.y) {  
            addedY = point.y < wl.y ? -1 : 1;  
        }  
    }  
  
    public void run() {  
    	System.out.println(window.getLocation() + "=====" + point);
  
        if (window.getLocation().equals(point))  
            return;// �������Ŀ�ĵ㣬�򷵻�  
        if (!window.isVisible())  
            return;// ��������ǲ����ӵ��򷵻�  
  
        while (index < FRAMES) {  
            Point p = window.getLocation();  
            if (p.x != point.x)  
                p.translate(addedX, 0);  
            if (p.y != point.y)  
                p.translate(0, addedY);  
            window.setLocation(p);  
            index++;  
            try {  
                Thread.sleep(15);  
            } catch (Exception e) {  
            }  
        }  
        window.setLocation(point);  
        // ��ԭ�����Ĳ���  
        window.getGlassPane().setVisible(false);  
        for (ComponentListener cl : componentListeners) {  
            window.addComponentListener(cl);  
        }  
        // �ͷ���Դ,ʹgc���Ի��մ˶���  
        window = null;  
        point = null;  
        componentListeners = null;  
        System.out.println("finsh Moved");  
    }  
  
}  
