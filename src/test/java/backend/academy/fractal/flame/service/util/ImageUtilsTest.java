package backend.academy.fractal.flame.service.util;

import backend.academy.fractal.flame.model.Pixel;
import backend.academy.fractal.flame.model.enums.ImageFormat;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImageUtilsTest {

    @Test
    void testSaveColorfulImage() throws IOException {
        FractalImage fractalImage = FractalImage.create(5, 5);
        Path filename = Path.of("testColorfulImage.png");
        ImageUtils.saveColorfulImage(fractalImage, filename, ImageFormat.PNG);

        assertTrue(Files.exists(filename));

        BufferedImage image = ImageIO.read(filename.toFile());
        assertEquals(fractalImage.width(), image.getWidth());
        assertEquals(fractalImage.height(), image.getHeight());

        Pixel pixel = fractalImage.data()[0][0];
        assertEquals(pixel.color().getRGB(), image.getRGB(0, 0));

        Files.delete(filename);
    }

    @Test
    void testSaveBlackAndWhiteImage() throws IOException {
        FractalImage fractalImage = FractalImage.create(5, 5);
        Path filename = Path.of("testBlackAndWhiteImage.png");
        ImageUtils.saveBlackAndWhiteImage(fractalImage, filename, ImageFormat.PNG);

        assertTrue(Files.exists(filename));

        BufferedImage image = ImageIO.read(filename.toFile());
        assertEquals(fractalImage.width(), image.getWidth());
        assertEquals(fractalImage.height(), image.getHeight());

        Pixel pixel = fractalImage.data()[0][0];
        int expectedRGB = (pixel.hitCount() << 16) | (pixel.hitCount() << 8) | pixel.hitCount();

        int actualRGB = image.getRGB(0, 0) & 0xFFFFFF;
        assertEquals(expectedRGB, actualRGB);

        Files.delete(filename);
    }
}
