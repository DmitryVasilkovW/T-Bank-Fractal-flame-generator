package backend.academy.fractal.flame.service.color;

import java.awt.Color;
import java.util.Random;

public class PinkTheme {
    private static final Random random = new Random();
    private static final int MAX_COLOR = 256;

    public static Color getRandomColor() {
        return new Color(
            random.nextInt(204,255),
            random.nextInt(102,211),
            random.nextInt(249, MAX_COLOR)
        );
    }
}
