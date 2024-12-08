package backend.academy.fractal.flame.service.color;

import java.awt.Color;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrangeTheme {
    private static final Random RANDOM = new Random();
    private static final int MAX_COLOR = 256;

    private static final int MIN_RED = 200;
    private static final int MIN_GREEN = 57;
    private static final int MAX_GREEN = 86;
    private static final int MIN_BLUE = 0;
    private static final int MAX_BLUE = 26;

    public static Color getRandomColor() {
        return new Color(
            RANDOM.nextInt(MIN_RED, MAX_COLOR),
            RANDOM.nextInt(MIN_GREEN, MAX_GREEN),
            RANDOM.nextInt(MIN_BLUE, MAX_BLUE)
        );
    }
}
