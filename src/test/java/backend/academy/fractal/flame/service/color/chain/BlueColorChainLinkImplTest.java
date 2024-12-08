package backend.academy.fractal.flame.service.color.chain;

import backend.academy.fractal.flame.model.enums.ColorTheme;
import backend.academy.fractal.flame.model.records.ColorRequest;
import backend.academy.fractal.flame.service.color.BlueTheme;
import backend.academy.fractal.flame.service.color.chain.impl.BlueColorChainLinkImpl;
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
class BlueColorChainLinkImplTest {

    @InjectMocks
    private BlueColorChainLinkImpl blueColorChainLink;

    @Test
    void testGetColorWithBlueTheme() {
        try (MockedStatic<BlueTheme> mockedBlueTheme = mockStatic(BlueTheme.class)) {
            Color mockColor = new Color(10, 20, 240);
            mockedBlueTheme.when(BlueTheme::getRandomColor).thenReturn(mockColor);

            ColorRequest request = new ColorRequest(ColorTheme.BLUE);

            Optional<Color> result = blueColorChainLink.getColor(request);

            assertEquals(Optional.of(mockColor), result);
            mockedBlueTheme.verify(BlueTheme::getRandomColor);
        }
    }

    @Test
    void testGetColorWithNonBlueTheme() {
        ColorRequest request = new ColorRequest(ColorTheme.AZURE);

        Optional<Color> result = blueColorChainLink.getColor(request);

        assertEquals(Optional.empty(), result);
    }
}
