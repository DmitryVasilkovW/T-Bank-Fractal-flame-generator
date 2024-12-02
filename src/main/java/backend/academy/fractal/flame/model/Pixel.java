package backend.academy.fractal.flame.model;

import lombok.Getter;

@Getter
public class Pixel {
    private int r;
    private int g;
    private int b;
    private int hitCount = 0;

    public Pixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void incrementHitCount() {
        hitCount++;
    }

    public void setRGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void add(Pixel other) {
        this.r += other.r;
        this.g += other.g;
        this.b += other.b;
        this.hitCount += other.hitCount;
    }

    public void average(Pixel other) {
        this.r = (this.r + other.r) / 2;
        this.g = (this.g + other.g) / 2;
        this.b = (this.b + other.b) / 2;
        this.hitCount += other.hitCount;
    }
}
