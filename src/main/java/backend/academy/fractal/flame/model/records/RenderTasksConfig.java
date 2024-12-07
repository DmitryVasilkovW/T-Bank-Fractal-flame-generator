package backend.academy.fractal.flame.model.records;

import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.impl.AffineTransformation;
import backend.academy.fractal.flame.service.utils.FractalImage;
import java.awt.Color;
import java.util.List;
import java.util.Optional;

public record RenderTasksConfig(
    FractalImage canvas,
    Rect world,
    List<Transformation> variations,
    Optional<List<AffineTransformation>> affineTransformations,
    Optional<List<Color>> colors,
    int samples,
    short iterPerSample
) {
    public RenderTaskConfig toRenderTaskConfig(int count) {
        int samplesPerThread = samples / count;
        return new RenderTaskConfig(
            canvas.width(),
            canvas.height(),
            world,
            variations,
            affineTransformations,
            colors,
            samplesPerThread,
            iterPerSample
        );
    }
}
