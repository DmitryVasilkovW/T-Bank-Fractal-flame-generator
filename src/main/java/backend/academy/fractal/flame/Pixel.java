package backend.academy.fractal.flame;

public class Pixel {
    private int r, g, b, hitCount;

    public Pixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.hitCount = 0;
    }

    public void hit() {
        hitCount++;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setRGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}

