package backend.academy.fractal.flame.service.transformation.chain;

import backend.academy.fractal.flame.model.enums.Transformations;
import backend.academy.fractal.flame.model.records.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.impl.SinusoidalTransformationChainLinkImpl;
import backend.academy.fractal.flame.service.transformation.impl.SinusoidalTransformationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static backend.academy.fractal.flame.model.enums.Transformations.SINUSOIDAL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SinusoidalTransformationChainLinkImplTest {

    @Mock
    private TransformationRequest mockRequest;

    @InjectMocks
    private SinusoidalTransformationChainLinkImpl chainLink;

    @Test
    void testTryToGetTransformationWithSinusoidal() {
        when(mockRequest.transformation()).thenReturn(SINUSOIDAL);

        Optional<Transformation> result = chainLink.tryToGetTransformation(mockRequest);

        assertTrue(result.isPresent(), "Expected transformation to be present");
        assertInstanceOf(SinusoidalTransformationImpl.class, result.get(),
            "Expected transformation to be of type SinusoidalTransformationImpl");
    }

    @Test
    void testTryToGetTransformationWithOtherTransformation() {
        when(mockRequest.transformation()).thenReturn(Transformations.COMPLEX_WAVE);

        Optional<Transformation> result = chainLink.tryToGetTransformation(mockRequest);

        assertFalse(result.isPresent(), "Expected no transformation to be present");
    }
}
