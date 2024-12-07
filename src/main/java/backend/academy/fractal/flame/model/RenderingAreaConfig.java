package backend.academy.fractal.flame.model;

import backend.academy.fractal.flame.service.utils.FractalImage;

public record RenderingAreaConfig(
    FractalImage canvas,
    Rect world
) {
}
