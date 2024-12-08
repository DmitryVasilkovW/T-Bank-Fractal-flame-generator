package backend.academy.fractal.flame.service.app;

import backend.academy.fractal.flame.model.Pixel;
import backend.academy.fractal.flame.service.color.chain.ColorChain;
import backend.academy.fractal.flame.service.io.Printer;
import backend.academy.fractal.flame.service.io.Reader;
import backend.academy.fractal.flame.service.render.FractalRenderer;
import backend.academy.fractal.flame.service.util.FractalImage;
import java.awt.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {

    @Mock
    private Printer printer;
    @Mock
    private Reader reader;
    @Mock
    private ColorChain colorChain;
    @Mock
    private FractalImage mockCanvas;

    @InjectMocks
    private Application application;

    @BeforeEach
    void setUp() {
        int width = 10;
        int height = 10;

        Pixel[][] pixelData = new Pixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Pixel pixelMock = mock(Pixel.class);
                when(pixelMock.color()).thenReturn(Color.BLACK);
                when(pixelMock.hitCount()).thenReturn(239);
                pixelData[i][j] = pixelMock;
            }
        }
        when(mockCanvas.data()).thenReturn(pixelData);
        when(mockCanvas.width()).thenReturn(width);
        when(mockCanvas.height()).thenReturn(height);
    }

    @Test
    void testRun() throws Exception {
        when(reader.readLineAsString())
            .thenReturn("90")
            .thenReturn("80")
            .thenReturn("10000")
            .thenReturn("200")
            .thenReturn("4")
            .thenReturn("6")
            .thenReturn("1.8")
            .thenReturn("n")
            .thenReturn("n")
            .thenReturn("n")
            .thenReturn("n")
            .thenReturn("");

        when(colorChain.getColor(any())).thenReturn(Color.RED);

        try (var mockedRenderer = mockStatic(FractalRenderer.class)) {
            mockedRenderer.when(() -> FractalRenderer.render(any(), any(), anyInt()))
                .thenReturn(mockCanvas);

            application.run();

            mockedRenderer.verify(() -> FractalRenderer.render(any(), any(), anyInt()), times(1));
        }

        verify(mockCanvas, times(1)).applySymmetry(true);
        verify(printer, atLeastOnce()).println(anyString());
    }
}
