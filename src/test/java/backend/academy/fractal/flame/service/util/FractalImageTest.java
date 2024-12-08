package backend.academy.fractal.flame.service.util;

import backend.academy.fractal.flame.model.Pixel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FractalImageTest {

    @Test
    void testCreate() {
        FractalImage image = FractalImage.create(5, 5);
        assertNotNull(image);
        assertEquals(5, image.width());
        assertEquals(5, image.height());
    }

    @Test
    void testContains() {
        FractalImage image = FractalImage.create(5, 5);

        assertTrue(image.contains(0, 0));
        assertFalse(image.contains(5, 5));
    }

    @Test
    void testPixel() {
        FractalImage image = FractalImage.create(5, 5);
        Pixel pixel = image.pixel(0, 0);

        assertNotNull(pixel);
        assertEquals(0, pixel.getR());
        assertEquals(0, pixel.getG());
        assertEquals(0, pixel.getB());
    }

    @Test
    void testApplySymmetryHorizontal() {
        FractalImage image = FractalImage.create(5, 5);
        image.applySymmetry(true);

        assertEquals(image.pixel(0, 4), image.pixel(0, 0));
        assertEquals(image.pixel(1, 4), image.pixel(1, 0));
    }

    @Test
    void testApplySymmetryVertical() {
        FractalImage image = FractalImage.create(5, 5);
        image.applySymmetry(false);

        assertEquals(image.pixel(4, 0), image.pixel(0, 0));
        assertEquals(image.pixel(4, 1), image.pixel(0, 1));
    }

    @Test
    void testMerge() {
        FractalImage image1 = FractalImage.create(5, 5);
        FractalImage image2 = FractalImage.create(5, 5);

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                image1.pixel(x, y).setRGB(255, 0, 0);
                image2.pixel(x, y).setRGB(0, 255, 0);
            }
        }

        image1.merge(image2);

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                Pixel pixel = image1.pixel(x, y);
                assertEquals(255, pixel.getR());
                assertEquals(255, pixel.getG());
                assertEquals(0, pixel.getB());
            }
        }
    }
}
