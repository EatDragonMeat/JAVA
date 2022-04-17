package com.mashibing.tank;

import java.awt.*;
import java.util.Random;

/**
 * @Auther: wz
 * @Date: 2022/4/16 - 04 - 16 - 下午1:59
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public class Tank {
    // 自己的坐标
    private int x,y;
    // 自己的方向
    private Dir dir = Dir.DOWN;
    // 移动速度
    private static final int SPEED = 5;
    public static int WIDTH = ResourceMgr.goodTankU.getWidth();

    public static int HEIGHT = ResourceMgr.goodTankU.getHeight();

    private Random random = new Random();

    // 坦克是否在移动
    private boolean moving = true;

    private TankFrame tf;

    private boolean living = true;

    private Group group;
    Rectangle rect = new Rectangle();

    public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void paint(Graphics g) {
//        Color color = g.getColor();
//        g.setColor(Color.yellow);
//        // 左上角开始 向右是X 向下是Y
//        g.fillRect(x,y,50,50);
//        g.setColor(color);

        // 画坦克图片
//        g.drawImage(ResourceMgr.tankL,x,y,null);

        if(!living){
            tf.tanks.remove(this);
            return;
        }

        switch(dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
        }




        move();
    }

    private void move() {

        if(!moving) return;

        switch (dir){
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            default:
                break;
        }


        if(this.group == Group.BAD && random.nextInt(10)>8){
            this.fire();
            randomDir();
        }

        boundsCheck();

        rect.x = this.x;
        rect.y = this.y;

    }

    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > TankFrame.GAME_WIDTH- Tank.WIDTH -2) x = TankFrame.GAME_WIDTH - Tank.WIDTH -2;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT -2 ) y = TankFrame.GAME_HEIGHT -Tank.HEIGHT -2;
    }

    private void randomDir(){
        this.dir = Dir.values()[random.nextInt(4)];
    }


    public void fire() {
        // 子弹的 X Y 位置
        int bx = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int by = this.y + Tank.WIDTH/2 - Bullet.HEIGHT/2;

        // 将子弹 赋值给 TankFrame
        tf.bullets.add(new Bullet(bx,by,this.dir,this.group,tf));
    }

    public void die() {
        this.living = false;
    }
}
