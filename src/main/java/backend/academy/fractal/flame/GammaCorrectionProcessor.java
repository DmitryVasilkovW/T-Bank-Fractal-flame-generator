package backend.academy.fractal.flame;

public class GammaCorrectionProcessor implements ImageProcessor {

    private final double gamma;

    public GammaCorrectionProcessor(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public void process(FractalImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Pixel pixel = image.pixel(x, y);
                int correctedValue = (int) (255 * Math.pow(pixel.getHitCount() / 255.0, 1.0 / gamma));
                pixel.setRGB(correctedValue, correctedValue, correctedValue);
            }
        }
    }
}

