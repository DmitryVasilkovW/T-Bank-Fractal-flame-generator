package backend.academy.fractal.flame.service.color.chain;

import backend.academy.fractal.flame.model.records.ColorRequest;
import java.awt.Color;
import java.util.Optional;

public interface ColorChainLink {
    Optional<Color> getColor(ColorRequest request);
}
