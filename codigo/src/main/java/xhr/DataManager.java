package xhr;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class DataManager {

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
     * Appends or updates a specific row in a CSV file at the given path. If the file
     * does not exist, it creates a new CSV file with the provided header. If a row
     * with the same identifier as the data to be updated is found, the existing row
     * is replaced with the new data. If no matching identifier is found, the data is
     * appended as a new row at the end of the file.
     *
     * @param path The path to the CSV file.
     * @param header An array representing the header of the CSV file.
     * @param data An array representing the data to append or update.
     * @param searchIndex The index of the column used as the identifier for searching.
     * @throws IOException If an I/O error occurs while reading or writing the file.
     * @throws CsvException If there is an issue with the CSV format.
     */
    public static void appendOrUpdateObject(Path path, String[] header, String[] data, int searchIndex) throws IOException, CsvException {

        if (Files.notExists(path)) {
            writeToCSVFile(path, Collections.singleton(header), StandardOpenOption.CREATE);
        }

        List<String[]> lines = readCSVFile(path), dataToWrite = null;

        for (int i = 1; i < lines.size(); i++) {

            String identifier = lines.get(i)[searchIndex];

            if (identifier.equals(data[searchIndex])) {

                dataToWrite = lines;
                dataToWrite.set(i, data);
                break;

            }

        }

        if (dataToWrite == null) {
            writeToCSVFile(path, Collections.singleton(data), StandardOpenOption.APPEND);
        } else {
            writeToCSVFile(path, dataToWrite);
        }

    }

    /**
     * Reads a specific entry from a CSV file at the given path based on a specified
     * column name, performs a comparison using the provided comparison function, and
     * transforms the matching CSV entry using the provided transformation function.
     *
     * @param <T> The type of result to return.
     * @param path The path to the CSV file.
     * @param columnNameToSearch The name of the column to search for a match.
     * @param comparation A function that takes a string and returns a boolean, used to compare the identifier from the CSV file.
     * @param transformation A function that takes an array of strings (representing CSV row data) and returns a result of type T.
     * @return The result of applying the transformation function to the matching CSV entry, or null if no matching entry is found.
     * @throws IOException If an I/O error occurs while reading the file.
     * @throws CsvException If there is an issue with the CSV format.
     * @throws IllegalArgumentException If the specified column name is not found inthe CSV header.
     */
    public static <T> T readObject(Path path, String columnNameToSearch, Function<String, Boolean> comparation, Function<String[], T> transformation) throws IOException, CsvException {

        List<String[]> lines = readCSVFile(path);
        Integer columnIndexToSearch = findColumnByName(lines.get(0), columnNameToSearch);

        if(columnIndexToSearch == null) {
            throw new IllegalArgumentException("Column name not found");
        }

        for (int index = 1; index < lines.size(); index++) {

            String[] row = lines.get(index);
            String identifier = row[columnIndexToSearch];

            if (comparation.apply(identifier)) {
                return transformation.apply(row);
            }

        }

        return null;

    }

    /**
     * Reads all objects from a CSV file specified by the given path.
     *
     * @param path The path to the CSV file to be read.
     * @param content A function that converts a row of CSV data (as an array of strings) into an object of type T.
     * @return A list of objects of type T found in the CSV file.
     * @param <T> The type of object to be read and returned.
     * @throws IOException If an I/O error occurs while reading the CSV file.
     * @throws CsvException If there is an issue with CSV file parsing.
     */
    public static <T> T readLatestObject(Path path, Function<String[], T> content) throws IOException, CsvException {
        List<String[]> lines = readCSVFile(path);
        return content.apply(lines.get(lines.size() - 1));
    }

    private static Integer findColumnByName(String[] header, String columnName) {
        for(int index = 0; index < header.length; index++) {
            if(header[index].equalsIgnoreCase(columnName)) {
                return index;
            }
        }
        return null;
    }

    /**
     * Reads all objects from a CSV file specified by the given path.
     *
     * @param path The path to the CSV file to be read.
     * @return A list of objects of type T found in the CSV file.
     * @throws IOException If an I/O error occurs while reading the CSV file.
     * @throws CsvException If there is an issue with CSV file parsing.
     */
    private static List<String[]> readCSVFile(Path path) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(Files.newBufferedReader(path))) {
            return reader.readAll();
        }
    }

    /**
     * Writes data to a CSV file specified by the given path.
     *
     * @param path The path to the CSV file to be written.
     * @param data The data to be written to the CSV file.
     * @param openOptions The options to be used when opening the file.
     * @throws IOException If an I/O error occurs while writing the CSV file.
     */
    private static void writeToCSVFile(Path path, Collection<String[]> data, OpenOption... openOptions) throws IOException {
        try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(path, openOptions))) {
            writer.writeAll(data);
        }
    }

}
