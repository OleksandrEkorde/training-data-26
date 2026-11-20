import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataFileHandler {
    public static Integer[] loadArrayFromFile(String filePath) {
        List<Integer> values = new ArrayList<>();
        try (BufferedReader fileReader = Files.newBufferedReader(Path.of(filePath), StandardCharsets.UTF_8)) {
            String currentLine;
            int lineNumber = 0;
            while ((currentLine = fileReader.readLine()) != null) {
                lineNumber++;

                currentLine = currentLine.replace("\uFEFF", "").trim();
                if (currentLine.isEmpty()) {
                    continue;
                }
                try {
                    values.add(Integer.valueOf(currentLine));
                } catch (NumberFormatException formatException) {
                    throw new IOException("Рядок " + lineNumber + " не є цілим числом: '" + currentLine + "'");
                }
            }
        } catch (IOException ioException) {
            throw new UncheckedIOException("Не вдалося прочитати " + filePath + ": " + ioException.getMessage(), ioException);
        }
        return values.toArray(new Integer[0]);
    }

    public static void writeArrayToFile(Integer[] valueArray, String filePath) {
        try (BufferedWriter fileWriter = Files.newBufferedWriter(Path.of(filePath), StandardCharsets.UTF_8)) {
            for (Integer valueElement : valueArray) {
                fileWriter.write(valueElement.toString());
                fileWriter.newLine();
            }
        } catch (IOException ioException) {
            throw new UncheckedIOException("Не вдалося записати " + filePath, ioException);
        }
    }
}
