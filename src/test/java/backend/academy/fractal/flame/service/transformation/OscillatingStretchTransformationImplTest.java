package backend.academy.fractal.flame.service.transformation;

import backend.academy.fractal.flame.model.records.Point;
import backend.academy.fractal.flame.service.transformation.impl.OscillatingStretchTransformationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OscillatingStretchTransformationImplTest {

    @InjectMocks
    private OscillatingStretchTransformationImpl oscillatingStretchTransformation;

    @Test
    void testApply() {
        Point inputPoint = new Point(1, 2);

        double expectedX = inputPoint.x() * Math.sin(inputPoint.y());
        double expectedY = inputPoint.y() * Math.sin(inputPoint.x());

        Point result = oscillatingStretchTransformation.apply(inputPoint);

        assertEquals(expectedX, result.x(), 1e-9);
        assertEquals(expectedY, result.y(), 1e-9);
    }
}
