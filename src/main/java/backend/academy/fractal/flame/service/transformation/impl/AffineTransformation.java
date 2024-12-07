package backend.academy.fractal.flame.service.transformation.impl;

import backend.academy.fractal.flame.model.AffineCoefficient;
import backend.academy.fractal.flame.model.Point;
import backend.academy.fractal.flame.service.transformation.Transformation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AffineTransformation implements Transformation {
    AffineCoefficient affineCoefficient;

    @Override
    public Point apply(Point p) {
        double x = affineCoefficient.a() + p.x() * affineCoefficient.b() + p.y() * affineCoefficient.c();
        double y = affineCoefficient.d() + p.x() * affineCoefficient.e() + p.y() * affineCoefficient.f();

        return new Point(x, y);
    }
}
