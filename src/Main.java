public class Main {
    public static void main(final String[] args) {
        final NoiseVisualiser visualiser = new NoiseVisualiser(
                new PerlinNoise(),
                1080, 720, 1
        );
        visualiser.run();
    }
}
