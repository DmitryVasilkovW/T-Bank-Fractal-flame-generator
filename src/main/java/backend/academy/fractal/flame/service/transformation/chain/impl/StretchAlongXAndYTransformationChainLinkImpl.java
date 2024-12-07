package backend.academy.fractal.flame.service.transformation.chain.impl;

import backend.academy.fractal.flame.model.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.TransformationChainLink;
import backend.academy.fractal.flame.service.transformation.impl.StretchAlongXAndYTransformationImpl;
import java.util.Optional;
import org.springframework.stereotype.Component;
import static backend.academy.fractal.flame.model.enums.Transformations.STRENGTH_ALONG_X_AND_Y;

@Component
public class StretchAlongXAndYTransformationChainLinkImpl implements TransformationChainLink {

    @Override
    public Optional<Transformation> tryToGetTransformation(TransformationRequest request) {
        if (request.transformation().equals(STRENGTH_ALONG_X_AND_Y)) {
            return Optional.of(new StretchAlongXAndYTransformationImpl());
        }
        return Optional.empty();
    }
}
