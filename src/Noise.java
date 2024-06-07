public interface Noise {
    float valueAt(float x, float y, float z);

    default float valueAt(final float x, final float y) {
        return valueAt(x, y, 0);
    }

    default float valueAt(final float x) {
        return valueAt(x, 0, 0);
    }
}
