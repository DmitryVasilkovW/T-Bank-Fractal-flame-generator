package backend.academy.fractal.flame.service.io;

import backend.academy.fractal.flame.service.io.impl.ScannerReaderImpl;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ScannerReaderImplTest {

    @Test
    void testReadLineAsString() {
        String input = "Hello, world!";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input.getBytes());

        Reader reader = new ScannerReaderImpl(byteArrayInputStream);

        String result = reader.readLineAsString();

        assertEquals(input, result);
    }
}
