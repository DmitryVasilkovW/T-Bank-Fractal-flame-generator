package backend.academy.fractal.flame.service;

import backend.academy.fractal.flame.model.ImageFormat;
import backend.academy.fractal.flame.model.Pixel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public final class ImageUtils {

    private ImageUtils() {
    }

    public static void save(FractalImage image, Path filename, ImageFormat format) throws IOException {
        BufferedImage bufferedImage =
            new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Pixel pixel = image.pixel(x, y);
                int rgb = (pixel.getHitCount() << 16) | (pixel.getHitCount() << 8) | pixel.getHitCount();
                bufferedImage.setRGB(x, y, rgb);
            }
        }

        ImageIO.write(bufferedImage, format.name().toLowerCase(), filename.toFile());
    }
}
