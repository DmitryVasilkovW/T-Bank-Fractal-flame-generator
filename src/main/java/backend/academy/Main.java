package backend.academy;

import backend.academy.fractal.flame.model.ImageFormat;
import backend.academy.fractal.flame.model.Rect;
import backend.academy.fractal.flame.service.transformation.impl.ComplexWaveTransformationImpl;
import backend.academy.fractal.flame.service.transformation.impl.OscillatingStretchTransformationImpl;
import backend.academy.fractal.flame.service.transformation.impl.SinusoidalWarpTransformationImpl;
import backend.academy.fractal.flame.service.transformation.impl.SphericalTransformationImpl;
import backend.academy.fractal.flame.service.transformation.impl.StretchAlongXAndYTransformationImpl;
import backend.academy.fractal.flame.service.transformation.impl.TangentWarpTransformationImpl;
import backend.academy.fractal.flame.service.utils.FractalImage;
import backend.academy.fractal.flame.service.render.FractalRenderer;
import backend.academy.fractal.flame.service.utils.ImageUtils;
import java.nio.file.Path;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;
        int samples = 10_000_000;
        short iterations = 200;
        long seed = System.currentTimeMillis();
        Rect world = new Rect(-3, -3, 6, 6);

        FractalImage canvas = FractalImage.create(width, height);

        var transformations = List.of(
            new StretchAlongXAndYTransformationImpl(),
            new OscillatingStretchTransformationImpl(),
            new SphericalTransformationImpl(),
            new ComplexWaveTransformationImpl(),
            new TangentWarpTransformationImpl(),
            new SinusoidalWarpTransformationImpl()
        );

        long start = System.currentTimeMillis();
        canvas = FractalRenderer.render(canvas, world, transformations, samples, iterations, 6);
        long end = System.currentTimeMillis();
        System.out.println("Render time: " + (end - start) + " ms");

        try {
            ImageUtils.save(canvas, Path.of("fractal.png"), ImageFormat.PNG);
            System.out.println("Фрактал успешно сохранён в файл fractal.png");
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении изображения: " + e.getMessage());
        }
    }
}

