package backend.academy.fractal.flame.model;

public class Pixel {
    private int r, g, b, hitCount;

    public Pixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.hitCount = 0;
    }

    // Метод для увеличения hitCount
    public void hit() {
        hitCount++;
    }

    // Получить количество попаданий
    public int getHitCount() {
        return hitCount;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }
    public int getB() {
        return b;
    }

    // Установить значения RGB
    public void setRGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    // Метод add для слияния пикселей
    public void add(Pixel other) {
        // Сложение цветов (r, g, b) и увеличение hitCount
        this.r += other.r;
        this.g += other.g;
        this.b += other.b;
        this.hitCount += other.hitCount;
    }

    // Вы можете добавить метод для усреднения значений RGB, если это необходимо
    public void average(Pixel other) {
        this.r = (this.r + other.r) / 2;
        this.g = (this.g + other.g) / 2;
        this.b = (this.b + other.b) / 2;
        this.hitCount += other.hitCount; // Увеличиваем количество попаданий
    }
}
