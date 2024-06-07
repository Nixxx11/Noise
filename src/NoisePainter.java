import java.awt.*;
import java.awt.image.BufferedImage;

public class NoisePainter {
    private static final int DEFAULT_SCALE = 50;
    private final Noise noise;
    private final ColorRGBA color0;
    private final ColorRGBA color1;

    public NoisePainter(final Noise noise, final Color color0, final Color color1) {
        this.noise = noise;
        this.color0 = new ColorRGBA(color0);
        this.color1 = new ColorRGBA(color1);
    }

    public NoisePainter(final Noise noise) {
        this(noise, Color.BLACK, Color.WHITE);
    }

    public BufferedImage paint(final int width, final int height, final float z, final float scale) {
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final float step = 1 / scale;
        float xf = 0;
        for (int x = 0; x < width; x++) {
            xf += step;
            float yf = 0;
            for (int y = 0; y < height; y++) {
                yf += step;
                final float noiseValue = noise.valueAt(xf, yf, z);
                final float t = (noiseValue + 1) / 2;
                image.setRGB(x, y, new ColorRGBA(
                        interpolate(color0.r(), color1.r(), t),
                        interpolate(color0.g(), color1.g(), t),
                        interpolate(color0.b(), color1.b(), t),
                        interpolate(color0.a(), color1.a(), t)
                ).getIntValue());
            }
        }
        return image;
    }

    public BufferedImage paint(final int width, final int height, final float z) {
        return paint(width, height, z, DEFAULT_SCALE);
    }

    public BufferedImage paint(final int width, final int height) {
        return paint(width, height, 0, DEFAULT_SCALE);
    }

    private int interpolate(final int color0, final int color1, final float t) {
        return color0 + Math.round((color1 - color0) * t);
    }
}
