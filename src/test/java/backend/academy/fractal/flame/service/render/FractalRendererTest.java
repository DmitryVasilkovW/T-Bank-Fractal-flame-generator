package backend.academy.fractal.flame.service.render;

import backend.academy.fractal.flame.model.records.FractalConfig;
import backend.academy.fractal.flame.model.records.Rect;
import backend.academy.fractal.flame.model.records.RenderingAreaConfig;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.util.FractalImage;
import java.awt.Color;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FractalRendererTest {

    @Mock
    private FractalConfig fractalConfig;

    @Mock
    private RenderingAreaConfig renderingAreaConfig;

    @Mock
    private FractalImage canvas;

    @Mock
    private Rect world;

    @Mock
    private Transformation transformation;

    @Mock
    private ExecutorService executorService;

    @Test
    void testRender() throws Exception {
        when(fractalConfig.degreeOfRandomnessOfFractalCreation()).thenReturn(5);
        when(fractalConfig.samples()).thenReturn(100);
        when(fractalConfig.iterPerSample()).thenReturn(10);
        when(fractalConfig.complicateFlameShape()).thenReturn(false);
        when(fractalConfig.variations()).thenReturn(List.of(transformation));
        when(fractalConfig.colors()).thenReturn(Optional.of(List.of(Color.RED, Color.BLUE)));

        when(renderingAreaConfig.canvas()).thenReturn(canvas);
        when(renderingAreaConfig.world()).thenReturn(world);

        when(executorService.invokeAll(anyList()))
            .thenReturn(List.of(
                CompletableFuture.completedFuture(mock(FractalImage.class)),
                CompletableFuture.completedFuture(mock(FractalImage.class))
            ));

        try (MockedStatic<Executors> mockedExecutors = mockStatic(Executors.class)) {
            mockedExecutors.when(() -> Executors.newFixedThreadPool(anyInt())).thenReturn(executorService);

            int threads = 2;
            FractalImage result = FractalRenderer.render(fractalConfig, renderingAreaConfig, threads);

            verify(executorService).invokeAll(anyList());
            verify(canvas, times(2)).merge(any(FractalImage.class));
            assertNotNull(result);
        }
    }
}


