package backend.academy.fractal.flame.service.color.chain;

import backend.academy.fractal.flame.model.enums.ColorTheme;
import backend.academy.fractal.flame.model.records.ColorRequest;
import backend.academy.fractal.flame.service.color.PinkTheme;
import backend.academy.fractal.flame.service.color.chain.impl.PinkColorChainLinkImpl;
import java.awt.Color;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class PinkColorChainLinkImplTest {

    @InjectMocks
    private PinkColorChainLinkImpl pinkColorChainLink;

    @Test
    void testGetColorWithPinkTheme() {
        try (MockedStatic<PinkTheme> mockedPinkTheme = mockStatic(PinkTheme.class)) {
            Color mockColor = new Color(255, 105, 180);
            mockedPinkTheme.when(PinkTheme::getRandomColor).thenReturn(mockColor);

            ColorRequest request = new ColorRequest(ColorTheme.PINK);

            Optional<Color> result = pinkColorChainLink.getColor(request);

            assertEquals(Optional.of(mockColor), result);
            mockedPinkTheme.verify(PinkTheme::getRandomColor);
        }
    }

    @Test
    void testGetColorWithNonPinkTheme() {
        ColorRequest request = new ColorRequest(ColorTheme.BLUE);

        Optional<Color> result = pinkColorChainLink.getColor(request);

        assertEquals(Optional.empty(), result);
    }
}
