package backend.academy.fractal.flame.service.launcher;

import backend.academy.fractal.flame.model.FractalConfig;
import backend.academy.fractal.flame.model.Rect;
import backend.academy.fractal.flame.model.RenderingAreaConfig;
import backend.academy.fractal.flame.model.TransformationRequest;
import backend.academy.fractal.flame.model.enums.ColorTheme;
import backend.academy.fractal.flame.model.enums.ImageFormat;
import backend.academy.fractal.flame.model.enums.Transformations;
import backend.academy.fractal.flame.service.io.Printer;
import backend.academy.fractal.flame.service.io.Reader;
import backend.academy.fractal.flame.service.render.FractalRenderer;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.TransformationChain;
import backend.academy.fractal.flame.service.utils.FractalImage;
import backend.academy.fractal.flame.service.utils.GammaCorrectionProcessor;
import backend.academy.fractal.flame.service.utils.ImageUtils;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import static backend.academy.fractal.flame.model.enums.ColorTheme.BLACK_AND_WHITE;
import static backend.academy.fractal.flame.model.enums.ColorTheme.BLUE;

@SpringBootApplication
@ComponentScan(basePackages = "backend.academy.fractal.flame")
public class Application implements CommandLineRunner {
    private final Printer printer;
    private final Reader reader;
    private final TransformationChain transformationChain;

    @Autowired
    public Application(
        Printer printer,
        Reader reader,
        TransformationChain transformationChain
    ) {
        this.printer = printer;
        this.reader = reader;
        this.transformationChain = transformationChain;
    }

    @Override
    public void run(String... args) throws Exception {
        int width = 1920;
        int height = 1080;
        int samples = 10_000_000;
        short iterations = 200;
        long seed = System.currentTimeMillis();

        FractalImage canvas = FractalImage.create(width, height);

        ColorTheme theme = BLACK_AND_WHITE;
        Rect world = getRect();
        List<Transformation> transformations = getTransformations();

        var fractalConfig = new FractalConfig(
            transformations,
            theme,
            6,
            false,
            6,
            iterations,
            samples
        );

        var renderingArea = new RenderingAreaConfig(
            canvas,
            world
        );

        long start = System.currentTimeMillis();
        canvas = FractalRenderer.render(fractalConfig, renderingArea, 6);
        long end = System.currentTimeMillis();
        printer.println("Render time: " + (end - start) + " ms");

        var gc = new GammaCorrectionProcessor(1.8);
        //gc.process(canvas);

        //ImageUtils.saveColorfulImage(canvas, Path.of("fractal1.png"), ImageFormat.PNG);
        ImageUtils.saveBlackAndWhiteImage(canvas, Path.of("fractal1.png"), ImageFormat.PNG);
        printer.println("Фрактал успешно сохранён в файл fractal.png");
    }

    private List<Transformation> getTransformations() {
        var transformations = new ArrayList<Transformation>();
        showAllTransformations();
        boolean stop = false;

        while (!stop) {
            try {
                Optional<Transformation> transformationO = tryTogetTransformationOptional();
                if (transformationO.isPresent()) {
                    transformations.add(transformationO.get());
                } else {
                    stop = true;
                }
            } catch (Exception e) {
                printer.println(e.getMessage());
            }
        }

        return transformations;
    }

    private void showAllTransformations() {
        int i = 1;
        for (Transformations transformation : Transformations.values()) {
            printer.println(i + ") " + transformation.displayName());
            i++;
        }
        printer.println("\n");
    }

    private Optional<Transformation> tryTogetTransformationOptional() {
        String line = reader.readLineAsString();
        if (line.isEmpty()) {
            return Optional.empty();
        }

        try {
            Transformations t = Transformations.fromDisplayName(line);
            return Optional.of(transformationChain.getTransformations(new TransformationRequest(t)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Rect getRect() {
        Optional<Rect> rectO = Optional.empty();
        while (rectO.isEmpty()) {
            String rectParams = reader.readLineAsString();
            rectO = tryToGetOptionalRect(rectParams);
        }

        return rectO.get();
    }

    private Optional<Rect> tryToGetOptionalRect(String rectParams) {
        if (rectParams.isEmpty()) {
            return Optional.of(new Rect(-4, -3, 8, 6));
        }

        String[] parts = rectParams.trim().split("\\s+");
        try {
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int w = Integer.parseInt(parts[2]);
            int h = Integer.parseInt(parts[3]);

            return Optional.of(new Rect(x, y, w, h));
        } catch (Exception e) {
            printer.println("Incorrect data format");
            return Optional.empty();
        }
    }
}
