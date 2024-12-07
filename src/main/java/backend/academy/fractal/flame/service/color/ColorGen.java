package backend.academy.fractal.flame.service.color;

import backend.academy.fractal.flame.model.enums.ColorTheme;
import java.awt.Color;
import java.util.Random;

public record ColorGen(
    Color color
) {
    private static final int MAX_COLOR = 256;
    private static final Random random = new Random();

    public static ColorGen createRandomAffineCoefficient(ColorTheme color) {
        switch (color){
            case NEON -> {
                return new ColorGen(
                    new Color(
                        random.nextInt(131,215),
                        random.nextInt(77, 239),
                        random.nextInt(226,246)
                    )
                );
            }
            case PINK -> {
                return new ColorGen(
                    new Color(
                        random.nextInt(204,255),
                        random.nextInt(102,211),
                        random.nextInt(249, MAX_COLOR)
                    )
                );
            }
            case ORANGE -> {
                return new ColorGen(
                    new Color(
                        random.nextInt(200, MAX_COLOR),
                        random.nextInt(57, 86),
                        random.nextInt(0, 26)
                    )
                );
            }
            case AZURE -> {
                return new ColorGen(
                    new Color(
                        random.nextInt(0, 100),
                        random.nextInt(150, 255),
                        random.nextInt(200, MAX_COLOR)
                    )
                );
            }
            case BLUE -> {
                return new ColorGen(
                    new Color(
                        random.nextInt(0, 50),
                        random.nextInt(0, 80),
                        random.nextInt(200, MAX_COLOR)
                    )
                );
            }
            default -> {
                return new ColorGen(
                    new Color(
                        random.nextInt(MAX_COLOR),
                        random.nextInt(MAX_COLOR),
                        random.nextInt(MAX_COLOR)
                    )
                );
            }
        }
    }
}
