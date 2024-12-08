package backend.academy.fractal.flame.service.transformation.impl;

import backend.academy.fractal.flame.model.records.Point;
import backend.academy.fractal.flame.service.transformation.Transformation;

public class LinearTransformationImpl implements Transformation {

    @Override
    public Point apply(Point p) {
        return p;
    }
}
