package backend.academy.fractal.flame.service.transformation.impl;

import backend.academy.fractal.flame.model.Point;
import backend.academy.fractal.flame.service.transformation.Transformation;

public class TransformationImpl implements Transformation {
    public Point apply(Point p) {
        return p;
    }

    public static Transformation linear() {
        return new TransformationImpl();
    }

    public static Transformation sinusoidal() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double x = Math.sin(p.x());
                double y = Math.sin(p.y());
                return new Point(x, y);
            }
        };
    }

    public static Transformation spherical() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double r2 = p.x() * p.x() + p.y() * p.y();
                double x = p.x() / r2;
                double y = p.y() / r2;
                return new Point(x, y);
            }
        };
    }

    public static Transformation swirl() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double r2 = p.x() * p.x() + p.y() * p.y();
                double x = p.x() * Math.sin(r2) - p.y() * Math.cos(r2);
                double y = p.x() * Math.cos(r2) + p.y() * Math.sin(r2);
                return new Point(x, y);
            }
        };
    }

    public static Transformation horseshoe() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double r = Math.sqrt(p.x() * p.x() + p.y() * p.y());
                double x = (p.x() - p.y()) * (p.x() + p.y()) / r;
                double y = 2 * p.x() * p.y() / r;
                return new Point(x, y);
            }
        };
    }

    public static Transformation stretchAlongXAndY() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double x = Math.sin(p.x()) * p.y();
                double y = Math.sin(p.y()) * p.x();
                return new Point(x, y);
            }
        };
    }

    public static Transformation oscillatingStretch() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double x = p.x() * Math.sin(p.y());
                double y = p.y() * Math.sin(p.x());
                return new Point(x, y);
            }
        };
    }

    public static Transformation complexWave() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double x = Math.sin(p.x() * p.x() - p.y() * p.y());
                double y = Math.sin(2 * p.x() * p.y());
                return new Point(x, y);
            }
        };
    }

    public static Transformation tangentWarp() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double x = Math.tan(p.x());
                double y = Math.tan(p.y());
                return new Point(x, y);
            }
        };
    }

    public static Transformation sinusoidalWarp() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double x = p.x() * Math.cos(p.y());
                double y = p.y() * Math.sin(p.x());
                return new Point(x, y);
            }
        };
    }
}
