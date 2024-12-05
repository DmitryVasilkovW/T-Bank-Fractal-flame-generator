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
        try (ExecutorService executor = Executors.newFixedThreadPool(threads)) {
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
    }
}
