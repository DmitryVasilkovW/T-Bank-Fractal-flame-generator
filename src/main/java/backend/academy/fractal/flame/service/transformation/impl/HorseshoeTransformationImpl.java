package backend.academy.fractal.flame.service.transformation.impl;

import backend.academy.fractal.flame.model.records.Point;
import backend.academy.fractal.flame.service.transformation.Transformation;

public class HorseshoeTransformationImpl implements Transformation {

    @Override
    public Point apply(Point p) {
        double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
        double x = (p.x() - p.y()) * (p.x() + p.y()) / r;
        double y = 2 * p.x() * p.y() / r;
        return new Point(x, y);
    }
}
