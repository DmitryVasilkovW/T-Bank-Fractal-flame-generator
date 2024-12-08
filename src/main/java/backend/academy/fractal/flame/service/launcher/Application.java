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

    private static final String PROMPT_WIDTH = "Input width";
    private static final String PROMPT_HEIGHT = "Input height";
    private static final String PROMPT_SAMPLES = "Input samples";
    private static final String PROMPT_ITERATIONS = "Input iterations";
    private static final String PROMPT_THREADS = "Input threads";
    private static final String PROMPT_RANDOMNESS = "Input degree of fractal creation";
    private static final String PROMPT_GAMMA = "Input gamma";
    private static final String PROMPT_COMPLEX_SHAPE = "Is it necessary to make a fractal with a more complex shape?";
    private static final String PROMPT_GAMMA_CORRECTION = "If gamma correction is necessary?";
    private static final String PROMPT_SYMMETRY = "If symmetry is necessary?";
    private static final String PROMPT_HORIZONTAL_SYMMETRY = "Need horizontal symmetry?";
    private static final String PROMPT_IMAGE_PATH = "Input path";
    private static final String PROMPT_COLOR_DIVERSITY_INDEX = "Input color diversity index";
    private static final String PROMPT_RENDERING_AREA = "Input rendering area (format: x y width height):";
    private static final String PROMPT_TRANSFORMATION = "Enter a transformation (or leave empty to finish):";
    private static final String PROMPT_COLORS = "Enter a color (or leave empty to finish):";
    private static final String PROMPT_YES_NO = "Enter yes or no";
    private static final String PROMPT_AVAILABLE_COLORS = "Available color themes:";
    private static final String PROMPT_AVAILABLE_TRANSFORMATIONS = "Available transformations:";

    private static final String ERROR_INCORRECT_INPUT = "Incorrect input";
    private static final String ERROR_INVALID_COLOR_DIVERSITY = "Invalid color diversity index";
    private static final String ERROR_INCORRECT_DATA_FORMAT = "Incorrect data format";
    private static final String ERROR_UNSUPPORTED_IMAGE_FORMAT = "Image format '%s' unsupported.";
    private static final String ERROR_NO_EXTENSION = "Incorrect file format. No extension.";

    private static final String SUCCESS_RENDER_TIME = "Render time: %d ms";
    private static final String SUCCESS_SAVE_IMAGE = "The fractal has been successfully saved to %s";

    private static final int INDEX_OF_X = 0;
    private static final int INDEX_OF_Y = 1;
    private static final int INDEX_OF_WIDTH = 2;
    private static final int INDEX_OF_HEIGHT = 3;

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
        int width = getPositiveNumInput(PROMPT_WIDTH, DEFAULT_WIDTH);
        int height = getPositiveNumInput(PROMPT_HEIGHT, DEFAULT_HEIGHT);
        int samples = getPositiveNumInput(PROMPT_SAMPLES, DEFAULT_SAMPLES);
        int iterations = getPositiveNumInput(PROMPT_ITERATIONS, DEFAULT_ITERATIONS);
        int threads = getPositiveNumInput(PROMPT_THREADS, DEFAULT_THREADS);
        int degreeOfRandomnessOfFractalCreation = getPositiveNumInput(
            PROMPT_RANDOMNESS, DEFAULT_RANDOMNESS_DEGREE);
        double gamma = getPositiveNumInput(PROMPT_GAMMA, DEFAULT_GAMMA);
        boolean complicateFlameShape = getBoolInput(PROMPT_COMPLEX_SHAPE);
        boolean isGammaCorrectionNecessary = getBoolInput(PROMPT_GAMMA_CORRECTION);
        boolean isSymmetryNecessary = getBoolInput(PROMPT_SYMMETRY);
        boolean horizontal = getBoolInput(PROMPT_HORIZONTAL_SYMMETRY);

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
        printer.println(String.format(SUCCESS_RENDER_TIME, (end - start)));

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

        printer.println(String.format(SUCCESS_SAVE_IMAGE, path));
    }

    private void parseImagePath() {
        printer.println(PROMPT_IMAGE_PATH);
        String filePath = reader.readLineAsString();

        if (filePath == null || filePath.isEmpty()) {
            path = DEFAULT_PATH;
            imageFormat = DEFAULT_IMAGE_FORMAT;
            return;
        }

        int dotIndex = filePath.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == filePath.length() - 1) {
            throw new IllegalArgumentException(ERROR_NO_EXTENSION);
        }

        String extension = filePath.substring(dotIndex + 1).toUpperCase();
        try {
            imageFormat = ImageFormat.valueOf(extension);
            path = filePath;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(ERROR_UNSUPPORTED_IMAGE_FORMAT, extension), e);
        }
    }

    private boolean getBoolInput(String message) {
        printer.println(message);
        printer.println(PROMPT_YES_NO);

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

            printer.println(ERROR_INCORRECT_INPUT);
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
                printer.println(ERROR_INCORRECT_INPUT);
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
        printer.println(PROMPT_COLOR_DIVERSITY_INDEX);

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
                printer.println(ERROR_INVALID_COLOR_DIVERSITY);
            }
        }
    }

    private ColorTheme getColorTheme() {
        printer.println(PROMPT_COLORS);
        printer.println(PROMPT_AVAILABLE_COLORS);
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
        printer.println(PROMPT_TRANSFORMATION);
        printer.println(PROMPT_AVAILABLE_TRANSFORMATIONS);
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
        printer.println(PROMPT_RENDERING_AREA);
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
            int x = Integer.parseInt(parts[INDEX_OF_X]);
            int y = Integer.parseInt(parts[INDEX_OF_Y]);
            int w = Integer.parseInt(parts[INDEX_OF_WIDTH]);
            int h = Integer.parseInt(parts[INDEX_OF_HEIGHT]);

            return Optional.of(new Rect(x, y, w, h));
        } catch (Exception e) {
            printer.println(ERROR_INCORRECT_DATA_FORMAT);
            return Optional.empty();
        }
    }
}
