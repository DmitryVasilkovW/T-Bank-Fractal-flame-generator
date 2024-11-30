package backend.academy.fractal.flame;

public class FractalImage {
    private final Pixel[][] data;
    private final int width;
    private final int height;

    private FractalImage(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new Pixel[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                data[y][x] = new Pixel(0, 0, 0); // Черный цвет
            }
        }
    }

    public static FractalImage create(int width, int height) {
        return new FractalImage(width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel pixel(int x, int y) {
        return data[y][x];
    }
}


