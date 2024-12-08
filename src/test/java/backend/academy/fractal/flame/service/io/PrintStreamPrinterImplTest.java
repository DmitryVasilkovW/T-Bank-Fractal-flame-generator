package backend.academy.fractal.flame.service.io;

import backend.academy.fractal.flame.service.io.impl.PrintStreamPrinterImpl;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PrintStreamPrinterImplTest {

    @Mock
    private PrintStream mockPrintStream;

    @InjectMocks
    private PrintStreamPrinterImpl printer;

    @Test
    void testPrintln() {
        String message = "Hello, world!";

        printer.println(message);

        verify(mockPrintStream, times(1)).println(message);
    }
}

