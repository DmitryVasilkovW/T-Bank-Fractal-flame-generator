package backend.academy.fractal.flame;

import java.util.List;

public class FractalRenderer {

    public static FractalImage render(
            FractalImage canvas,
            Rect world,
            List<Transformation> variations,
            int samples,
            short iterPerSample,
            long seed
    ) {
        for (int num = 0; num < samples; ++num) {
            Point pw = random(world);

            for (short step = 0; step < iterPerSample; ++step) {
                Transformation variation = variations.get((int) (Math.random() * variations.size()));
                pw = variation.apply(pw);

                if (world.contains(pw)) {
                    int x = (int) ((pw.getX() - world.getX()) / world.getWidth() * canvas.getWidth());
                    int y = (int) ((pw.getY() - world.getY()) / world.getHeight() * canvas.getHeight());
                    if (canvas.contains(x, y)) {
                        Pixel pixel = canvas.pixel(x, y);
                        pixel.hit();
                    }
                }
            }
        }
        return canvas;
    }

    private static Point random(Rect world) {
        double x = world.getX() + Math.random() * world.getWidth();
        double y = world.getY() + Math.random() * world.getHeight();
        return new Point(x, y);
    }
}


