package backend.academy.fractal.flame.model.records;

import backend.academy.fractal.flame.service.color.ColorGen;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.impl.AffineTransformation;
import java.util.List;
import java.util.Optional;

public record RenderTaskConfig(
    int width,
    int height,
    Rect world,
    List<Transformation> variations,
    Optional<List<AffineTransformation>> affineTransformations,
    Optional<List<ColorGen>> colors,
    int samples,
    short iterPerSample
) {
}
