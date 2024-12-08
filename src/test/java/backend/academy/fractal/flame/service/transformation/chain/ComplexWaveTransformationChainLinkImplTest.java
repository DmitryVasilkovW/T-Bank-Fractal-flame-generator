package backend.academy.fractal.flame.service.transformation.chain;

import backend.academy.fractal.flame.model.enums.Transformations;
import backend.academy.fractal.flame.model.records.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.impl.ComplexWaveTransformationChainLinkImpl;
import backend.academy.fractal.flame.service.transformation.impl.ComplexWaveTransformationImpl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static backend.academy.fractal.flame.model.enums.Transformations.SINUSOIDAL_WARP;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComplexWaveTransformationChainLinkImplTest {

    @Mock
    private TransformationRequest mockRequest;

    @InjectMocks
    private ComplexWaveTransformationChainLinkImpl chainLink;

    @Test
    void testTryToGetTransformationWithComplexWave() {
        when(mockRequest.transformation()).thenReturn(Transformations.COMPLEX_WAVE);

        Optional<Transformation> result = chainLink.tryToGetTransformation(mockRequest);

        assertTrue(result.isPresent(), "Expected transformation to be present");
        assertInstanceOf(ComplexWaveTransformationImpl.class, result.get(),
            "Expected transformation to be of type ComplexWaveTransformationImpl");
    }

    @Test
    void testTryToGetTransformationWithOtherTransformation() {
        when(mockRequest.transformation()).thenReturn(SINUSOIDAL_WARP);

        Optional<Transformation> result = chainLink.tryToGetTransformation(mockRequest);

        assertFalse(result.isPresent(), "Expected no transformation to be present");
    }
}
