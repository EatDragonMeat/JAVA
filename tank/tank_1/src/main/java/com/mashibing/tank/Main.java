package com.mashibing.tank;

/**
 * @Auther: wz
 * @Date: 2022/4/16 - 04 - 16 - 上午10:15
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();


        //初始化地方坦克
        for(int i=0;i<5;i++){
            tf.tanks.add(new Tank(50 + i*80,200, Dir.DOWN, Group.BAD, tf));
        }

        //music
        new Thread(()->new Audio("audio/war1.wav").loop()).start();

        while(true){
            // 休眠多长时间 50 毫秒
            Thread.sleep(50);
            // 每隔 50 毫秒 我们就重画一次窗口
            tf.repaint();
        }

    }
}
