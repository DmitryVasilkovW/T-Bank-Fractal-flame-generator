package backend.academy.fractal.flame.service.color;

import java.awt.Color;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomTheme {
    private static final Random RANDOM = new Random();
    private static final int MAX_COLOR = 256;

    public static Color getRandomColor() {
        return new Color(
            RANDOM.nextInt(MAX_COLOR),
            RANDOM.nextInt(MAX_COLOR),
            RANDOM.nextInt(MAX_COLOR)
        );
    }
}
