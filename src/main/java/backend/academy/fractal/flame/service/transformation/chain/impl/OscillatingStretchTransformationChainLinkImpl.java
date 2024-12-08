package backend.academy.fractal.flame.service.transformation.chain.impl;

import backend.academy.fractal.flame.model.records.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.TransformationChainLink;
import backend.academy.fractal.flame.service.transformation.impl.OscillatingStretchTransformationImpl;
import java.util.Optional;
import org.springframework.stereotype.Component;
import static backend.academy.fractal.flame.model.enums.Transformations.OSCILLATING;

@Component
public class OscillatingStretchTransformationChainLinkImpl implements TransformationChainLink {

    @Override
    public Optional<Transformation> tryToGetTransformation(TransformationRequest request) {
        if (request.transformation().equals(OSCILLATING)) {
            return Optional.of(new OscillatingStretchTransformationImpl());
        }
        return Optional.empty();
    }
}
