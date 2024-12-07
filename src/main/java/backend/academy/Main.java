package backend.academy;

import backend.academy.fractal.flame.model.ColorThemes;
import backend.academy.fractal.flame.model.ImageFormat;
import backend.academy.fractal.flame.model.Rect;
import backend.academy.fractal.flame.service.render.FractalRenderer;
import backend.academy.fractal.flame.service.transformation.impl.ComplexWaveTransformationImpl;
import backend.academy.fractal.flame.service.transformation.impl.OscillatingStretchTransformationImpl;
import backend.academy.fractal.flame.service.transformation.impl.SinusoidalWarpTransformationImpl;
import backend.academy.fractal.flame.service.transformation.impl.SphericalTransformationImpl;
import backend.academy.fractal.flame.service.transformation.impl.StretchAlongXAndYTransformationImpl;
import backend.academy.fractal.flame.service.utils.FractalImage;
import backend.academy.fractal.flame.service.utils.GammaCorrectionProcessor;
import backend.academy.fractal.flame.service.utils.ImageUtils;
import lombok.experimental.UtilityClass;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class Main {

    public static void main(String[] args) throws IOException {
        int width = 1920;
        int height = 1080;
        int samples = 10_000_000;
        short iterations = 200;
        long seed = System.currentTimeMillis();
        //Rect world = new Rect(-3, -3, 6, 6);
        Rect world = new Rect(-4, -3, 8, 6);

        FractalImage canvas = FractalImage.create(width, height);

        var transformations = List.of(
            new SinusoidalWarpTransformationImpl(),
            new SphericalTransformationImpl()
        );

        Optional<ColorThemes> themeO = Optional.of(ColorThemes.BLUE);

        long start = System.currentTimeMillis();
        canvas = FractalRenderer.render(canvas, world, transformations, themeO, true, 6, samples, iterations, 6);
        long end = System.currentTimeMillis();
        System.out.println("Render time: " + (end - start) + " ms");

        var gc = new GammaCorrectionProcessor(1.8);
        gc.process(canvas);

        ImageUtils.saveColorfulImage(canvas, Path.of("fractal1.png"), ImageFormat.PNG);
        //ImageUtils.saveBlackAndWhiteImage(canvas, Path.of("fractal1.png"), ImageFormat.PNG);
        System.out.println("Фрактал успешно сохранён в файл fractal.png");
//        try {
//            ImageUtils.save(canvas, Path.of("green_fractal_1.png"), ImageFormat.PNG);
//            System.out.println("Фрактал успешно сохранён в файл black_and_white_fractal_1.png");
//        } catch (Exception e) {
//            System.out.println(e.fillInStackTrace());
//            System.err.println("Ошибка при сохранении изображения: " + e.getMessage());
//        }
    }
}

