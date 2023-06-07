import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class UmbrellaPanel extends JPanel {// 定义 UmbrellaPanel 类
    private ArrayList<Raindrop> raindrops;// 雨滴集合
    private RainGenerator rainGenerator;// 雨滴生成器
    private int panelWidth; // 面板宽度
    private int panelHeight;// 面板高度
    private int umbrellaX;// 雨伞的 x 坐标
    private int umbrellaY;// 雨伞的 y 坐标
    private int umbrellaWidth;// 雨伞的宽度
    private int umbrellaHeight;// 雨伞的高度

    public UmbrellaPanel(ArrayList<Rectangle> umbrellas, RainGenerator rainGenerator,
                         int panelWidth, int panelHeight) {// 构造方法
        // 初始化变量
        this.rainGenerator = rainGenerator;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.raindrops = rainGenerator.getRaindrops(); // 将 Raindrop 对象添加到 raindrops 中
        this.umbrellaWidth = 80;
        this.umbrellaHeight = 80;
        this.umbrellaX = (panelWidth - umbrellaWidth) / 2;
        this.umbrellaY = panelHeight - umbrellaHeight - 10;
        // 添加鼠标事件监听器
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                moveUmbrella(e.getX() - umbrellaWidth / 2, e.getY() - umbrellaHeight / 2);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                updateUmbrellaStatus();
            }
        });
        // 添加键盘事件监听器
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        moveUmbrella(umbrellaX, umbrellaY - 10);
                        break;
                    case KeyEvent.VK_DOWN:
                        moveUmbrella(umbrellaX, umbrellaY + 10);
                        break;
                    case KeyEvent.VK_LEFT:
                        moveUmbrella(umbrellaX - 10, umbrellaY);
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveUmbrella(umbrellaX + 10, umbrellaY);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                updateUmbrellaStatus();
            }
        });

        // 设置面板的首选尺寸，设置为可获得焦点状态并获取焦点
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setFocusable(true);
        grabFocus();
    }
    // 移动雨伞方法
    private void moveUmbrella(int x, int y) {
        umbrellaX = Math.min(Math.max(x, 0), panelWidth - umbrellaWidth);
        umbrellaY = Math.min(Math.max(y, 0), panelHeight - umbrellaHeight);
        repaint();
    }
    // 重写 paintComponent 方法
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 绘制背景渐变色
        Graphics2D g2d = (Graphics2D) g;
        Color color1 = new Color(8, 8, 78);
        Color color2 = new Color(16, 16, 183);
        g2d.setPaint(new GradientPaint(0, 0, color1, 0, panelHeight, color2));
        g2d.fillRect(0, 0, panelWidth, panelHeight);

        // 绘制雨伞
        g2d.setColor(Color.white);
        g2d.fillArc(umbrellaX, umbrellaY, umbrellaWidth, umbrellaHeight, 0, 180);
        g2d.drawLine(umbrellaX, umbrellaY + umbrellaHeight / 2, umbrellaX + umbrellaWidth / 2, umbrellaY);
        g2d.drawLine(umbrellaX + umbrellaWidth, umbrellaY + umbrellaHeight / 2, umbrellaX + umbrellaWidth / 2, umbrellaY);

        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(3)); // 设置线条粗度为 3
        g2d.drawLine(umbrellaX, umbrellaY + umbrellaHeight / 2, umbrellaX + umbrellaWidth, umbrellaY + umbrellaHeight / 2);
        g2d.drawLine(umbrellaX + umbrellaWidth / 2, umbrellaY + umbrellaHeight / 2, umbrellaX + umbrellaWidth / 2, umbrellaY + umbrellaHeight);

        // 绘制雨滴
        synchronized (raindrops) {
            for (Raindrop raindrop : raindrops) {
                if (raindrop.isFalling()) {
                    g2d.setColor(Color.white);
                    g2d.fillRect(raindrop.getX(), raindrop.getY(), 1, 5);
                }
            }
        }
    }

    // 获取雨伞弧线对象的方法
    public Arc2D.Double getUmbrellaArc() {
        return new Arc2D.Double(umbrellaX, umbrellaY, umbrellaWidth, umbrellaHeight, 0, 180, Arc2D.OPEN);
    }
//    public Rectangle getUmbrellaRectangle() {// 获取雨伞矩形对象的方法，此方法被注释掉，不再使用
//        return new Rectangle(umbrellaX + 20, umbrellaY, umbrellaWidth - 40, umbrellaHeight / 2);
//    }
    // 更新雨伞状态的方法
    public void updateUmbrellaStatus(){
        synchronized (raindrops) {
            for (Raindrop raindrop : raindrops) {
                if (raindrop.isInside(getUmbrellaArc())) {
                //if (raindrop.isInside(getUmbrellaRectangle())) {
                    raindrop.setFalling(false);// 当雨滴进入雨伞区域内，设置其下落状态为 false，即不再下落
                }
            }
        }
        repaint();// 重新绘制面板
    }

    public void startRain(){
        Thread t = new Thread(() -> {
            while (true) {
                rainGenerator.addRaindrop();// 向雨滴集合中添加新的雨滴
                rainGenerator.updateRaindrops(getUmbrellaArc());// 更新雨滴状态
//                rainGenerator.updateRaindrops(getUmbrellaRectangle());
                updateUmbrellaStatus();// 更新雨伞状态
                try {
                    Thread.sleep(10);// 休眠 10 毫秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}

