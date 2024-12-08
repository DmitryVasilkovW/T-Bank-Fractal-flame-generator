package backend.academy.fractal.flame.service.render;

import backend.academy.fractal.flame.model.records.Point;
import backend.academy.fractal.flame.model.records.Rect;
import backend.academy.fractal.flame.model.records.RenderTaskConfig;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.impl.AffineTransformation;
import backend.academy.fractal.flame.service.util.FractalImage;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private AffineTransformation mockAffineTransformation;

    @Test
    void testCall() {
        int width = 100;
        int height = 100;
        int samples = 10;
        int iterPerSample = 5;

        when(mockWorld.contains(any(Point.class))).thenReturn(true);
        when(mockWorld.x()).thenReturn(0.0);
        when(mockWorld.y()).thenReturn(0.0);
        when(mockWorld.width()).thenReturn(100.0);
        when(mockWorld.height()).thenReturn(100.0);

        when(mockTransformation.apply(any(Point.class))).thenAnswer(invocation -> {
            Point input = invocation.getArgument(0);
            return new Point(input.x() + 1, input.y() + 1);
        });

        when(mockAffineTransformation.apply(any(Point.class))).thenAnswer(invocation -> {
            Point input = invocation.getArgument(0);
            return new Point(input.x() * 0.5, input.y() * 0.5);
        });

        List<Transformation> mockVariations = Collections.singletonList(mockTransformation);
        List<AffineTransformation> mockAffineTransformations = Collections.singletonList(mockAffineTransformation);
        Optional<List<Color>> mockColors = Optional.of(List.of(Color.RED, Color.GREEN, Color.BLUE));

        RenderTaskConfig config = new RenderTaskConfig(
            width,
            height,
            mockWorld,
            mockVariations,
            Optional.of(mockAffineTransformations),
            mockColors,
            samples,
            iterPerSample
        );

        RenderTask renderTask = new RenderTask(config);

        FractalImage result = renderTask.call();

        verify(mockWorld, atLeastOnce()).contains(any(Point.class));
        verify(mockTransformation, atLeastOnce()).apply(any(Point.class));
        verify(mockAffineTransformation, atLeastOnce()).apply(any(Point.class));
        assertNotNull(result);
    }
}
