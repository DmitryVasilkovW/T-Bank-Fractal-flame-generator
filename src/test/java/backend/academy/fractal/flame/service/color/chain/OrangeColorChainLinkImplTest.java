package backend.academy.fractal.flame.service.color.chain;

import backend.academy.fractal.flame.model.enums.ColorTheme;
import backend.academy.fractal.flame.model.records.ColorRequest;
import backend.academy.fractal.flame.service.color.OrangeTheme;
import backend.academy.fractal.flame.service.color.chain.impl.OrangeColorChainLinkImpl;
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
class OrangeColorChainLinkImplTest {

    @InjectMocks
    private OrangeColorChainLinkImpl orangeColorChainLink;

    @Test
    void testGetColorWithOrangeTheme() {
        try (MockedStatic<OrangeTheme> mockedOrangeTheme = mockStatic(OrangeTheme.class)) {
            Color mockColor = new Color(255, 165, 0);
            mockedOrangeTheme.when(OrangeTheme::getRandomColor).thenReturn(mockColor);

            ColorRequest request = new ColorRequest(ColorTheme.ORANGE);

            Optional<Color> result = orangeColorChainLink.getColor(request);

            assertEquals(Optional.of(mockColor), result);
            mockedOrangeTheme.verify(OrangeTheme::getRandomColor);
        }
    }

    @Test
    void testGetColorWithNonOrangeTheme() {
        ColorRequest request = new ColorRequest(ColorTheme.BLUE);

        Optional<Color> result = orangeColorChainLink.getColor(request);

        assertEquals(Optional.empty(), result);
    }
}
