package backend.academy;

import backend.academy.fractal.flame.model.ImageFormat;
import backend.academy.fractal.flame.model.Rect;
import backend.academy.fractal.flame.service.FractalImage;
import backend.academy.fractal.flame.service.render.FractalRenderer;
import backend.academy.fractal.flame.service.ImageUtils;
import backend.academy.fractal.flame.service.TransformationImpl;
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
            TransformationImpl.custom1(),
            TransformationImpl.custom2(),
            TransformationImpl.spherical(),
            TransformationImpl.custom3(),
            TransformationImpl.tangent(),
            TransformationImpl.custom4()
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

