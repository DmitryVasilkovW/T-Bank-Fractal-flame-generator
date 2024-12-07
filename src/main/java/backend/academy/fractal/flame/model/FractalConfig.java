package backend.academy.fractal.flame.model;

import backend.academy.fractal.flame.service.transformation.Transformation;
import java.util.List;

public record FractalConfig(
    List<Transformation> variations,
    ColorTheme colorTheme,
    int degreeOfRandomnessOfFractalCreation,
    boolean complicateFlameShape,
    int colorDiversityIndex,
    short iterPerSample,
    int samples
) {
}
