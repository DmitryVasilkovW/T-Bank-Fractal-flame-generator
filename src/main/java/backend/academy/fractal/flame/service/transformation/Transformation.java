package backend.academy.fractal.flame.service.transformation;

import backend.academy.fractal.flame.model.records.Point;
import java.util.function.Function;

@FunctionalInterface
public interface Transformation extends Function<Point, Point> {}

