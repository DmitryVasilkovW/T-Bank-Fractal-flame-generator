package backend.academy.fractal.flame.service.color.chain;

import backend.academy.fractal.flame.model.enums.ColorTheme;
import backend.academy.fractal.flame.model.records.ColorRequest;
import backend.academy.fractal.flame.service.color.RandomTheme;
import backend.academy.fractal.flame.service.color.chain.ColorChainLink;
import backend.academy.fractal.flame.service.color.chain.impl.ColorChainImpl;
import java.awt.Color;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ColorChainImplTest {

    @Mock
    private ColorChainLink firstHandler;

    @Mock
    private ColorChainLink secondHandler;

    private ColorChainImpl colorChain;

    @BeforeEach
    void setUp() {
        colorChain = new ColorChainImpl(List.of(firstHandler, secondHandler));
    }

    @Test
    void testGetColorFromFirstHandler() {
        Color mockColor = new Color(100, 150, 200);
        when(firstHandler.getColor(any(ColorRequest.class))).thenReturn(Optional.of(mockColor));

        ColorRequest request = new ColorRequest(ColorTheme.BLUE);
        Color result = colorChain.getColor(request);

        assertEquals(mockColor, result);
        verify(firstHandler).getColor(request);
        verifyNoInteractions(secondHandler);
    }

    @Test
    void testGetColorFromSecondHandler() {
        Color mockColor = new Color(50, 80, 150);
        when(firstHandler.getColor(any(ColorRequest.class))).thenReturn(Optional.empty());
        when(secondHandler.getColor(any(ColorRequest.class))).thenReturn(Optional.of(mockColor));

        ColorRequest request = new ColorRequest(ColorTheme.AZURE);
        Color result = colorChain.getColor(request);

        assertEquals(mockColor, result);
        verify(firstHandler).getColor(request);
        verify(secondHandler).getColor(request);
    }

    @Test
    void testGetColorFallbackToRandomTheme() {
        when(firstHandler.getColor(any(ColorRequest.class))).thenReturn(Optional.empty());
        when(secondHandler.getColor(any(ColorRequest.class))).thenReturn(Optional.empty());

        Color mockFallbackColor = new Color(0, 0, 0);
        try (MockedStatic<RandomTheme> mockedRandomTheme = mockStatic(RandomTheme.class)) {
            mockedRandomTheme.when(RandomTheme::getRandomColor).thenReturn(mockFallbackColor);

            ColorRequest request = new ColorRequest(ColorTheme.RANDOM);
            Color result = colorChain.getColor(request);

            assertEquals(mockFallbackColor, result);
            verify(firstHandler).getColor(request);
            verify(secondHandler).getColor(request);
            mockedRandomTheme.verify(RandomTheme::getRandomColor);
        }
    }
}
