package backend.academy.fractal.flame.model;

public class Rect {
    private final double x, y, width, height;

    public Rect(double x, double y, double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive.");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public boolean contains(Point point) {
        return point.getX() >= x &&
                point.getX() <= x + width &&
                point.getY() >= y &&
                point.getY() <= y + height;
    }
}

