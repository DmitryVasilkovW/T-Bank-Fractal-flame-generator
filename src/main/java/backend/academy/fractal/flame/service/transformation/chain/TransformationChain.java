package backend.academy.fractal.flame.service.transformation.chain;

import backend.academy.fractal.flame.model.records.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;

public interface TransformationChain {
    Transformation getTransformations(TransformationRequest request);
}
