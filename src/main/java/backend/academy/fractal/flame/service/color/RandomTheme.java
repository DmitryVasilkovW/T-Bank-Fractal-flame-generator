package backend.academy.fractal.flame.service.color;

import java.awt.Color;
import java.util.Random;

public class RandomTheme {
    private static final Random random = new Random();
    private static final int MAX_COLOR = 256;

    public static Color getRandomColor() {
        return new Color(
            random.nextInt(MAX_COLOR),
            random.nextInt(MAX_COLOR),
            random.nextInt(MAX_COLOR)
        );
    }
}
