package backend.academy.fractal.flame.service.transformation.chain.impl;

import backend.academy.fractal.flame.model.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.TransformationChainLink;
import backend.academy.fractal.flame.service.transformation.impl.SinusoidalTransformationImpl;
import java.util.Optional;
import org.springframework.stereotype.Component;
import static backend.academy.fractal.flame.model.enums.Transformations.SINUSOIDAL;

@Component
public class SinusoidalTransformationChainLinkImpl implements TransformationChainLink {

    @Override
    public Optional<Transformation> tryToGetTransformation(TransformationRequest request) {
        if (request.transformation().equals(SINUSOIDAL)) {
            return Optional.of(new SinusoidalTransformationImpl());
        }
        return Optional.empty();
    }
}
