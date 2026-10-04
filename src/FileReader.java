package F28PAAssignment2026.src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileReader {
    public static List<String> readLines(String fileName) throws IOException {
        return Files.readAllLines(Path.of(fileName));
    }
}

