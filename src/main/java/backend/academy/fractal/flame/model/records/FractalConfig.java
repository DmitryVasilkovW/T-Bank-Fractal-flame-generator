package backend.academy.fractal.flame.model.records;

import backend.academy.fractal.flame.service.transformation.Transformation;
import java.awt.Color;
import java.util.List;
import java.util.Optional;

public record FractalConfig(
    List<Transformation> variations,
    Optional<List<Color>> colors,
    int degreeOfRandomnessOfFractalCreation,
    boolean complicateFlameShape,
    short iterPerSample,
    int samples
) {
}
