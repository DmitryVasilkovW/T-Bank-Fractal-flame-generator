package backend.academy.fractal.flame.service.util;

import backend.academy.fractal.flame.model.Pixel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GammaCorrectionProcessorTest {

    @Test
    void testGammaCorrectionProcessor() {
        FractalImage fractalImage = FractalImage.create(5, 5);
        GammaCorrectionProcessor processor = new GammaCorrectionProcessor(2.2);

        processor.process(fractalImage);

        Pixel pixel = fractalImage.data()[0][0];
        int red = pixel.color().getRed();
        int green = pixel.color().getGreen();
        int blue = pixel.color().getBlue();

        assertTrue(red >= 0 && red <= 255);
        assertTrue(green >= 0 && green <= 255);
        assertTrue(blue >= 0 && blue <= 255);
    }
}
