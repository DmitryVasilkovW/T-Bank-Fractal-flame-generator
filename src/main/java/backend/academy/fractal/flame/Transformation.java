package backend.academy.fractal.flame;

import java.util.function.Function;

@FunctionalInterface
public interface Transformation extends Function<Point, Point> {}

