package backend.academy.fractal.flame.model.records;

import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.impl.AffineTransformation;
import java.awt.Color;
import java.util.List;
import java.util.Optional;

public record RenderTaskConfig(
    int width,
    int height,
    Rect world,
    List<Transformation> variations,
    Optional<List<AffineTransformation>> affineTransformations,
    Optional<List<Color>> colors,
    int samples,
    short iterPerSample
) {
}
