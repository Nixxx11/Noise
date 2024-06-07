import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class for representing noise values as an image.
 */
public class NoisePainter {
    private static final int DEFAULT_SCALE = 50;
    private final Noise noise;
    private final ColorRGBA color0;
    private final ColorRGBA color1;

    /**
     * Creates new noise painter for given noise that will draw the image in given colors.
     *
     * @param noise  noise to paint
     * @param color0 color for negative noise values
     * @param color1 color for positive noise values
     */
    public NoisePainter(final Noise noise, final Color color0, final Color color1) {
        this.noise = noise;
        this.color0 = new ColorRGBA(color0);
        this.color1 = new ColorRGBA(color1);
    }

    /**
     * Creates new noise painter for given noise that will draw the image in black and white colors.
     *
     * @param noise noise to paint
     */
    public NoisePainter(final Noise noise) {
        this(noise, Color.BLACK, Color.WHITE);
    }

    /**
     * Returns an image with given dimensions representing noise values at given z coordinate.
     * Scale represents amount of pixels per unit in x and y coordinate.
     *
     * @param width  width of the resulting image
     * @param height height of the resulting image
     * @param z      z coordinate for noise
     * @param scale  amount of pixels per unit in x and y coordinate
     * @return image representing the noise at given z coordinate
     */
    public BufferedImage paint(final int width, final int height, final float z, final float scale) {
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final float step = 1 / scale;
        for (int x = 0; x < width; x++) {
            final float xf = x * step;
            for (int y = 0; y < height; y++) {
                final float yf = y * step;
                final float noiseValue = noise.valueAt(xf, yf, z);
                final float t = (noiseValue + 1) / 2;
                final ColorRGBA color = new ColorRGBA(
                        interpolate(color0.r(), color1.r(), t),
                        interpolate(color0.g(), color1.g(), t),
                        interpolate(color0.b(), color1.b(), t),
                        interpolate(color0.a(), color1.a(), t)
                );
                image.setRGB(x, y, color.getIntValue());
            }
        }
        return image;
    }

    /**
     * Returns an image with given dimensions representing noise values at given z coordinate.
     * The image will have 50 pixels per uint in x and y coordinate.
     *
     * @param width  width of the resulting image
     * @param height height of the resulting image
     * @param z      z coordinate for noise
     * @return image representing the noise at given z coordinate
     */
    public BufferedImage paint(final int width, final int height, final float z) {
        return paint(width, height, z, DEFAULT_SCALE);
    }

    /**
     * Returns an image with given dimensions representing noise values at z=0.
     * The image will have 50 pixels per uint in x and y coordinate.
     *
     * @param width  width of the resulting image
     * @param height height of the resulting image
     * @return image representing the noise at given z coordinate
     */
    public BufferedImage paint(final int width, final int height) {
        return paint(width, height, 0, DEFAULT_SCALE);
    }

    private int interpolate(final int color0, final int color1, final float t) {
        return color0 + Math.round((color1 - color0) * t);
    }
}
