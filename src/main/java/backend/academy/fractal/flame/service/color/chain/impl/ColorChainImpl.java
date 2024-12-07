package backend.academy.fractal.flame.service.color.chain.impl;

import backend.academy.fractal.flame.model.records.ColorRequest;
import backend.academy.fractal.flame.service.color.RandomTheme;
import backend.academy.fractal.flame.service.color.chain.ColorChain;
import backend.academy.fractal.flame.service.color.chain.ColorChainLink;
import java.awt.Color;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ColorChainImpl implements ColorChain {
    private final List<ColorChainLink> handlers;

    @Autowired
    public ColorChainImpl(List<ColorChainLink> handlers) {
        this.handlers = handlers;
    }

    @Override
    public Color getColor(ColorRequest request) {
        for (ColorChainLink handler : handlers) {
            Optional<Color> colorO = handler.getColor(request);
            if (colorO.isPresent()) {
                return colorO.get();
            }
        }

        return RandomTheme.getRandomColor();
    }
}
