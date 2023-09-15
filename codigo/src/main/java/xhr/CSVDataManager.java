package xhr;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CSVDataManager {

    public static Path DATA_PATH = Path.of("data");

    /**
     * Creates the data directory if it doesn't exist.
     *
     * @throws IOException if an I/O error occurs
     */
    public static void setup() throws IOException {

        if (Files.notExists(DATA_PATH)) {
            Files.createDirectory(DATA_PATH);
        }

    }

    /**
     * Appends or updates data in a CSV file specified by the given path.
     *
     * @param path        The path to the CSV file to be modified.
     * @param header      An array of strings representing the CSV file's header. Can be null.
     * @param data        An array of strings representing the data to be appended or updated in the CSV file.
     * @param searchIndex An integer indicating the index used for searching existing data in the CSV file.
     * @throws IOException  If an I/O error occurs while reading or writing the CSV file.
     * @throws CsvException If there is an issue with CSV file parsing.
     */
    public static void appendOrUpdateObject(Path path, String[] header, String[] data, int searchIndex) throws IOException, CsvException {

        if (Files.notExists(path) && header != null) {
            Files.createFile(path);
            appendOrUpdateObject(path, null, header, searchIndex);
        }

        List<String[]> dataToWrite = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(path.toString()))) {

            List<String[]> lines = reader.readAll();

            for (int i = 1; i < lines.size(); i++) {

                String[] row = lines.get(i);
                String identifier = row[searchIndex];

                if (identifier.equals(data[searchIndex])) {

                    dataToWrite.addAll(lines);
                    dataToWrite.set(i, data);
                    break;

                }

            }

        }

        boolean noDataToUpdate = dataToWrite.isEmpty();

        if (noDataToUpdate) {
            dataToWrite.add(data);
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString(), noDataToUpdate))) {
            writer.writeAll(dataToWrite);
        }

    }

    /**
     * Reads an object from a CSV file specified by the given path based on a search key-value pair.
     *
     * @param <T>            The type of object to be read and returned.
     * @param path           The path to the CSV file to be read.
     * @param searchKeyIndex The index of the column used as the search key.
     * @param searchValue    The value to search for in the specified column.
     * @param content        A function that converts a row of CSV data (as an array of strings) into an object of type T.
     * @return The object of type T found in the CSV file, or null if not found.
     * @throws IOException  If an I/O error occurs while reading the CSV file.
     * @throws CsvException If there is an issue with CSV file parsing.
     */
    public static <T> T readObject(Path path, int searchKeyIndex, String searchValue, Function<String[], T> content) throws IOException, CsvException {

        try (CSVReader reader = new CSVReader(new FileReader(path.toString()))) {

            List<String[]> lines = reader.readAll();

            for (int i = 1; i < lines.size(); i++) {

                String[] row = lines.get(i);
                String identifier = row[searchKeyIndex];

                if (identifier.equals(searchValue)) {
                    return content.apply(row);
                }

            }

        }

        return null;

    }

}
