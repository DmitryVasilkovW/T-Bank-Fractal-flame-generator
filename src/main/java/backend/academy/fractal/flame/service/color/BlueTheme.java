package backend.academy.fractal.flame.service.color;

import java.awt.Color;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BlueTheme {
    private static final Random RANDOM = new Random();
    private static final int MAX_COLOR = 256;

    private static final int MIN_RED = 0;
    private static final int MAX_RED = 50;
    private static final int MIN_GREEN = 0;
    private static final int MAX_GREEN = 80;
    private static final int MIN_BLUE = 200;

    public static Color getRandomColor() {
        return new Color(
            RANDOM.nextInt(MIN_RED, MAX_RED),
            RANDOM.nextInt(MIN_GREEN, MAX_GREEN),
            RANDOM.nextInt(MIN_BLUE, MAX_COLOR)
        );
    }
}
