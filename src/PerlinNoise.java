import java.util.Random;

/**
 * An implementation of {@link <a href="https://en.wikipedia.org/wiki/Perlin_noise">Perlin noise algorithm</a>}.
 */
public class PerlinNoise implements Noise {
    private static final int PERMUTATION_SIZE = 1 << 8;
    private final int[] permutation;

    private PerlinNoise(final Random random) {
        permutation = shuffle(random);
    }

    /**
     * Creates new noise with given seed
     *
     * @param seed noise seed
     */
    public PerlinNoise(final long seed) {
        this(new Random(seed));
    }

    /**
     * Creates new noise with random seed
     */
    public PerlinNoise() {
        this(new Random());
    }

    private int[] shuffle(final Random random) {
        final int[] arr = new int[PERMUTATION_SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = arr.length - 1; i > 0; i--) {
            final int index = random.nextInt(i + 1);
            final int tmp = arr[index];
            arr[index] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    private int random(final int x) {
        return permutation[x & (PERMUTATION_SIZE - 1)];
    }

    private int random(final int x, final int y, final int z) {
        return random(z ^ random(y ^ random(x)));
    }

    private float multiplyRandomGradient(
            final float xf, final float yf, final float zf,
            final int xi, final int yi, final int zi
    ) {
        final float dx = xf - xi;
        final float dy = yf - yi;
        final float dz = zf - zi;

        final int i = random(xi, yi, zi) % 12;
        final boolean bit0 = (i & 1) != 0;
        final boolean bit1 = (i >> 1 & 1) != 0;
        final float result = switch (i >> 2) {
            case 0 -> dx + (bit0 ? dy : -dy);
            case 1 -> dy + (bit0 ? dz : -dz);
            case 2 -> dz + (bit0 ? dx : -dx);
            default -> throw new AssertionError("unreachable");
        };
        return bit1 ? result : -result;
    }

    private float interpolate(final float a, final float b, final float t) {
        return a + (b - a) * t;
    }

    private float quinticCurve(final float t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private boolean valid(final float f) {
        return f < Integer.MAX_VALUE && f > Integer.MIN_VALUE;
    }

    @Override
    public float valueAt(final float x, final float y, final float z) {
        if (!valid(x) || !valid(y) || !valid(z)) {
            throw new IllegalArgumentException("Float argument exceeds integer boundaries");
        }
        final int x0 = (int) Math.floor(x);
        final int y0 = (int) Math.floor(y);
        final int z0 = (int) Math.floor(z);

        final float tx = quinticCurve(x - x0);
        final float ty = quinticCurve(y - y0);
        final float tz = quinticCurve(z - z0);

        final float x0y0 = interpolate(
                multiplyRandomGradient(x, y, z, x0, y0, z0),
                multiplyRandomGradient(x, y, z, x0, y0, z0 + 1),
                tz
        );
        final float x0y1 = interpolate(
                multiplyRandomGradient(x, y, z, x0, y0 + 1, z0),
                multiplyRandomGradient(x, y, z, x0, y0 + 1, z0 + 1),
                tz
        );
        final float x1y0 = interpolate(
                multiplyRandomGradient(x, y, z, x0 + 1, y0, z0),
                multiplyRandomGradient(x, y, z, x0 + 1, y0, z0 + 1),
                tz
        );
        final float x1y1 = interpolate(
                multiplyRandomGradient(x, y, z, x0 + 1, y0 + 1, z0),
                multiplyRandomGradient(x, y, z, x0 + 1, y0 + 1, z0 + 1),
                tz
        );

        return interpolate(
                interpolate(x0y0, x0y1, ty),
                interpolate(x1y0, x1y1, ty),
                tx
        );
    }
}
