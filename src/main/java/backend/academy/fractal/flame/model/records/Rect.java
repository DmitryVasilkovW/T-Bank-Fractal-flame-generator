package backend.academy.fractal.flame.model.records;

public record Rect(double x, double y, double width, double height) {
    public Rect {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive.");
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height;
    }

    public boolean contains(Point point) {
        return point.x() >= x
            && point.x() <= x + width
            && point.y() >= y
            && point.y() <= y + height;
    }
}

