package backend.academy.fractal.flame.service.launcher;

import backend.academy.fractal.flame.model.enums.ColorTheme;
import backend.academy.fractal.flame.model.enums.ImageFormat;
import backend.academy.fractal.flame.model.enums.Transformations;
import backend.academy.fractal.flame.model.records.ColorRequest;
import backend.academy.fractal.flame.model.records.FractalConfig;
import backend.academy.fractal.flame.model.records.Rect;
import backend.academy.fractal.flame.model.records.RenderingAreaConfig;
import backend.academy.fractal.flame.model.records.TransformationRequest;
import backend.academy.fractal.flame.service.color.chain.ColorChain;
import backend.academy.fractal.flame.service.io.Printer;
import backend.academy.fractal.flame.service.io.Reader;
import backend.academy.fractal.flame.service.render.FractalRenderer;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.TransformationChain;
import backend.academy.fractal.flame.service.utils.FractalImage;
import backend.academy.fractal.flame.service.utils.GammaCorrectionProcessor;
import backend.academy.fractal.flame.service.utils.ImageUtils;
import java.awt.Color;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import static backend.academy.fractal.flame.model.enums.ColorTheme.BLACK_AND_WHITE;
import static backend.academy.fractal.flame.model.enums.ColorTheme.RANDOM;

@SpringBootApplication
@ComponentScan(basePackages = "backend.academy.fractal.flame")
public class Application implements CommandLineRunner {
    private static final int DEFAULT_WIDTH = 1920;
    private static final int DEFAULT_HEIGHT = 1080;
    private static final int DEFAULT_SAMPLES = 10_000_000;
    private static final int DEFAULT_ITERATIONS = 200;
    private static final int DEFAULT_THREADS = 6;
    private static final int DEFAULT_RANDOMNESS_DEGREE = 6;
    private static final double DEFAULT_GAMMA = 1.8;
    private static final String DEFAULT_PATH = "fractal.png";
    private static final ImageFormat DEFAULT_IMAGE_FORMAT = ImageFormat.PNG;
    private static final Rect DEFAULT_RECT = new Rect(-4, -3, 8, 6);
    private static final int DEFAULT_COLOR_DIVERSITY_INDEX = 4;

    private final Printer printer;
    private final Reader reader;
    private final TransformationChain transformationChain;
    private final ColorChain colorChain;
    private String path;
    private ImageFormat imageFormat;
    private ColorTheme theme;

    @Autowired
    public Application(
        Printer printer,
        Reader reader,
        TransformationChain transformationChain,
        ColorChain colorChain
    ) {
        this.printer = printer;
        this.reader = reader;
        this.transformationChain = transformationChain;
        this.colorChain = colorChain;
    }

    @Override
    public void run(String... args) {
        int width = getPositiveNumInput("Input width", DEFAULT_WIDTH);
        int height = getPositiveNumInput("Input height", DEFAULT_HEIGHT);
        int samples = getPositiveNumInput("Input samples", DEFAULT_SAMPLES);
        int iterations = getPositiveNumInput("Input iterations", DEFAULT_ITERATIONS);
        int threads = getPositiveNumInput("Input threads", DEFAULT_THREADS);
        int degreeOfRandomnessOfFractalCreation = getPositiveNumInput(
            "Input degree of fractal creation", DEFAULT_RANDOMNESS_DEGREE);
        double gamma = getPositiveNumInput("Input gamma", DEFAULT_GAMMA);
        boolean complicateFlameShape = getBoolInput("Is it necessary to make a fractal with a more complex shape?");
        boolean isGammaCorrectionNecessary = getBoolInput("If gamma correction is necessary?");
        boolean isSymmetryNecessary = getBoolInput("if symmetry is necessary?");
        boolean horizontal = getBoolInput("Need horizontal symmetry?");

        FractalImage canvas = FractalImage.create(width, height);

        Rect world = getRect();
        List<Transformation> transformations = getTransformations();
        Optional<List<Color>> colorsO = getOptionalColors();
        parseImagePath();

        var fractalConfig = new FractalConfig(
            transformations,
            colorsO,
            degreeOfRandomnessOfFractalCreation,
            complicateFlameShape,
            iterations,
            samples
        );

        var renderingArea = new RenderingAreaConfig(
            canvas,
            world
        );

        long start = System.currentTimeMillis();
        canvas = FractalRenderer.render(fractalConfig, renderingArea, threads);
        long end = System.currentTimeMillis();
        printer.println("Render time: " + (end - start) + " ms");

        if (isGammaCorrectionNecessary) {
            var gc = new GammaCorrectionProcessor(gamma);
            gc.process(canvas);
        }

        if (isSymmetryNecessary) {
            canvas.applySymmetry(horizontal);
        }

        if (theme.equals(BLACK_AND_WHITE)) {
            ImageUtils.saveBlackAndWhiteImage(canvas, Path.of(path), imageFormat);
        } else {
            ImageUtils.saveColorfulImage(canvas, Path.of(path), imageFormat);
        }

        printer.println("The fractal has been successfully saved to "  + path);
    }

