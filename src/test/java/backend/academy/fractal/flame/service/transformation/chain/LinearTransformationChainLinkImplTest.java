package backend.academy.fractal.flame.service.transformation.chain;

import backend.academy.fractal.flame.model.enums.Transformations;
import backend.academy.fractal.flame.model.records.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.impl.LinearTransformationChainLinkImpl;
import backend.academy.fractal.flame.service.transformation.impl.LinearTransformationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static backend.academy.fractal.flame.model.enums.Transformations.COMPLEX_WAVE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinearTransformationChainLinkImplTest {

    @Mock
    private TransformationRequest mockRequest;

    @InjectMocks
    private LinearTransformationChainLinkImpl chainLink;

    @Test
    void testTryToGetTransformation_WithLinear() {
        when(mockRequest.transformation()).thenReturn(Transformations.LINEAR);

        Optional<Transformation> result = chainLink.tryToGetTransformation(mockRequest);

        assertTrue(result.isPresent(), "Expected transformation to be present");
        assertInstanceOf(LinearTransformationImpl.class, result.get(),
            "Expected transformation to be of type LinearTransformationImpl");
    }

    @Test
    void testTryToGetTransformation_WithOtherTransformation() {
        when(mockRequest.transformation()).thenReturn(COMPLEX_WAVE);

        Optional<Transformation> result = chainLink.tryToGetTransformation(mockRequest);

        assertFalse(result.isPresent(), "Expected no transformation to be present");
    }
}
