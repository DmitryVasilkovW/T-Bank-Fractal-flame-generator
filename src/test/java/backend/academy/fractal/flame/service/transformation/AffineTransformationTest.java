package backend.academy.fractal.flame.service.transformation;

import backend.academy.fractal.flame.model.records.AffineCoefficient;
import backend.academy.fractal.flame.model.records.Point;
import backend.academy.fractal.flame.service.transformation.impl.AffineTransformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AffineTransformationTest {

    @Mock
    private AffineCoefficient affineCoefficient;

    @InjectMocks
    private AffineTransformation affineTransformation;

    @Test
    void testApply() {
        Point inputPoint = new Point(1, 2);

        when(affineCoefficient.a()).thenReturn(1.0);
        when(affineCoefficient.b()).thenReturn(2.0);
        when(affineCoefficient.c()).thenReturn(3.0);
        when(affineCoefficient.d()).thenReturn(4.0);
        when(affineCoefficient.e()).thenReturn(5.0);
        when(affineCoefficient.f()).thenReturn(6.0);

        Point result = affineTransformation.apply(inputPoint);

        assertNotNull(result);
        assertEquals(1.0 + 1 * 2.0 + 2 * 3.0, result.x());
        assertEquals(4.0 + 1 * 5.0 + 2 * 6.0, result.y());

        verify(affineCoefficient).a();
        verify(affineCoefficient).b();
        verify(affineCoefficient).c();
        verify(affineCoefficient).d();
        verify(affineCoefficient).e();
        verify(affineCoefficient).f();
    }
}

