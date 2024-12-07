package backend.academy.fractal.flame.service.color.chain.impl;

import backend.academy.fractal.flame.model.records.ColorRequest;
import backend.academy.fractal.flame.service.color.AzureTheme;
import backend.academy.fractal.flame.service.color.chain.ColorChainLink;
import java.awt.Color;
import java.util.Optional;
import org.springframework.stereotype.Component;
import static backend.academy.fractal.flame.model.enums.ColorTheme.AZURE;

@Component
public class AzureColorChainLinkImpl implements ColorChainLink {

    @Override
    public Optional<Color> getColor(ColorRequest request) {
        if (request.theme().equals(AZURE)) {
            return Optional.of(AzureTheme.getRandomColor());
        }
        return Optional.empty();
    }
}
