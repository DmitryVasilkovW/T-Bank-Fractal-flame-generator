package backend.academy.fractal.flame.service.transformation.impl;

import backend.academy.fractal.flame.model.Point;
import backend.academy.fractal.flame.service.transformation.Transformation;

public class StretchAlongXAndYTransformationImpl implements Transformation {

    @Override
    public Point apply(Point p) {
        double x = Math.sin(p.x()) * p.y();
        double y = Math.sin(p.y()) * p.x();
        return new Point(x, y);
    }
}
