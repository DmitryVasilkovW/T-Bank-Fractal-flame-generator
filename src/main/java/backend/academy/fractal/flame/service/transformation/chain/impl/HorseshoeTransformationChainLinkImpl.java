package backend.academy.fractal.flame.service.transformation.chain.impl;

import backend.academy.fractal.flame.model.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.TransformationChainLink;
import backend.academy.fractal.flame.service.transformation.impl.HorseshoeTransformationImpl;
import java.util.Optional;
import org.springframework.stereotype.Component;
import static backend.academy.fractal.flame.model.enums.Transformations.HORSESHOE;

@Component
public class HorseshoeTransformationChainLinkImpl implements TransformationChainLink {

    @Override
    public Optional<Transformation> tryToGetTransformation(TransformationRequest request) {
        if (request.transformation().equals(HORSESHOE)) {
            return Optional.of(new HorseshoeTransformationImpl());
        }
        return Optional.empty();
    }
}
