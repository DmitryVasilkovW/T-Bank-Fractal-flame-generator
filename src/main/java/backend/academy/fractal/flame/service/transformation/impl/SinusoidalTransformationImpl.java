package backend.academy.fractal.flame.service.transformation.impl;

import backend.academy.fractal.flame.model.records.Point;
import backend.academy.fractal.flame.service.transformation.Transformation;

public class SinusoidalTransformationImpl implements Transformation {

    @Override
    public Point apply(Point p) {
        double x = Math.sin(p.x());
        double y = Math.sin(p.y());
        return new Point(x, y);
    }
}
