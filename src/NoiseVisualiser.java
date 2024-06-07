import javax.swing.*;
import java.awt.*;

/**
 * Class for visualising changes in noise values over time. Time is interpreted as z coordinate of the noise.
 */
public class NoiseVisualiser extends JFrame implements Runnable {
    private static final int FRAMES_PER_SECOND = 20;
    private static final int TIMEOUT_PER_FRAME = 1000 / FRAMES_PER_SECOND;
    private final NoisePainter painter;
    private final int width;
    private final int height;
    private final float speed;
    private float t = 0;

    /**
     * Creates new noise visualiser for given noise that will produce the animation in given dimensions with given speed.
     *
     * @param noise  noise to visualise
     * @param width  width of the animation
     * @param height height of the animation
     * @param speed  speed of changing the z coordinate each second
     */
    public NoiseVisualiser(final Noise noise, final int width, final int height, final float speed) {
        this.painter = new NoisePainter(noise, Color.BLACK, Color.RED);
        this.width = width;
        this.height = height;
        this.speed = speed / FRAMES_PER_SECOND;
        setVisible(true);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Creates new noise visualiser for given noise that will produce the animation in given dimensions.
     * The z coordinate will increase by 1 every second.
     *
     * @param noise  noise to visualise
     * @param width  width of the animation
     * @param height height of the animation
     */
    public NoiseVisualiser(final Noise noise, final int width, final int height) {
        this(noise, width, height, 1);
    }

    /**
     * Starts window application that animates noise over time.
     */
    @Override
    public void run() {
        try {
            //Quick-and-dirty solution. TODO: fix
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
