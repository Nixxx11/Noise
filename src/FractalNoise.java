public class FractalNoise implements Noise {
    private static final int DEFAULT_OCTAVES = 4;
    private static final float DEFAULT_FADE = 0.5f;
    private final Noise noise;
    private final int octaves;
    private final float fade;

    public FractalNoise(final Noise noise, final int octaves, final float fade) {
        this.noise = noise;
        this.octaves = octaves;
        this.fade = fade;
    }

    public FractalNoise(final Noise noise) {
        this(noise, DEFAULT_OCTAVES, DEFAULT_FADE);
    }

    @Override
    public float valueAt(float x, float y, float z) {
        float result = 0;
        float max = 0;
        float multiplier = fade;

        for (int i = 0; i < octaves; i++) {
            result += noise.valueAt(x, y, z) * multiplier;
            max += multiplier;
            multiplier *= fade;
            x *= 2;
            y *= 2;
            z *= 2;
        }
        return result / max;
    }
}
