package backend.academy.fractal.flame.service.transformation.chain.impl;

import backend.academy.fractal.flame.model.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.TransformationChainLink;
import backend.academy.fractal.flame.service.transformation.impl.TangentWarpTransformationImpl;
import java.util.Optional;
import org.springframework.stereotype.Component;
import static backend.academy.fractal.flame.model.enums.Transformations.TANGENT_WARP;

@Component
public class TangentWarpTransformationChainLinkImpl implements TransformationChainLink {

    @Override
    public Optional<Transformation> tryToGetTransformation(TransformationRequest request) {
        if (request.transformation().equals(TANGENT_WARP)) {
            return Optional.of(new TangentWarpTransformationImpl());
        }
        return Optional.empty();
    }
}
