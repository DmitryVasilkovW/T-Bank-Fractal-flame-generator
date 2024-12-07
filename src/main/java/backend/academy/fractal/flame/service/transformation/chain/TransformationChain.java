package backend.academy.fractal.flame.service.transformation.chain;

import backend.academy.fractal.flame.model.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;

public interface TransformationChain {
    Transformation getTransformations(TransformationRequest request);
}