    private void parseImagePath() {
        printer.println("input path");
        String filePath = reader.readLineAsString();

        if (filePath == null || filePath.isEmpty()) {
            path = DEFAULT_PATH;
            imageFormat = DEFAULT_IMAGE_FORMAT;
            return;
        }

        int dotIndex = filePath.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == filePath.length() - 1) {
            throw new IllegalArgumentException("Incorrect file format. No extension.");
        }

        String extension = filePath.substring(dotIndex + 1).toUpperCase();
        try {
            imageFormat = ImageFormat.valueOf(extension);
            path = filePath;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Image format '" + extension + "' unsupported.", e);
        }
    }

    private boolean getBoolInput(String message) {
        printer.println(message);
        printer.println("(This will slow the process down a bit)");
        printer.println("enter yes or no");

        while (true) {
            String line = reader.readLineAsString().toLowerCase();

            if (line.isEmpty()
                || line.equalsIgnoreCase("yes")
                || line.equalsIgnoreCase("y")) {
                return true;
            } else if (line.equalsIgnoreCase("no")
                || line.equalsIgnoreCase("n")) {
                return false;
            }

            printer.println("incorrect input");
        }
    }

    private <T extends Number> T getPositiveNumInput(String message, T defaultSize) {
        printer.println(message);

        while (true) {
            try {
                String line = reader.readLineAsString();
                if (line.isEmpty()) {
                    return defaultSize;
                }

                int size = Integer.parseInt(line);
                if (size < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                printer.println("incorrect input");
            }
        }
    }

    private Optional<List<Color>> getOptionalColors() {
        theme = getColorTheme();
        int colorDiversityIndex = getColorDiversityIndex();

        if (theme != BLACK_AND_WHITE) {
            return Optional.of(getRandomColors(colorDiversityIndex));
        }
        return Optional.empty();
    }

    private int getColorDiversityIndex() {
        printer.println("input color diversity index");

        while (true) {
            String line = reader.readLineAsString();

            if (line.isEmpty()) {
                return DEFAULT_COLOR_DIVERSITY_INDEX;
            }

            try {
                int colorDiversityIndex = Integer.parseInt(line);
                if (colorDiversityIndex < 0) {
                    throw new NumberFormatException();
                }
                return colorDiversityIndex;
            } catch (NumberFormatException e) {
                printer.println("invalid color diversity index");
            }
        }
    }

    private ColorTheme getColorTheme() {
        showAllColors();

        while (true) {
            String line = reader.readLineAsString();

            if (line.isEmpty()) {
                return RANDOM;
            }

            try {
                return ColorTheme.fromDisplayName(line);
            } catch (IllegalArgumentException e) {
                printer.println(e.getMessage());
            }
        }
    }

    private List<Color> getRandomColors(int colorDiversityIndex) {
        var colors = new ArrayList<Color>();
        for (int i = 0; i < colorDiversityIndex; i++) {
            colors.add(colorChain.getColor(new ColorRequest(theme)));
        }

        return colors;
    }

    private void showAllColors() {
        int i = 1;
        for (ColorTheme colorTheme : ColorTheme.values()) {
            printer.println(i + ") " + colorTheme.displayName());
            i++;
        }
        printer.println("\n");
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
            } catch (IllegalArgumentException e) {
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
        } catch (IllegalArgumentException e) {
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
            return Optional.of(DEFAULT_RECT);
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
