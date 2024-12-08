package backend.academy.fractal.flame.service.color.chain;

import backend.academy.fractal.flame.model.enums.ColorTheme;
import backend.academy.fractal.flame.model.records.ColorRequest;
import backend.academy.fractal.flame.service.color.RandomTheme;
import backend.academy.fractal.flame.service.color.chain.impl.RandomColorChainLinkImpl;
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
class RandomColorChainLinkImplTest {

    @InjectMocks
    private RandomColorChainLinkImpl randomColorChainLink;

    @Test
    void testGetColorWithRandomTheme() {
        try (MockedStatic<RandomTheme> mockedRandomTheme = mockStatic(RandomTheme.class)) {
            Color mockColor = new Color(128, 128, 128);
            mockedRandomTheme.when(RandomTheme::getRandomColor).thenReturn(mockColor);

            ColorRequest request = new ColorRequest(ColorTheme.RANDOM);

            Optional<Color> result = randomColorChainLink.getColor(request);

            assertEquals(Optional.of(mockColor), result);
            mockedRandomTheme.verify(RandomTheme::getRandomColor);
        }
    }

    @Test
    void testGetColorWithNonRandomTheme() {
        ColorRequest request = new ColorRequest(ColorTheme.BLUE);

        Optional<Color> result = randomColorChainLink.getColor(request);

        assertEquals(Optional.empty(), result);
    }
}
