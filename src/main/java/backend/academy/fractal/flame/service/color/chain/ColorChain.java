package backend.academy.fractal.flame.service.color.chain;

import backend.academy.fractal.flame.model.records.ColorRequest;
import java.awt.Color;

public interface ColorChain {
    Color getColor(ColorRequest request);
}
