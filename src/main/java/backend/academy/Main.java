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
        // Параметры генерации
        int width = 1920;
        int height = 1080;
        int samples = 10_000_000; // Увеличим количество точек
        short iterations = 200;  // Больше итераций
        long seed = System.currentTimeMillis();
        Rect world = new Rect(-3, -3, 6, 6); // Широкая область мира

        // Создание пустого холста
        FractalImage canvas = FractalImage.create(width, height);

        // Список трансформаций
        List<Transformation> transformations = List.of(
                point -> new Point(Math.sin(point.getX()) * point.getY(), Math.sin(point.getY()) * point.getX()),
                // Sinusoidal
                point -> new Point(point.getX() * Math.sin(point.getY()), point.getY() * Math.sin(point.getX())),
                // Swirl
                point -> new Point(point.getX() / (point.getX() * point.getX() + point.getY() * point.getY()),
                        point.getY() / (point.getX() * point.getX() + point.getY() * point.getY())), // Spherical
                point -> new Point(Math.sin(point.getX() * point.getX() - point.getY() * point.getY()),
                        Math.sin(2 * point.getX() * point.getY())), // Complex Sin
                point -> new Point(Math.tan(point.getX()), Math.tan(point.getY())), // Tangent
                point -> new Point(point.getX() * Math.cos(point.getY()), point.getY() * Math.sin(point.getX())) // Curl
        );

        // Генерация фрактального изображения
        canvas = FractalRenderer.render(canvas, world, transformations, samples, iterations, seed);

        // Сохранение изображения
        try {
            ImageUtils.save(canvas, Path.of("fractal.png"), ImageFormat.PNG);
            System.out.println("Фрактал успешно сохранён в файл fractal.png");
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении изображения: " + e.getMessage());
        }
    }
}

