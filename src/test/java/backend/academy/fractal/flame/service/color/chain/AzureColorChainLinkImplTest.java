package backend.academy.fractal.flame.service.color.chain;

import backend.academy.fractal.flame.model.enums.ColorTheme;
import backend.academy.fractal.flame.model.records.ColorRequest;
import backend.academy.fractal.flame.service.color.AzureTheme;
import backend.academy.fractal.flame.service.color.chain.impl.AzureColorChainLinkImpl;
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
class AzureColorChainLinkImplTest {

    @InjectMocks
    private AzureColorChainLinkImpl azureColorChainLink;

    @Test
    void testGetColorWithAzureTheme() {
        try (MockedStatic<AzureTheme> mockedAzureTheme = mockStatic(AzureTheme.class)) {
            Color mockColor = new Color(50, 100, 200);
            mockedAzureTheme.when(AzureTheme::getRandomColor).thenReturn(mockColor);

            ColorRequest request = new ColorRequest(ColorTheme.AZURE);

            Optional<Color> result = azureColorChainLink.getColor(request);

            assertEquals(Optional.of(mockColor), result);
            mockedAzureTheme.verify(AzureTheme::getRandomColor);
        }
    }

    @Test
    void testGetColorWithNonAzureTheme() {
        ColorRequest request = new ColorRequest(ColorTheme.RANDOM);

        Optional<Color> result = azureColorChainLink.getColor(request);

        assertEquals(Optional.empty(), result);
    }
}
