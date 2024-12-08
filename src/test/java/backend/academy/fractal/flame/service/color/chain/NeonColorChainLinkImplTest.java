package backend.academy.fractal.flame.service.color.chain;

import backend.academy.fractal.flame.model.enums.ColorTheme;
import backend.academy.fractal.flame.model.records.ColorRequest;
import backend.academy.fractal.flame.service.color.NeonTheme;
import backend.academy.fractal.flame.service.color.chain.impl.NeonColorChainLinkImpl;
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
class NeonColorChainLinkImplTest {

    @InjectMocks
    private NeonColorChainLinkImpl neonColorChainLink;

    @Test
    void testGetColorWithNeonTheme() {
        try (MockedStatic<NeonTheme> mockedNeonTheme = mockStatic(NeonTheme.class)) {
            Color mockColor = new Color(255, 0, 255);
            mockedNeonTheme.when(NeonTheme::getRandomColor).thenReturn(mockColor);

            ColorRequest request = new ColorRequest(ColorTheme.NEON);

            Optional<Color> result = neonColorChainLink.getColor(request);

            assertEquals(Optional.of(mockColor), result);
            mockedNeonTheme.verify(NeonTheme::getRandomColor);
        }
    }

    @Test
    void testGetColorWithNonNeonTheme() {
        ColorRequest request = new ColorRequest(ColorTheme.AZURE);

        Optional<Color> result = neonColorChainLink.getColor(request);

        assertEquals(Optional.empty(), result);
    }
}
