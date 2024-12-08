package backend.academy.fractal.flame.service.color;

import java.awt.Color;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class NeonTheme {
    private static final Random RANDOM = new Random();

    private static final int MIN_RED = 131;
    private static final int MAX_RED = 215;
    private static final int MIN_GREEN = 77;
    private static final int MAX_GREEN = 239;
    private static final int MIN_BLUE = 226;
    private static final int MAX_BLUE = 246;

    public static Color getRandomColor() {
        return new Color(
            RANDOM.nextInt(MIN_RED, MAX_RED),
            RANDOM.nextInt(MIN_GREEN, MAX_GREEN),
            RANDOM.nextInt(MIN_BLUE, MAX_BLUE)
        );
    }
}
