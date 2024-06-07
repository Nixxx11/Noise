import java.awt.*;
import java.awt.image.BufferedImage;

record ColorRGBA(int r, int g, int b, int a) {
    ColorRGBA(final Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    /**
     * Returns int value of this color, as specified by {@link BufferedImage#TYPE_INT_ARGB}
     * @return int value of this color
     */
    public int getIntValue() {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
