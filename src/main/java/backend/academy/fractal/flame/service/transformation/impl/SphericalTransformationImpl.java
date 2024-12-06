package backend.academy.fractal.flame.service.transformation.impl;

import backend.academy.fractal.flame.model.Point;
import backend.academy.fractal.flame.service.transformation.Transformation;

public class SphericalTransformationImpl implements Transformation {

    @Override
    public Point apply(Point p) {
        double r2 = p.x() * p.x() + p.y() * p.y();
        double x = p.x() / r2;
        double y = p.y() / r2;
        return new Point(x, y);
    }
}
