package backend.academy.fractal.flame.service.color;

import java.awt.Color;
import java.util.Random;

public class OrangeTheme {
    private static final Random random = new Random();
    private static final int MAX_COLOR = 256;

    public static Color getRandomColor() {
        return new Color(
            random.nextInt(200, MAX_COLOR),
            random.nextInt(57, 86),
            random.nextInt(0, 26)
        );
    }
}
