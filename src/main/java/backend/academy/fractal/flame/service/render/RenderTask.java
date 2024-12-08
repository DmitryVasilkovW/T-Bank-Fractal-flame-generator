package backend.academy.fractal.flame.service.render;

import backend.academy.fractal.flame.model.Pixel;
import backend.academy.fractal.flame.model.records.Point;
import backend.academy.fractal.flame.model.records.Rect;
import backend.academy.fractal.flame.model.records.RenderTaskConfig;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.impl.AffineTransformation;
import backend.academy.fractal.flame.service.utils.FractalImage;
import java.awt.Color;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class RenderTask implements Callable<FractalImage> {
    private final int width;
    private final int height;
    private final Rect world;
    private final List<Transformation> variations;
    private final Optional<List<AffineTransformation>> affineTransformationsO;
    private final Optional<List<Color>> colorsO;
    private final int samples;
    private final int iterPerSample;

    public RenderTask(
        RenderTaskConfig config
    ) {
        this.width = config.width();
        this.height = config.height();
        this.world = config.world();
        this.variations = config.variations();
        this.affineTransformationsO = config.affineTransformations();
        this.colorsO = config.colors();
        this.samples = config.samples();
        this.iterPerSample = config.iterPerSample();
    }

    @Override
    public FractalImage call() {
        FractalImage localCanvas = FractalImage.create(width, height);

        for (int num = 0; num < samples; ++num) {
            Point pw = random(world);

            for (short step = 0; step < iterPerSample; ++step) {
                pw = tryToApplyRandomAffineTransformation(pw);
                pw = applyRandomTransformation(pw);

                if (world.contains(pw)) {
                    int x = (int) ((pw.x() - world.x()) / world.width() * width);
                    int y = (int) ((pw.y() - world.y()) / world.height() * height);
                    if (localCanvas.contains(x, y)) {
                        Pixel pixel = localCanvas.pixel(x, y);

                        tryToAddColor(pixel);
                        pixel.incrementHitCount();
                    }
                }
            }
        }

        return localCanvas;
    }

    private void tryToAddColor(Pixel pixel) {
        if (colorsO.isPresent()) {
            Color color = getRandomItemFromList(colorsO.get());

            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();

            pixel.addAverageRGB(r, g, b);
        }
    }

    private Point tryToApplyRandomAffineTransformation(Point p) {
        if (affineTransformationsO.isPresent()) {
            AffineTransformation affineTransformation =
                getRandomItemFromList(affineTransformationsO.get());

            return affineTransformation.apply(p);
        }

        return p;
    }

    private Point applyRandomTransformation(Point p) {
        Transformation transformation = getRandomItemFromList(variations);
        return transformation.apply(p);
    }

    private <T> T getRandomItemFromList(List<T> item) {
        return item.get(ThreadLocalRandom.current().nextInt(item.size()));
    }

    private static Point random(Rect world) {
        double x = world.x() + ThreadLocalRandom.current().nextDouble() * world.width();
        double y = world.y() + ThreadLocalRandom.current().nextDouble() * world.height();
        return new Point(x, y);
    }
}
