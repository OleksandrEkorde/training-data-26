import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Клас DataFileHandler управляє роботою з файлами цілих чисел Integer.
 */
public class DataFileHandler {
    /**
     * Завантажує масив Integer з файлу в кодуванні UTF-8.
     * Порожні рядки пропускаються, BOM на початку прибирається.
     *
     * @param filePath Шлях до файлу з даними.
     * @return Масив Integer.
     */
    public static Integer[] loadArrayFromFile(String filePath) {
        List<Integer> values = new ArrayList<>();
        try (BufferedReader fileReader = Files.newBufferedReader(Path.of(filePath), StandardCharsets.UTF_8)) {
            String currentLine;
            int lineNumber = 0;
            while ((currentLine = fileReader.readLine()) != null) {
                lineNumber++;
                // Видаляємо пробіли та можливий BOM на початку файлу
                currentLine = currentLine.replace("﻿", "").trim();
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

    /**
     * Зберігає масив Integer у файл у кодуванні UTF-8, по одному значенню в рядку.
     *
     * @param valueArray Масив Integer.
     * @param filePath Шлях до файлу для збереження.
     */
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
