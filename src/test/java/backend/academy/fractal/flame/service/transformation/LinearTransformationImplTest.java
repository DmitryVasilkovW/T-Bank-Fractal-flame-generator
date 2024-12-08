package backend.academy.fractal.flame.service.transformation;

import backend.academy.fractal.flame.model.records.Point;
import backend.academy.fractal.flame.service.transformation.impl.LinearTransformationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LinearTransformationImplTest {

    @InjectMocks
    private LinearTransformationImpl linearTransformation;

    @Test
    void testApply() {
        Point inputPoint = new Point(1, 2);

        Point result = linearTransformation.apply(inputPoint);

        assertEquals(inputPoint, result);
    }
}
