package backend.academy.fractal.flame.service.io.impl;

import backend.academy.fractal.flame.service.io.Reader;
import java.io.InputStream;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ScannerReaderImpl implements Reader {
    private final Scanner scanner;

    public ScannerReaderImpl(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public String readLineAsString() {
        return scanner.nextLine();
    }
}
