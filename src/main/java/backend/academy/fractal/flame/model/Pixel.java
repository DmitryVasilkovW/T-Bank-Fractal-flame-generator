package backend.academy.fractal.flame.model;

import java.awt.Color;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode
public class Pixel {
    private static final int MAX_COLOR = 255;

    private Color color;
    private int hitCount = 0;
    @Setter
    private double normal;

    public Pixel(int r, int g, int b) {
        this.color = new Color(r, g, b);
    }

    public int getR() {
        return color.getRed();
    }

    public int getG() {
        return color.getGreen();
    }

    public int getB() {
        return color.getBlue();
    }

    public void incrementHitCount() {
        hitCount++;
    }

    public void setRGB(int r, int g, int b) {
        this.color = new Color(r, g, b);
    }

    public void add(Pixel other) {
        int r = Math.min(MAX_COLOR, Math.max(0, this.color.getRed() + other.color.getRed()));
        int g = Math.min(MAX_COLOR, Math.max(0, this.color.getGreen() + other.color.getGreen()));
        int b = Math.min(MAX_COLOR, Math.max(0, this.color.getBlue() + other.color.getBlue()));

        color = new Color(r, g, b);
        this.hitCount += other.hitCount;
    }

    public void addAverageRGB(int r, int g, int b) {
        int newR = (getR() + r) / 2;
        int newG = (getG() + g) / 2;
        int newB = (getB() + b) / 2;

        setRGB(newR, newG, newB);
    }
}
