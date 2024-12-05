package backend.academy.fractal.flame.service.render;

import backend.academy.fractal.flame.model.Pixel;
import backend.academy.fractal.flame.model.Point;
import backend.academy.fractal.flame.model.Rect;
import backend.academy.fractal.flame.service.FractalImage;
import backend.academy.fractal.flame.service.Transformation;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RenderTaskTest {

    private FractalImage mockImage;
    private Rect mockWorld;
    private List<Transformation> mockVariations;

    @BeforeEach
    void setup() {
        mockImage = mock(FractalImage.class);
        mockWorld = mock(Rect.class);
        Transformation mockTransformation = mock(Transformation.class);
        mockVariations = Collections.singletonList(mockTransformation);

        when(mockImage.contains(anyInt(), anyInt())).thenReturn(true);

        Pixel mockPixel = mock(Pixel.class);
        when(mockImage.pixel(anyInt(), anyInt())).thenReturn(mockPixel);

        when(mockWorld.contains(any(Point.class))).thenReturn(true);

        when(mockTransformation.apply(any(Point.class))).thenAnswer(invocation -> {
            Point input = invocation.getArgument(0);
            return new Point(input.x() + 1, input.y() + 1);
        });
    }

    @Test
    void testCall() {
        when(mockWorld.contains(any(Point.class))).thenReturn(true);
        when(mockWorld.x()).thenReturn(0.0);
        when(mockWorld.y()).thenReturn(0.0);
        when(mockWorld.width()).thenReturn(100.0);
        when(mockWorld.height()).thenReturn(100.0);

        when(mockImage.contains(anyInt(), anyInt())).thenReturn(true);
        Pixel mockPixel = mock(Pixel.class);
        when(mockImage.pixel(anyInt(), anyInt())).thenReturn(mockPixel);

        RenderTask renderTask = new RenderTask(
            100, 100, mockWorld, mockVariations, 10, (short) 5
        );

        FractalImage result = renderTask.call();

        verify(mockWorld, atLeastOnce()).contains(any(Point.class));

        assertNotNull(result);
    }

}
