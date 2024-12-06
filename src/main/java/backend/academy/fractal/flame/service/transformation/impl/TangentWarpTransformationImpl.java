package backend.academy.fractal.flame.service.transformation.impl;

import backend.academy.fractal.flame.model.Point;
import backend.academy.fractal.flame.service.transformation.Transformation;

public class TangentWarpTransformationImpl implements Transformation {

    @Override
    public Point apply(Point p) {
        double x = Math.tan(p.x());
        double y = Math.tan(p.y());
        return new Point(x, y);
    }
}
