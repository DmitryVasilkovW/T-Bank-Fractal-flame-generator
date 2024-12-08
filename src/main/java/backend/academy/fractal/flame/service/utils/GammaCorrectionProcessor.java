package backend.academy.fractal.flame.service.utils;

import backend.academy.fractal.flame.model.Pixel;

public class GammaCorrectionProcessor implements ImageProcessor {
    private final double gamma;

    private final static int GAMMA_COLOR = 255;

    public GammaCorrectionProcessor(double gamma) {
        this.gamma = gamma;
    }

    public void process(FractalImage fractalImage) {
        double max = 0;

        for (int x = 0; x < fractalImage.data().length; x++) {
            for (int y = 0; y < fractalImage.data()[x].length; y++) {
                if (fractalImage.data()[x][y].hitCount() != 0) {
                    fractalImage.data()[x][y].normal(Math.log10(fractalImage.data()[x][y].hitCount()));
                    max = Math.max(max, fractalImage.data()[x][y].normal());
                }
            }
        }

        for (int x = 0; x < fractalImage.data().length; x++) {
            for (int y = 0; y < fractalImage.data()[x].length; y++) {
                Pixel pixel = fractalImage.data()[x][y];
                pixel.normal(pixel.normal() / max);

                int red = (int) (pixel.color().getRed() * Math.pow(pixel.normal(), 1.0 / gamma));
                int green = (int) (pixel.color().getGreen() * Math.pow(pixel.normal(), 1.0 / gamma));
                int blue = (int) (pixel.color().getBlue() * Math.pow(pixel.normal(), 1.0 / gamma));

                pixel.setRGB(
                    Math.min(GAMMA_COLOR, Math.max(0, red)),
                    Math.min(GAMMA_COLOR, Math.max(0, green)),
                    Math.min(GAMMA_COLOR, Math.max(0, blue))
                );
            }
        }
    }
}

