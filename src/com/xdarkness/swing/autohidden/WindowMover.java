package com.xdarkness.swing.autohidden;

import java.awt.Point;  
import java.awt.event.ComponentListener;  
  
import javax.swing.JFrame;
  
/**
 * 用来提示将要隐藏的线程
 * 
 * @author Darkness
 * @version 1.0
 * @since JDF 1.0
 */
public class WindowMover extends Thread {  
  
    public static void moveToPoint(JFrame dialog, Point targetPoint) {  
        new WindowMover(dialog, targetPoint).start();  
    }  
  
    protected static int FRAMES = 15; // 最多移动多少动画帧  
  
    private JFrame window; // 要移动的窗口  
  
    private Point point; // 目的坐标  
  
    private int index; // 当前帧数  
  
    private int addedX; // 每次移动的X坐标增量  
  
    private int addedY; // 每次移动的Y坐标的增量  
  
    ComponentListener[] componentListeners;// 组件侦听器数组  
  
    /* 
     * 定义私有的构造方法,应调用静态方法moveToPoint; 
     */  
    private WindowMover(JFrame window, Point targetPoint) {  
        this.window = window;  
        window.getGlassPane().setVisible(true);  
        // 设置此窗体的GlassPane为显示的,以阻止子组件接收鼠标事件,减少事件触发  
  
        // 同样,移除此窗体上的组件侦听器,防止再次触发窗体移动事件  
        componentListeners = window.getComponentListeners();  
  
        for (ComponentListener cl : componentListeners) {  
            window.removeComponentListener(cl);  
        }  
  
        Point wl = window.getLocation();  
        point = targetPoint;  
        index = 0; // 初始化帧书为0;  
  
        // 计算每次移动量  
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
            return;// 如果已在目的点，则返回  
        if (!window.isVisible())  
            return;// 如果窗口是不可视的则返回  
  
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
        // 还原所做的操作  
        window.getGlassPane().setVisible(false);  
        for (ComponentListener cl : componentListeners) {  
            window.addComponentListener(cl);  
        }  
        // 释放资源,使gc可以回收此对象  
        window = null;  
        point = null;  
        componentListeners = null;  
        System.out.println("finsh Moved");  
    }  
  
}  
