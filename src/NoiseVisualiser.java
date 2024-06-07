import javax.swing.*;
import java.awt.*;

public class NoiseVisualiser extends JFrame implements Runnable {
    private static final int TIMEOUT_PER_FRAME = 50;
    private final NoisePainter painter;
    private final int width;
    private final int height;
    private final float speed;
    private float t = 0;

    public NoiseVisualiser(final Noise noise, final int width, final int height, final float speed) {
        this.painter = new NoisePainter(noise, Color.BLACK, Color.RED);
        this.width = width;
        this.height = height;
        this.speed = speed / 20;
        setVisible(true);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        try {
            while (true) {
                repaint();
                t += speed;
                Thread.sleep(TIMEOUT_PER_FRAME);
            }
        } catch (final InterruptedException ignored) {
        }
    }

    @Override
    public void paint(final Graphics g) {
        g.drawImage(painter.paint(width, height, t), 0, 0, this);
    }
}
