package backend.academy.fractal.flame.service.utils;


import backend.academy.fractal.flame.model.Pixel;
import lombok.Getter;

public final class FractalImage {
    @Getter
    private final Pixel[][] data;

    @Getter
    private final int width;
    @Getter
    private final int height;

    private FractalImage(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new Pixel[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                data[y][x] = new Pixel(0, 0, 0);
            }
        }
    }

    public static FractalImage create(int width, int height) {
        return new FractalImage(width, height);
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel pixel(int x, int y) {
        return data[y][x];
    }

    public void applySymmetry(boolean horizontal) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width / 2; x++) {
                int mirrorX = width - 1 - x;
                int mirrorY = height - 1 - y;

                if (horizontal) {
                    data[y][mirrorX] = data[y][x];
                } else {
                    data[mirrorY][x] = data[y][x];
                }
            }
        }
    }

    public void merge(FractalImage other) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (this.contains(x, y) && other.contains(x, y)) {
                    Pixel pixel1 = this.pixel(x, y);
                    Pixel pixel2 = other.pixel(x, y);
                    pixel1.add(pixel2);
                }
            }
        }
    }
}
