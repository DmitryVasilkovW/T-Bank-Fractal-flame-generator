package backend.academy.fractal.flame.service.transformation.chain;

import backend.academy.fractal.flame.model.records.TransformationRequest;
import backend.academy.fractal.flame.service.transformation.chain.impl.TangentWarpTransformationChainLinkImpl;
import backend.academy.fractal.flame.service.transformation.chain.impl.TransformationChainImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static backend.academy.fractal.flame.model.enums.Transformations.COMPLEX_WAVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransformationChainImplTest {

    @Mock
    private TangentWarpTransformationChainLinkImpl tangentWarpChainLink;

    @Mock
    private TransformationRequest mockRequest;

    private TransformationChainImpl transformationChain;

    @BeforeEach
    void beforeEach() {
        List<TransformationChainLink> chainLinks = List.of(tangentWarpChainLink);

        transformationChain = new TransformationChainImpl(chainLinks);
    }

    @Test
    void testGetTransformationsWithNoMatchingTransformation() {
        when(mockRequest.transformation()).thenReturn(COMPLEX_WAVE);

        when(tangentWarpChainLink.tryToGetTransformation(mockRequest))
            .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transformationChain.getTransformations(mockRequest);
        });

        assertEquals("No enum constant with display name: COMPLEX_WAVE", exception.getMessage(),
            "Expected error message to match");
    }
}
