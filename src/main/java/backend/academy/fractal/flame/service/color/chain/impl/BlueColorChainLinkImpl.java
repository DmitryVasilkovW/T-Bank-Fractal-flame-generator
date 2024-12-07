package backend.academy.fractal.flame.service.color.chain.impl;

import backend.academy.fractal.flame.model.records.ColorRequest;
import backend.academy.fractal.flame.service.color.BlueTheme;
import backend.academy.fractal.flame.service.color.chain.ColorChainLink;
import java.awt.Color;
import java.util.Optional;
import org.springframework.stereotype.Component;
import static backend.academy.fractal.flame.model.enums.ColorTheme.BLUE;

@Component
public class BlueColorChainLinkImpl implements ColorChainLink {

    @Override
    public Optional<Color> getColor(ColorRequest request) {
        if (request.theme().equals(BLUE)) {
            return Optional.of(BlueTheme.getRandomColor());
        }
        return Optional.empty();
    }
}
