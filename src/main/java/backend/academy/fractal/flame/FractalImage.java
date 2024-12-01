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
                data[y][x] = new Pixel(0, 0, 0);  // Инициализация пикселей
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

    public void applyGammaCorrection(double gamma) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = data[y][x];
                int correctedR = (int) (Math.pow(pixel.getR() / 255.0, gamma) * 255);
                int correctedG = (int) (Math.pow(pixel.getG() / 255.0, gamma) * 255);
                int correctedB = (int) (Math.pow(pixel.getB() / 255.0, gamma) * 255);
                pixel.setRGB(correctedR, correctedG, correctedB);
            }
        }
    }

    public void applySymmetry(boolean horizontal) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width / 2; x++) {
                int mirrorX = width - 1 - x;
                int mirrorY = height - 1 - y;

                if (horizontal) {
                    data[y][mirrorX] = data[y][x]; // Горизонтальная симметрия
                } else {
                    data[mirrorY][x] = data[y][x]; // Вертикальная симметрия
                }
            }
        }
    }

    // Метод для слияния данных с другим изображением
    public void merge(FractalImage other) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (this.contains(x, y) && other.contains(x, y)) {
                    // Слияние пикселей. Здесь пример простого суммирования
                    Pixel pixel1 = this.pixel(x, y);
                    Pixel pixel2 = other.pixel(x, y);
                    pixel1.add(pixel2);
                }
            }
        }
    }
}
