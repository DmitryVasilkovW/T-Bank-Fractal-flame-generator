package backend.academy.fractal.flame.service.transformation;

import backend.academy.fractal.flame.model.records.Point;
import backend.academy.fractal.flame.service.transformation.impl.SwirlTransformationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SwirlTransformationImplTest {

    @InjectMocks
    private SwirlTransformationImpl swirlTransformation;

    @Test
    void testApply() {
        Point inputPoint = new Point(1, 2);

        double r2 = inputPoint.x() * inputPoint.x() + inputPoint.y() * inputPoint.y();
        double expectedX = inputPoint.x() * Math.sin(r2) - inputPoint.y() * Math.cos(r2);
        double expectedY = inputPoint.x() * Math.cos(r2) + inputPoint.y() * Math.sin(r2);

        Point result = swirlTransformation.apply(inputPoint);

        assertEquals(expectedX, result.x(), 1e-9);
        assertEquals(expectedY, result.y(), 1e-9);
    }
}
