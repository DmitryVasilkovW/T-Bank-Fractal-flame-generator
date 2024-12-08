package backend.academy.fractal.flame.model.records;

import java.util.Random;

public record AffineCoefficient(
    double a,
    double b,
    double c,
    double d,
    double e,
    double f
) {
    private static final Random RANDOM = new Random();

    public static AffineCoefficient createRandomAffineCoefficient() {
        double a = RANDOM.nextDouble(-1, 1);
        double b = RANDOM.nextDouble(-1, 1);
        double c = RANDOM.nextDouble(-1, 1);
        double d = RANDOM.nextDouble(-1, 1);
        double e = RANDOM.nextDouble(-1, 1);
        double f = RANDOM.nextDouble(-1, 1);

        while (!isAffineCoefficientValid(a, b, d, e)) {
            a = RANDOM.nextDouble(-1, 1);
            b = RANDOM.nextDouble(-1, 1);
            d = RANDOM.nextDouble(-1, 1);
            e = RANDOM.nextDouble(-1, 1);
        }

        return new AffineCoefficient(a, b, c, d, e, f);
    }

    private static boolean isAffineCoefficientValid(double a, double b, double d, double e) {
        return ((a * a + d * d) < 1) && ((b * b - e * e) < 1)
            && ((a * a + b * b + d * d + e * e) < (1 + (a * e - b * d) * (a * e - b * d)));
    }
}
