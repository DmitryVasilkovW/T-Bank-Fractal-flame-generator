package backend.academy.fractal.flame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class FractalRenderer {

    public static FractalImage render(
            FractalImage canvas,
            Rect world,
            List<Transformation> variations,
            int samples,
            short iterPerSample,
            long seed,
            int numThreads
    ) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Runnable> tasks = new ArrayList<>();

        // Разделяем изображение на блоки по горизонтали
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int samplesPerThread = samples / numThreads;

        for (int threadIndex = 0; threadIndex < numThreads; threadIndex++) {
            final int startX = (width / numThreads) * threadIndex;
            final int endX = (width / numThreads) * (threadIndex + 1);

            tasks.add(() -> {
                FractalImage localCanvas = FractalImage.create(width, height);
                for (int num = 0; num < samplesPerThread; ++num) {
                    Point pw = random(world);

                    for (short step = 0; step < iterPerSample; ++step) {
                        Transformation variation = variations.get(ThreadLocalRandom.current().nextInt(variations.size()));
                        pw = variation.apply(pw);

                        if (world.contains(pw)) {
                            int x = (int) ((pw.getX() - world.getX()) / world.getWidth() * width);
                            int y = (int) ((pw.getY() - world.getY()) / world.getHeight() * height);

                            if (x >= startX && x < endX && canvas.contains(x, y)) {
                                Pixel pixel = localCanvas.pixel(x, y);
                                pixel.hit();
                            }
                        }
                    }
                }
                // Сливаем локальный холст в общий
                synchronized (canvas) {
                    canvas.merge(localCanvas);
                }
            });
        }

        // Выполняем задачи
        for (Runnable task : tasks) {
            executor.submit(task);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        return canvas;
    }

    private static Point random(Rect world) {
        double x = world.getX() + ThreadLocalRandom.current().nextDouble() * world.getWidth();
        double y = world.getY() + ThreadLocalRandom.current().nextDouble() * world.getHeight();
        return new Point(x, y);
    }
}

