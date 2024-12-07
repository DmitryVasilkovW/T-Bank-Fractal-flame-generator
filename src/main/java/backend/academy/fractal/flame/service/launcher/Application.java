package backend.academy.fractal.flame.service.launcher;

import backend.academy.fractal.flame.model.ColorTheme;
import backend.academy.fractal.flame.model.FractalConfig;
import backend.academy.fractal.flame.model.ImageFormat;
import backend.academy.fractal.flame.model.Rect;
import backend.academy.fractal.flame.model.RenderingAreaConfig;
import backend.academy.fractal.flame.service.io.Printer;
import backend.academy.fractal.flame.service.io.Reader;
import backend.academy.fractal.flame.service.render.FractalRenderer;
import backend.academy.fractal.flame.service.transformation.impl.ComplexWaveTransformationImpl;
import backend.academy.fractal.flame.service.transformation.impl.SinusoidalWarpTransformationImpl;
import backend.academy.fractal.flame.service.transformation.impl.SphericalTransformationImpl;
import backend.academy.fractal.flame.service.transformation.impl.SwirlTransformationImpl;
import backend.academy.fractal.flame.service.utils.FractalImage;
import backend.academy.fractal.flame.service.utils.GammaCorrectionProcessor;
import backend.academy.fractal.flame.service.utils.ImageUtils;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import static backend.academy.fractal.flame.model.ColorTheme.BLUE;

@SpringBootApplication
@ComponentScan(basePackages = "backend.academy.fractal.flame")
public class Application implements CommandLineRunner {
    @Autowired
    private final Printer printer;
    @Autowired
    private final Reader reader;

    public Application(Printer printer, Reader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    @Override
    public void run(String... args) throws Exception {
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
            new SphericalTransformationImpl(),
            new SwirlTransformationImpl()
        );

        ColorTheme theme = BLUE;

        var fractalConfig = new FractalConfig(
            transformations,
            theme,
            6,
            true,
            6,
            iterations,
            samples
        );

        var renderingArea = new RenderingAreaConfig(
            canvas,
            world
        );

        long start = System.currentTimeMillis();
        canvas = FractalRenderer.render(fractalConfig, renderingArea,6);
        long end = System.currentTimeMillis();
        printer.println("Render time: " + (end - start) + " ms");

        var gc = new GammaCorrectionProcessor(1.8);
        gc.process(canvas);

        ImageUtils.saveColorfulImage(canvas, Path.of("fractal1.png"), ImageFormat.PNG);
        //ImageUtils.saveBlackAndWhiteImage(canvas, Path.of("fractal1.png"), ImageFormat.PNG);
        printer.println("Фрактал успешно сохранён в файл fractal.png");
    }

//    private Rect getRect() {
//
//    }
}
