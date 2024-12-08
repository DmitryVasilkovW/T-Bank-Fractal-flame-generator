package backend.academy.fractal.flame.service.transformation;

import backend.academy.fractal.flame.model.records.Point;
import backend.academy.fractal.flame.service.transformation.impl.HorseshoeTransformationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HorseshoeTransformationImplTest {

    @InjectMocks
    private HorseshoeTransformationImpl horseshoeTransformation;

    @Test
    void testApply() {
        Point inputPoint = new Point(1, 2);

        double r = Math.sqrt(inputPoint.x() * inputPoint.x() + inputPoint.y() * inputPoint.y());
        double expectedX = (inputPoint.x() - inputPoint.y()) * (inputPoint.x() + inputPoint.y()) / r;
        double expectedY = 2 * inputPoint.x() * inputPoint.y() / r;

        Point result = horseshoeTransformation.apply(inputPoint);

        assertEquals(expectedX, result.x(), 1e-9);
        assertEquals(expectedY, result.y(), 1e-9);
    }
}
