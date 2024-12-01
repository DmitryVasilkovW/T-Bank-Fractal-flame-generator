package backend.academy.fractal.flame;

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
                double x = Math.sin(p.getX());
                double y = Math.sin(p.getY());
                return new Point(x, y);
            }
        };
    }

    public static Transformation spherical() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double r2 = p.getX() * p.getX() + p.getY() * p.getY();
                double x = p.getX() / r2;
                double y = p.getY() / r2;
                return new Point(x, y);
            }
        };
    }

    public static Transformation swirl() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double r2 = p.getX() * p.getX() + p.getY() * p.getY();
                double x = p.getX() * Math.sin(r2) - p.getY() * Math.cos(r2);
                double y = p.getX() * Math.cos(r2) + p.getY() * Math.sin(r2);
                return new Point(x, y);
            }
        };
    }

    public static Transformation horseshoe() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double r = Math.sqrt(p.getX() * p.getX() + p.getY() * p.getY());
                double x = (p.getX() - p.getY()) * (p.getX() + p.getY()) / r;
                double y = 2 * p.getX() * p.getY() / r;
                return new Point(x, y);
            }
        };
    }

    public static Transformation custom1() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double x = Math.sin(p.getX()) * p.getY();
                double y = Math.sin(p.getY()) * p.getX();
                return new Point(x, y);
            }
        };
    }

    public static Transformation custom2() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double x = p.getX() * Math.sin(p.getY());
                double y = p.getY() * Math.sin(p.getX());
                return new Point(x, y);
            }
        };
    }

    public static Transformation custom3() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double x = Math.sin(p.getX() * p.getX() - p.getY() * p.getY());
                double y = Math.sin(2 * p.getX() * p.getY());
                return new Point(x, y);
            }
        };
    }

    public static Transformation tangent() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double x = Math.tan(p.getX());
                double y = Math.tan(p.getY());
                return new Point(x, y);
            }
        };
    }

    public static Transformation custom4() {
        return new Transformation() {
            @Override
            public Point apply(Point p) {
                double x = p.getX() * Math.cos(p.getY());
                double y = p.getY() * Math.sin(p.getX());
                return new Point(x, y);
            }
        };
    }
}
