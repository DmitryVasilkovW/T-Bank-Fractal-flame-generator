package backend.academy.fractal.flame.service.color;

import java.awt.Color;
import java.util.Random;

public class AzureTheme {
    private static final Random random = new Random();
    private static final int MAX_COLOR = 256;

    public static Color getRandomColor() {
        return new Color(
            random.nextInt(0, 100),
            random.nextInt(150, 255),
            random.nextInt(200, MAX_COLOR)
        );
    }
}
