package backend.academy.fractal.flame.service.transformation;

import backend.academy.fractal.flame.model.records.Point;
import backend.academy.fractal.flame.service.transformation.impl.SinusoidalTransformationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SinusoidalTransformationImplTest {

    @InjectMocks
    private SinusoidalTransformationImpl sinusoidalTransformation;

    @Test
    void testApply() {
        Point inputPoint = new Point(1, 2);

        double expectedX = Math.sin(inputPoint.x());
        double expectedY = Math.sin(inputPoint.y());

        Point result = sinusoidalTransformation.apply(inputPoint);

        assertEquals(expectedX, result.x(), 1e-9);
        assertEquals(expectedY, result.y(), 1e-9);
    }
}
