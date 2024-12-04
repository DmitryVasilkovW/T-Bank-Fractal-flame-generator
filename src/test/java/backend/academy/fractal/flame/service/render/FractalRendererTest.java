package backend.academy.fractal.flame.service.render;

import backend.academy.fractal.flame.model.Rect;
import backend.academy.fractal.flame.service.FractalImage;
import backend.academy.fractal.flame.service.Transformation;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FractalRendererTest {

    @Mock
    private FractalImage canvas;

    @Mock
    private Rect world;

    @Mock
    private Transformation transformation;

    @Mock
    private ExecutorService executorService;

    @InjectMocks
    private RenderTask renderTask;

    private List<Transformation> variations;

    @BeforeEach
    void setUp() {
        variations = List.of(transformation);
    }

    @Test
    void testRender() throws Exception {
        when(executorService.invokeAll(anyList()))
            .thenReturn(Arrays.asList(
                CompletableFuture.completedFuture(mock(FractalImage.class)),
                CompletableFuture.completedFuture(mock(FractalImage.class))
            ));

        try (MockedStatic<Executors> mockedExecutors = mockStatic(Executors.class)) {
            mockedExecutors.when(() -> Executors.newFixedThreadPool(anyInt())).thenReturn(executorService);

            int samples = 100;
            short iterPerSample = 10;
            int threads = 2;
            FractalImage result = FractalRenderer.render(canvas, world, variations, samples, iterPerSample, threads);

            verify(executorService).invokeAll(anyList());
            verify(canvas, times(2)).merge(any(FractalImage.class));
            assertNotNull(result);
        }
    }
}

