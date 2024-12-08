package backend.academy.fractal.flame.service.transformation.chain;

import backend.academy.fractal.flame.model.enums.Transformations;
import backend.academy.fractal.flame.model.records.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.impl.OscillatingStretchTransformationChainLinkImpl;
import backend.academy.fractal.flame.service.transformation.impl.OscillatingStretchTransformationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static backend.academy.fractal.flame.model.enums.Transformations.OSCILLATING;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OscillatingStretchTransformationChainLinkImplTest {

    @Mock
    private TransformationRequest mockRequest;

    @InjectMocks
    private OscillatingStretchTransformationChainLinkImpl chainLink;

    @Test
    void testTryToGetTransformationWithOscillating() {
        when(mockRequest.transformation()).thenReturn(OSCILLATING);

        Optional<Transformation> result = chainLink.tryToGetTransformation(mockRequest);

        assertTrue(result.isPresent(), "Expected transformation to be present");
        assertInstanceOf(OscillatingStretchTransformationImpl.class, result.get(),
            "Expected transformation to be of type OscillatingStretchTransformationImpl");
    }

    @Test
    void testTryToGetTransformationWithOtherTransformation() {
        when(mockRequest.transformation()).thenReturn(Transformations.COMPLEX_WAVE);

        Optional<Transformation> result = chainLink.tryToGetTransformation(mockRequest);

        assertFalse(result.isPresent(), "Expected no transformation to be present");
    }
}
