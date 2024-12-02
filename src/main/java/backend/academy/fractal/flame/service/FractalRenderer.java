package backend.academy.fractal.flame.service;

import backend.academy.fractal.flame.model.Pixel;
import backend.academy.fractal.flame.model.Point;
import backend.academy.fractal.flame.model.Rect;
import lombok.experimental.UtilityClass;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class FractalRenderer {

    public static FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        int threads
    ) {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Callable<FractalImage>> tasks = new ArrayList<>();

        int samplesPerThread = samples / threads;
        for (int i = 0; i < threads; i++) {
            tasks.add(new RenderTask(canvas.width(), canvas.height(), world, variations, samplesPerThread,
                iterPerSample));
        }

        try {
            List<FractalImage> results = executor.invokeAll(tasks).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

            for (FractalImage result : results) {
                canvas.merge(result);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Rendering was interrupted", e);
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }

        return canvas;
    }

    private static class RenderTask implements Callable<FractalImage> {
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
                        int x = (int) ((pw.getX() - world.getX()) / world.getWidth() * width);
                        int y = (int) ((pw.getY() - world.getY()) / world.getHeight() * height);
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
            double x = world.getX() + ThreadLocalRandom.current().nextDouble() * world.getWidth();
            double y = world.getY() + ThreadLocalRandom.current().nextDouble() * world.getHeight();
            return new Point(x, y);
        }
    }
}
