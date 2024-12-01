package backend.academy.fractal.flame.service;

import backend.academy.fractal.flame.model.Point;
import java.util.function.Function;

@FunctionalInterface
public interface Transformation extends Function<Point, Point> {}

