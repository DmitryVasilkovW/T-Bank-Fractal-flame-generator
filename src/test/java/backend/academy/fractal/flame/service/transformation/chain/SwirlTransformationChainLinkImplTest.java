package backend.academy.fractal.flame.service.transformation.chain;

import backend.academy.fractal.flame.model.enums.Transformations;
import backend.academy.fractal.flame.model.records.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.Transformation;
import backend.academy.fractal.flame.service.transformation.chain.impl.SwirlTransformationChainLinkImpl;
import backend.academy.fractal.flame.service.transformation.impl.SwirlTransformationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static backend.academy.fractal.flame.model.enums.Transformations.SWIRL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SwirlTransformationChainLinkImplTest {

    @Mock
    private TransformationRequest mockRequest;

    @InjectMocks
    private SwirlTransformationChainLinkImpl chainLink;

    @Test
    void testTryToGetTransformationWithSwirl() {
        when(mockRequest.transformation()).thenReturn(SWIRL);

        Optional<Transformation> result = chainLink.tryToGetTransformation(mockRequest);

        assertTrue(result.isPresent(), "Expected transformation to be present");
        assertInstanceOf(SwirlTransformationImpl.class, result.get(),
            "Expected transformation to be of type SwirlTransformationImpl");
    }

    @Test
    void testTryToGetTransformationWithOtherTransformation() {
        when(mockRequest.transformation()).thenReturn(Transformations.COMPLEX_WAVE);

        Optional<Transformation> result = chainLink.tryToGetTransformation(mockRequest);

        assertFalse(result.isPresent(), "Expected no transformation to be present");
    }
}
