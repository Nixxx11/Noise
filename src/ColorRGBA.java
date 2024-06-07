import java.awt.*;

record ColorRGBA(int r, int g, int b, int a) {
    ColorRGBA(final Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public int getIntValue() {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
