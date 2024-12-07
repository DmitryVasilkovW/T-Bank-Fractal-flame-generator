package backend.academy.fractal.flame.service.render;

import backend.academy.fractal.flame.model.records.AffineCoefficient;
import backend.academy.fractal.flame.model.enums.ColorTheme;
import backend.academy.fractal.flame.model.records.FractalConfig;
import backend.academy.fractal.flame.model.records.Rect;
import backend.academy.fractal.flame.model.records.RenderTasksConfig;
import backend.academy.fractal.flame.model.records.RenderingAreaConfig;
import backend.academy.fractal.flame.service.color.ColorGen;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.impl.AffineTransformation;
import backend.academy.fractal.flame.service.utils.FractalImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.experimental.UtilityClass;
import static backend.academy.fractal.flame.model.enums.ColorTheme.BLACK_AND_WHITE;

@UtilityClass
public class FractalRenderer {
    private static final int TIMEOUT_IN_SECONDS = 60;
    private static final String ERROR_MESSAGE = "Rendering was interrupted";

    public static FractalImage render(
        FractalConfig fractalConfig,
        RenderingAreaConfig renderingAreaConfig,
        int threads
    ) {
        ColorTheme theme = fractalConfig.colorTheme();
        int colorDiversityIndex = fractalConfig.colorDiversityIndex();
        int degreeOfRandomnessOfFractalCreation = fractalConfig.degreeOfRandomnessOfFractalCreation();
        int samples = fractalConfig.samples();
        short iterPerSample = fractalConfig.iterPerSample();
        boolean complicateFlameShape = fractalConfig.complicateFlameShape();
        List<Transformation> variations = fractalConfig.variations();
        FractalImage canvas = renderingAreaConfig.canvas();
        Rect world = renderingAreaConfig.world();

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        Optional<List<ColorGen>> colorGensO = getOptionalColors(theme, colorDiversityIndex);
        Optional<List<AffineTransformation>> affineTransformationsO =
            getOptionalAffineCoefficients(complicateFlameShape, degreeOfRandomnessOfFractalCreation);

        var renderTasksConfig = new RenderTasksConfig(
            canvas,
            world,
            variations,
            affineTransformationsO,
            colorGensO,
            samples,
            iterPerSample
        );

        try {
            List<Callable<FractalImage>> tasks =
                createRenderTasks(renderTasksConfig,
                    threads);
            List<FractalImage> results = executeRenderTasks(executor, tasks);

            mergeResults(canvas, results);
        } catch (InterruptedException e) {
            throw new RuntimeException(ERROR_MESSAGE, e);
        } finally {
            shutdownExecutor(executor);
        }

        return canvas;
    }

    private static Optional<List<ColorGen>> getOptionalColors(ColorTheme theme, int colorDiversityIndex) {
        if (theme != BLACK_AND_WHITE) {
            return Optional.of(getRandomColors(theme, colorDiversityIndex));
        }
        return Optional.empty();
    }

    private static List<ColorGen> getRandomColors(ColorTheme theme, int colorDiversityIndex) {
        var colors = new ArrayList<ColorGen>();
        for (int i = 0; i < colorDiversityIndex; i++) {
            colors.add(ColorGen.createRandomAffineCoefficient(theme));
        }

        return colors;
    }

    private static Optional<List<AffineTransformation>> getOptionalAffineCoefficients(
        boolean complicateFlameShape,
        int degreeOfRandomnessOfFractalCreation
    ) {
        if (complicateFlameShape) {
            return Optional.of(getRandomAffineCoefficients(degreeOfRandomnessOfFractalCreation));
        }
        return Optional.empty();
    }

    private static List<AffineTransformation> getRandomAffineCoefficients(int degreeOfRandomnessOfFractalCreation) {
        var transforms = new ArrayList<AffineTransformation>();
        for (int i = 0; i < degreeOfRandomnessOfFractalCreation; i++) {
            transforms.add(new AffineTransformation(AffineCoefficient.createRandomAffineCoefficient()));
        }

        return transforms;
    }

    private static List<Callable<FractalImage>> createRenderTasks(
        RenderTasksConfig renderTasksConfig,
        int threads
    ) {
        List<Callable<FractalImage>> tasks = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            tasks.add(new RenderTask(renderTasksConfig.toRenderTaskConfig(threads)));
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
            throw new RuntimeException(ERROR_MESSAGE, e);
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
            if (!executor.awaitTermination(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
