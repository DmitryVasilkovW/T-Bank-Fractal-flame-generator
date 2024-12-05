package backend.academy.fractal.flame.service.render;

import backend.academy.fractal.flame.model.Rect;
import backend.academy.fractal.flame.service.FractalImage;
import backend.academy.fractal.flame.service.Transformation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;

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

        try {
            List<Callable<FractalImage>> tasks = createRenderTasks(canvas, world, variations, samples, iterPerSample, threads);
            List<FractalImage> results = executeRenderTasks(executor, tasks);

            mergeResults(canvas, results);
        } catch (InterruptedException e) {
            throw new RuntimeException("Rendering was interrupted", e);
        } finally {
            shutdownExecutor(executor);
        }

        return canvas;
    }

    private static List<Callable<FractalImage>> createRenderTasks(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        int threads
    ) {
        List<Callable<FractalImage>> tasks = new ArrayList<>();
        int samplesPerThread = samples / threads;

        for (int i = 0; i < threads; i++) {
            tasks.add(new RenderTask(canvas.width(), canvas.height(), world, variations, samplesPerThread, iterPerSample));
        }

        return tasks;
    }

    private static List<FractalImage> executeRenderTasks(ExecutorService executor, List<Callable<FractalImage>> tasks)
        throws InterruptedException {
        try {
            return executor.invokeAll(tasks).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        } catch (InterruptedException e) {
            throw new RuntimeException("Rendering was interrupted", e);
        }
    }

    private static void mergeResults(FractalImage canvas, List<FractalImage> results) {
        for (FractalImage result : results) {
            canvas.merge(result);
        }
    }

    private static void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
