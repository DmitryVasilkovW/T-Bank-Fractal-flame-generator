package backend.academy.fractal.flame.service.io.impl;

import backend.academy.fractal.flame.service.io.Printer;
import java.io.PrintStream;
import org.springframework.stereotype.Component;

@Component
public class PrintStreamPrinterImpl implements Printer {
    private final PrintStream printStream;

    public PrintStreamPrinterImpl(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void println(String message) {
        printStream.println(message);
    }
}
