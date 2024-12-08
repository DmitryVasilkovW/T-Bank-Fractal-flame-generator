package backend.academy.fractal.flame.service.transformation.chain.impl;

import backend.academy.fractal.flame.model.records.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.TransformationChainLink;
import backend.academy.fractal.flame.service.transformation.impl.SinusoidalWarpTransformationImpl;
import java.util.Optional;
import org.springframework.stereotype.Component;
import static backend.academy.fractal.flame.model.enums.Transformations.SINUSOIDAL_WARP;

@Component
public class SinusoidalWarpTransformationChainLinkImpl implements TransformationChainLink {

    @Override
    public Optional<Transformation> tryToGetTransformation(TransformationRequest request) {
        if (request.transformation().equals(SINUSOIDAL_WARP)) {
            return Optional.of(new SinusoidalWarpTransformationImpl());
        }
        return Optional.empty();
    }
}
