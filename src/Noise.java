/**
 * An interface representing procedural noise.
 */
public interface Noise {
    /**
     * Returns noise value between -1 and 1 at the given point.
     * Multiple calls with same arguments will return the same value.
     * Moreover, noise value is continuous.
     * That is, close points will have close noise values.
     *
     * @param x x coordinate of the point
     * @param y y coordinate of the point
     * @param z z coordinate of the point
     * @return noise value between -1 and 1
     */
    float valueAt(float x, float y, float z);

    /**
     * Returns noise value between -1 and 1 at the given point.
     * Multiple calls with same arguments will return the same value.
     * Moreover, noise value is continuous.
     * That is, close points will have close noise values.
     *
     * @param x x coordinate of the point
     * @param y y coordinate of the point
     * @return noise value between -1 and 1
     */
    default float valueAt(final float x, final float y) {
        return valueAt(x, y, 0);
    }

    /**
     * Returns noise value between -1 and 1 at the given point.
     * Multiple calls with same arguments will return the same value.
     * Moreover, noise value is continuous.
     * That is, close points will have close noise values.
     *
     * @param x x coordinate of the point
     * @return noise value between -1 and 1
     */
    default float valueAt(final float x) {
        return valueAt(x, 0, 0);
    }
}
