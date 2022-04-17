package com.mashibing.tank;

import java.awt.*;

/**
 * @Auther: wz
 * @Date: 2022/4/16 - 04 - 16 - 下午2:55
 * @Description: com.mashibing.tank
 * @version: 1.0
 */
public class Bullet {
    // 子弹的速度
    private static final int SPEED = 10;
    public static int WIDTH = ResourceMgr.bulletD.getWidth(),HEIGHT = ResourceMgr.bulletD.getHeight();
    private boolean living = true;
    private TankFrame tf;

    private Group group;

    private int x,y;
    private Dir dir;
    Rectangle rect = new Rectangle();

    public Bullet(int x, int y, Dir dir, Group group,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }

    public void paint(Graphics g) {

        if(!living){
            tf.bullets.remove(this);
            return;
        }

//            //保存画笔颜色
//            Color color = g.getColor();
//
//            //设置画笔颜色
//            g.setColor(Color.RED);
//            // 我们画一个 圆的
//            // 左上角开始 向右是X 向下是Y
//            g.fillOval(x, y, WIDTH, HEIGHT);
//
//            // 将画笔颜色还原
//            g.setColor(color);


            switch (dir){
                case LEFT:
                    g.drawImage(ResourceMgr.bulletL,x,y,null);
                    break;
                case UP:
                    g.drawImage(ResourceMgr.bulletU,x,y,null);
                    break;
                case RIGHT:
                    g.drawImage(ResourceMgr.bulletR,x,y,null);
                    break;
                case DOWN:
                    g.drawImage(ResourceMgr.bulletD,x,y,null);
                    break;
                default:
                    break;
            }

            rect.x = this.x;
            rect.y = this.y;

            move();

    }


    private void move() {

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

        if (x<0 || y<0 || x>TankFrame.GAME_WIDTH || y>TankFrame.GAME_WIDTH){
            living = false;
        }

    }

    public void collideWith(Tank tank) {

        if(this.group == tank.getGroup()) return;

        // 每次检测都会NEW 对象 我们现在使用一个
        // 判断两个是否相交
//        Rectangle rect1 = new Rectangle(this.x,this.y,WIDTH,HEIGHT);
//        Rectangle rect2 = new Rectangle(tank.getX(),tank.getY(),tank.WIDTH,tank.HEIGHT);

        if(this.rect.intersects(tank.rect)){

            tank.die();
            this.die();
            int ex = tank.getX() + Tank.WIDTH/2 - Expload.WIDTH/2;
            int ey = tank.getY() + Tank.WIDTH/2 - Expload.HEIGHT/2;
            tf.exploads.add(new Expload(ex,ey,this.tf));
        }

    }

    private void die() {
        this.living = false;
    }

}
