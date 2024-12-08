package backend.academy.fractal.flame.service.transformation;

import backend.academy.fractal.flame.model.records.Point;
import backend.academy.fractal.flame.service.transformation.impl.SphericalTransformationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SphericalTransformationImplTest {

    @InjectMocks
    private SphericalTransformationImpl sphericalTransformation;

    @Test
    void testApply() {
        Point inputPoint = new Point(1, 2);

        double r2 = inputPoint.x() * inputPoint.x() + inputPoint.y() * inputPoint.y();
        double expectedX = inputPoint.x() / r2;
        double expectedY = inputPoint.y() / r2;

        Point result = sphericalTransformation.apply(inputPoint);

        assertEquals(expectedX, result.x(), 1e-9);
        assertEquals(expectedY, result.y(), 1e-9);
    }
}
