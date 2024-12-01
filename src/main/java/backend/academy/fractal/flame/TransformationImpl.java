package backend.academy.fractal.flame;

public class TransformationImpl implements Transformation {
    public Point apply(Point p) {
        // Базовая трансформация, можно использовать как linear
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
}

