package backend.academy.fractal.flame.service.transformation.impl;

import backend.academy.fractal.flame.model.Point;
import backend.academy.fractal.flame.service.transformation.Transformation;

public class ComplexWaveTransformationImpl implements Transformation {

    @Override
    public Point apply(Point p) {
        double x = Math.sin(p.x() * p.x() - p.y() * p.y());
        double y = Math.sin(2 * p.x() * p.y());
        return new Point(x, y);
    }
}
