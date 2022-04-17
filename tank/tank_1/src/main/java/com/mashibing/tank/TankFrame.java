package com.mashibing.tank;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;


/**
 * @Auther: wz
 * @Date: 2022/4/16 - 04 - 16 - 上午10:32
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public class TankFrame extends Frame {

    Tank myTank = new Tank(200,500, Dir.UP, Group.GOOD,this);
    List<Bullet> bullets = new ArrayList<>();
    List<Tank> tanks = new ArrayList<>();
    List<Expload> exploads = new ArrayList<>();
    static final int GAME_WIDTH = 800, GAME_HEIGHT=600;

    public TankFrame(){
        // 设置大小
        setSize(GAME_WIDTH,GAME_HEIGHT);
        // 设置他能不能改变大小
        setResizable(false);
        // 设置标题
        setTitle(" tank war");
        // 设置为可见
        setVisible(true);

        // 添加键盘监听
        this.addKeyListener(new MyKeyListener());

        // 添加一个Windows 监听器
        // 监听着 windowClosing 我们点关闭就触发
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });



    }


    // 这个概念是这样的
    // 我们的屏幕 屏幕上能看到显示的内容 是其中的一个画面
    // 如果说我们直接在画面里进行绘画 将原来的背景颜色进行改变，这话点图那画点图
    // 他会出现不一致的情况 比如我刚画到一半 就已经刷了一遍了 我们下面的内容才画出来
    // 这种情况就会出现闪烁的情况
    // 我们打开一张非常大的图的时候 是不是经常看到一段一段的显示
    // 屏幕刷新的特别快 后面的计算跟不上就会出现这个现象

    // 想他这个闪烁现象处理了 我们就在内存中给他定义一块跟他一样大小的空间
    // 先将要画的内容画到这个空间 等画完了 我们再将内容一次性画到 屏幕上
    // 这个时候闪烁现象就消除了

    //具体怎么实现呢？
    // 1. 我们首先定义一张图片  这张图片是定义在内存中
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {  // Graphics g 这里是屏幕上的画笔 系统帮我们调的
        if (offScreenImage == null) {
            // 按照窗口大小生成图片
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        // 拿到图片上的画笔
        Graphics gOffScreen = offScreenImage.getGraphics();
        // 获取画笔颜色
        Color c = gOffScreen.getColor();
        // 设置画笔颜色
        gOffScreen.setColor(Color.BLACK);
        // 将背景重新画一遍
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);  // 将图片的画笔交给 paint
        g.drawImage(offScreenImage, 0, 0, null); //最后再把整体的图片画到屏幕上
    }



    // 这个方法是被自动调用的
    // 什么时候会被调用呢
    // 1. 窗口第一次显示出来的时候  2. 窗口被别人盖住然后又出来的的时候
    // 简单来说窗口被重新绘制的时候
    // Graphics 我们把他想象成一只画笔
    // paint 是系统自动调用， 系统在调用 paint的时候他会给我们一只画笔 Graphics
    // 我们 拿着这只画笔在窗口里面想化什么就画什么
    @Override
    public void paint(Graphics g){
        Color color = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹数量："+bullets.size(),10,60);
        g.drawString("敌人数量："+tanks.size(),10,80);
        g.drawString("爆炸数量："+exploads.size(),10,100);
        g.setColor(color);

        //将画笔传给我们的主战坦克 让他自己把自己画出来
        myTank.paint(g);

//        for (Bullet b : bullets) {
//            //将画笔交给子弹 让他自己把自己画出来
//            b.paint(g);
//        }

        // 画子弹
        for(int i = 0;i<bullets.size();i++){
            bullets.get(i).paint(g);
        }

        // 画敌人坦克
        for(int i = 0;i<tanks.size();i++){
            tanks.get(i).paint(g);
        }

        // 判断坦克是否跟子弹撞上了
        for(int i=0;i<bullets.size();i++){
            for(int j =0;j<tanks.size();j++){
                bullets.get(i).collideWith(tanks.get(j));
            }
        }

        // 画爆炸
        for(int i=0;i<exploads.size();i++){
            exploads.get(i).paint(g);
        }

    }



    // 键盘监听处理类
    class MyKeyListener extends KeyAdapter{

        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;

        // 系统调用我们的方法  keyEvent 给我们传递的对象
        @Override
        public void keyPressed(KeyEvent e) {
            // 获取哪个键被按下了
            int key = e.getKeyCode();

            //键盘按下去将他设置为 true
            switch (key){
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }


            //改变坦克方向
            setMainTankDir();


        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            //键盘抬起来将他设置为 false
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }

            //坦克方向
            setMainTankDir();

        }

        private void setMainTankDir() {
            if(!bL && !bU && !bD && !bR){
                myTank.setMoving(false);
            }else{
                myTank.setMoving(true);
            }

            // 你觉得你是同时按下去的 对计算机来说绝对有先后
            if(bL)  myTank.setDir(Dir.LEFT);
            if(bU)  myTank.setDir(Dir.UP);
            if(bR)  myTank.setDir(Dir.RIGHT);
            if(bD)  myTank.setDir(Dir.DOWN);

        }

    }


}
