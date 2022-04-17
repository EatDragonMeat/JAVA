package com.mashibing.tank;

import java.awt.*;

/**
 * @Auther: wz
 * @Date: 2022/4/17 - 04 - 17 - 上午10:38
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public class Expload {
    // 子弹的速度
    private static final int SPEED = 10;
    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private TankFrame tf;
    private int x,y;

    private int step = 0 ;

    public Expload(int x, int y,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
    }

    public void paint(Graphics g) {

        g.drawImage(ResourceMgr.explodes[step++],x,y,null);

        if(step >= ResourceMgr.explodes.length)
            tf.exploads.remove(this);

    }





}
