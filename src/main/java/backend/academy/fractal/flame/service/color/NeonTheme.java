package backend.academy.fractal.flame.service.color;

import java.awt.Color;
import java.util.Random;

public class NeonTheme {
    private static final Random random = new Random();

    public static Color getRandomColor() {
        return new Color(
            random.nextInt(131,215),
            random.nextInt(77, 239),
            random.nextInt(226,246)
        );
    }
}
