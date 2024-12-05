package backend.academy.fractal.flame.service.render;

import backend.academy.fractal.flame.model.Point;
import backend.academy.fractal.flame.model.Rect;
import backend.academy.fractal.flame.service.FractalImage;
import backend.academy.fractal.flame.service.Transformation;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RenderTaskTest {

    @Mock
    private Rect mockWorld;

    @Mock
    private Transformation mockTransformation;

    @Mock
    private RenderTask renderTask;

    @Test
    void testCall() {
        when(mockWorld.contains(any(Point.class))).thenReturn(true);
        when(mockWorld.x()).thenReturn(0.0);
        when(mockWorld.y()).thenReturn(0.0);
        when(mockWorld.width()).thenReturn(100.0);
        when(mockWorld.height()).thenReturn(100.0);

        when(mockTransformation.apply(any(Point.class))).thenAnswer(invocation -> {
            Point input = invocation.getArgument(0);
            return new Point(input.x() + 1, input.y() + 1);
        });

        List<Transformation> mockVariations = Collections.singletonList(mockTransformation);

        renderTask = new RenderTask(
            100, 100, mockWorld, mockVariations, 10, (short) 5
        );

        FractalImage result = renderTask.call();

        verify(mockWorld, atLeastOnce()).contains(any(Point.class));
        assertNotNull(result);
    }
}
