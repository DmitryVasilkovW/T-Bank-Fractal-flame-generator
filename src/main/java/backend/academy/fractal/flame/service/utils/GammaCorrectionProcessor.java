package backend.academy.fractal.flame.service.utils;

import backend.academy.fractal.flame.model.Pixel;

public class GammaCorrectionProcessor implements ImageProcessor {

    private final double gamma;

    public GammaCorrectionProcessor(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public void process(FractalImage image) {
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel pixel = image.pixel(x, y);
                int correctedValue = (int) (255 * Math.pow(pixel.hitCount() / 255.0, 1.0 / gamma));
                pixel.setRGB(correctedValue, correctedValue, correctedValue);
            }
        }
    }
}

