package backend.academy.fractal.flame.service.transformation.chain;

import backend.academy.fractal.flame.model.records.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import java.util.Optional;

public interface TransformationChainLink {
    Optional<Transformation> tryToGetTransformation(TransformationRequest request);
}
