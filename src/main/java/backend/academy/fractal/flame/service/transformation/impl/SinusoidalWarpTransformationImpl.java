package backend.academy.fractal.flame.service.transformation.impl;

import backend.academy.fractal.flame.model.records.Point;
import backend.academy.fractal.flame.service.transformation.Transformation;

public class SinusoidalWarpTransformationImpl implements Transformation {

    @Override
    public Point apply(Point p) {
        double x = p.x() * Math.cos(p.y());
        double y = p.y() * Math.sin(p.x());
        return new Point(x, y);
    }
}
