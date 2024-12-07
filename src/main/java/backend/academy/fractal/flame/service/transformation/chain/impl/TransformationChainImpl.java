package backend.academy.fractal.flame.service.transformation.chain.impl;

import backend.academy.fractal.flame.model.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.TransformationChain;
import backend.academy.fractal.flame.service.transformation.chain.TransformationChainLink;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransformationChainImpl implements TransformationChain {
    private final List<TransformationChainLink> transformations;
    private static final String ERROR_MESSAGE = "No enum constant with display name: ";

    @Autowired
    public TransformationChainImpl(List<TransformationChainLink> transformations) {
        this.transformations = transformations;
    }

    @Override
    public Transformation getTransformations(TransformationRequest request) {
        for (TransformationChainLink transformationChainLink : transformations) {
            Optional<Transformation> transformation
                = transformationChainLink.tryToGetTransformation(request);

            if (transformation.isPresent()) {
                return transformation.get();
            }
        }

        throw new IllegalArgumentException(ERROR_MESSAGE + request.transformation().name());
    }
}
