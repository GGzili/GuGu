import java.awt.geom.Arc2D;

public class Raindrop {// 定义 Raindrop 类
    private int x;// 雨滴的 x 坐标
    private int y;// 雨滴的 y 坐标
    private int speed;// 雨滴下落的速度
    private boolean falling;// 雨滴是否正在下落

    public Raindrop(int x, int y, int speed) {// 构造方法
        this.x = x;
        this.y = y;
        this.speed = speed;
        falling = true;
    }// 初始状态为正在下落

    public void fall() {
        y += speed;
    }// 下落方法

    public boolean isInside(Arc2D arc) {// 判断雨滴是否在圆内的方法
        return arc.contains(x + 5, y + 5); // 以圆心为判断点
    }

//    public boolean isInside(Rectangle rectangle) {// 判断雨滴是否在矩形内的方法，此方法被注释掉，不再使用
//        return rectangle.contains(x + 5, y + 5); // 以圆心为判断点
//    }

    public boolean isFalling() {
        return falling;
    }// 获取雨滴下落状态的方法

    public void setFalling(boolean falling) {
        this.falling = falling;
    }// 设置雨滴下落状态的方法

    public int getX() {
        return x;
    }// 获取雨滴 x 坐标的方法

    public int getY() {
        return y;
    }// 获取雨滴 y 坐标的方法
}