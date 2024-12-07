package backend.academy.fractal.flame.service.transformation.chain.impl;

import backend.academy.fractal.flame.model.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.TransformationChainLink;
import backend.academy.fractal.flame.service.transformation.impl.ComplexWaveTransformationImpl;
import java.util.Optional;
import org.springframework.stereotype.Component;
import static backend.academy.fractal.flame.model.enums.Transformations.COMPLEX_WAVE;

@Component
public class ComplexWaveTransformationChainLinkImpl implements TransformationChainLink {

    @Override
    public Optional<Transformation> tryToGetTransformation(TransformationRequest request) {
        if (request.transformation().equals(COMPLEX_WAVE)) {
            return Optional.of(new ComplexWaveTransformationImpl());
        }
        return Optional.empty();
    }
}
