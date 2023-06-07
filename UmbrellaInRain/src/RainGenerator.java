import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.Random;
// 定义 RainGenerator 类
public class RainGenerator {
    private ArrayList<Raindrop> raindrops;// 雨滴集合
    private int frameWidth;// 定义 RainGenerator 类
    private int frameHeight;// 面板高度
    // 构造方法
    public RainGenerator(ArrayList<Raindrop> raindrops, int frameWidth, int frameHeight) {
        this.raindrops = raindrops;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }
    // 添加 getRaindrops() 方法
    public ArrayList<Raindrop> getRaindrops() {
        return raindrops;
    }
    // 添加新雨滴的方法
    public void addRaindrop() {
        int x = new Random().nextInt(frameWidth - 10); // 保证雨滴不会超出面板
        int y = -20; // 从上方开始落下
        int speed = new Random().nextInt(10) + 5; // 速度在 5~14 之间随机
        int density = new Random().nextInt(4) + 1; // 雨滴密度在 1~4 之间随机
        for (int i = 0; i < density; i++) {
            int x_offset = new Random().nextInt(10) - 0; // 位置随机偏移不超过 10 个像素
            int y_offset = new Random().nextInt(100) - 5;
            raindrops.add(new Raindrop(x + x_offset, y + y_offset, speed));
        }
    }
    // 更新雨滴状态的方法
    public void updateRaindrops(Arc2D umbrellaArc) {
        synchronized (raindrops) {
            for (Raindrop raindrop : raindrops) {
                if (raindrop.isFalling()) {
                    raindrop.fall();// 雨滴下落
                    if (raindrop.getY() >= frameHeight - 10) {// 判断是否落地
                        raindrop.setFalling(false);// 设置雨滴下落状态为 false，即不再下落

                    }
                    if (raindrop.isInside(umbrellaArc)) { // 判断是否碰撞到雨伞

                        raindrop.setFalling(false);// 设置雨滴下落状态为 false，即不再下落
                    }
                }
            }
        }
    }
//    public void updateRaindrops(Rectangle umbrellaRectangle) {
//        synchronized (raindrops) {
//            for (Raindrop raindrop : raindrops) {
//                if (raindrop.isFalling()) {
//                    raindrop.fall();
//                    if (raindrop.getY() >= frameHeight - 10) {
//                        raindrop.setFalling(false);
//                    }
//                    if (raindrop.isInside(umbrellaRectangle)) {
//                        raindrop.setFalling(false);
//                    }
//                }
//            }
//        }
//    }
}