package backend.academy;

import backend.academy.fractal.flame.FractalImage;
import backend.academy.fractal.flame.FractalRenderer;
import backend.academy.fractal.flame.ImageFormat;
import backend.academy.fractal.flame.ImageUtils;
import backend.academy.fractal.flame.Point;
import backend.academy.fractal.flame.Rect;
import backend.academy.fractal.flame.Transformation;
import lombok.experimental.UtilityClass;
import java.nio.file.Path;
import java.util.List;

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

        List<Transformation> transformations = List.of(
                point -> new Point(Math.sin(point.getX()) * point.getY(), Math.sin(point.getY()) * point.getX()),
                point -> new Point(point.getX() * Math.sin(point.getY()), point.getY() * Math.sin(point.getX())),
                point -> new Point(point.getX() / (point.getX() * point.getX() + point.getY() * point.getY()),
                        point.getY() / (point.getX() * point.getX() + point.getY() * point.getY())),
                point -> new Point(Math.sin(point.getX() * point.getX() - point.getY() * point.getY()),
                        Math.sin(2 * point.getX() * point.getY())),
                point -> new Point(Math.tan(point.getX()), Math.tan(point.getY())),
                point -> new Point(point.getX() * Math.cos(point.getY()), point.getY() * Math.sin(point.getX()))
        );


        long start = System.currentTimeMillis();
        canvas = FractalRenderer.render(canvas, world, transformations, samples, iterations, seed, 8);
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

