package backend.academy.fractal.flame.service.render;

import backend.academy.fractal.flame.model.Pixel;
import backend.academy.fractal.flame.model.Point;
import backend.academy.fractal.flame.model.Rect;
import backend.academy.fractal.flame.service.FractalImage;
import backend.academy.fractal.flame.service.Transformation;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class RenderTask implements Callable<FractalImage> {
    private final int width;
    private final int height;
    private final Rect world;
    private final List<Transformation> variations;
    private final int samples;
    private final short iterPerSample;

    public RenderTask(
        int width,
        int height,
        Rect world,
        List<Transformation> variations,
        int samples,
        short iterPerSample
    ) {
        this.width = width;
        this.height = height;
        this.world = world;
        this.variations = variations;
        this.samples = samples;
        this.iterPerSample = iterPerSample;
    }

    @Override
    public FractalImage call() {
        FractalImage localCanvas = FractalImage.create(width, height);

        for (int num = 0; num < samples; ++num) {
            Point pw = random(world);

            for (short step = 0; step < iterPerSample; ++step) {
                Transformation variation = variations.get(ThreadLocalRandom.current().nextInt(variations.size()));
                pw = variation.apply(pw);

                if (world.contains(pw)) {
                    int x = (int) ((pw.x() - world.x()) / world.width() * width);
                    int y = (int) ((pw.y() - world.y()) / world.height() * height);
                    if (localCanvas.contains(x, y)) {
                        Pixel pixel = localCanvas.pixel(x, y);
                        pixel.incrementHitCount();
                    }
                }
            }
        }

        return localCanvas;
    }

    private static Point random(Rect world) {
        double x = world.x() + ThreadLocalRandom.current().nextDouble() * world.width();
        double y = world.y() + ThreadLocalRandom.current().nextDouble() * world.height();
        return new Point(x, y);
    }
}
