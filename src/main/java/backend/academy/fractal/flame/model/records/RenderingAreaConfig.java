package backend.academy.fractal.flame.model.records;

import backend.academy.fractal.flame.service.util.FractalImage;

public record RenderingAreaConfig(
    FractalImage canvas,
    Rect world
) {
}
