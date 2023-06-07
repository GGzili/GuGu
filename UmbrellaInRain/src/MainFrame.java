import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
    private int frameWidth;
    private int frameHeight;

    private UmbrellaPanel umbrellaPanel;

    public MainFrame(int frameWidth, int frameHeight) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        setTitle("雨中雨伞模拟");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        umbrellaPanel = new UmbrellaPanel(new ArrayList<>(), new RainGenerator(new ArrayList<>(), frameWidth, frameHeight),
                frameWidth, frameHeight);

        add(umbrellaPanel, BorderLayout.CENTER);

        // 开始雨滴生成和更新线程
        umbrellaPanel.startRain();  // 启动雨滴产生和更新线程
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame(600, 800);
    }
}