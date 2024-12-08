package backend.academy.fractal.flame.service.utils;

import backend.academy.fractal.flame.model.enums.ImageFormat;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.imageio.ImageIO;
import backend.academy.fractal.flame.model.Pixel;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ImageUtils {
    public static void saveColorfulImage(FractalImage fractalImage, Path filename, ImageFormat format) {
        BufferedImage image = new BufferedImage(
            fractalImage.width(),
            fractalImage.height(),
            BufferedImage.TYPE_INT_RGB
        );
        for (int i = 0; i < fractalImage.data().length; i++) {
            for (int j = 0; j < fractalImage.data()[i].length; j++) {
                image.setRGB(j, i, fractalImage.data()[i][j].color().getRGB());
            }
        }
        try {
            var outputStream = Files.newOutputStream(
                filename,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
            ImageIO.write(image, format.name(), outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveBlackAndWhiteImage(FractalImage fractalImage, Path filename, ImageFormat format) {
        BufferedImage bufferedImage =
            new BufferedImage(fractalImage.width(), fractalImage.height(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < fractalImage.height(); y++) {
            for (int x = 0; x < fractalImage.width(); x++) {
                Pixel pixel = fractalImage.pixel(x, y);
                int rgb = (pixel.hitCount() << 16) | (pixel.hitCount() << 8) | pixel.hitCount();
                bufferedImage.setRGB(x, y, rgb);
            }
        }
        try {
            var outputStream = Files.newOutputStream(
                filename,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
            ImageIO.write(bufferedImage, format.name().toLowerCase(), outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
