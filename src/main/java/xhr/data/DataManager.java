package xhr.data;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import xhr.modules.Identifiable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Manages data from a CSV file.
 *
 * @param <T> type of data
 */
public abstract class DataManager<T extends Identifiable> {

    protected final List<T> data = new ArrayList<>();

    private final Path path;
    private final String[] header;

    /**
     * Creates a new DataManager.
     *
     * @param path path to the CSV file
     * @param header CSV file header
     */
    public DataManager(Path path, String[] header) {
        this.path = path;
        this.header = header;
    }

    /**
     * Returns all stored data.
     *
     * @return all data
     */
    public Collection<T> getAll() {
        return new ArrayList<>(data);
    }

    /**
     * Returns the data with the given ID.
     *
     * @param id data ID
     * @return data with the given ID
     */
    public T getById(int id) {
        return this.data.stream().filter(object -> object.getId() == id).findFirst().orElse(null);
    }

    /**
     * Returns the next available ID.
     *
     * @return next available ID
     */
    public int getNextId() {
        return this.data.stream().mapToInt(Identifiable::getId).max().orElse(0) + 1;
    }

    /**
     * Adds an object to the data list.
     *
     * @param object object to be added
     */
    public void add(T object) {
        this.data.add(object);
    }

    /**
     * Loads all data from the CSV file to the memory.
     *
     * @throws IOException if an I/O error occurs
     * @throws CsvException if an error occurs while parsing the CSV file
     */
    public void load() throws IOException, CsvException {

        if (Files.notExists(this.path)) {
            return;
        }

        List<String[]> lines;

        try (CSVReader reader = new CSVReader(Files.newBufferedReader(this.path))) {
            lines = reader.readAll();
        }

        for (int index = 1; index < lines.size(); index++) {

            String[] row = lines.get(index);
            this.add(this.fromCSV(row));

        }

    }

    /**
     * Saves all data from the memory to the CSV file.
     *
     * @throws IOException if an I/O error occurs
     */
    public void save() throws IOException {

        if(Files.notExists(this.path)) {
            Files.createFile(this.path);
        }

        List<String[]> lines = new ArrayList<>();

        lines.add(this.header);

        for (T object : this.data) {
            lines.add(this.toCSV(object));
        }

        try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(
                this.path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
        ))) {
            writer.writeAll(lines);
        }

    }

    /**
     * Converts a CSV row to an object.
     *
     * @param row CSV row content
     * @return object
     */
    public abstract T fromCSV(String[] row);

    /**
     * Converts an object to a CSV row.
     *
     * @param object object to be converted
     * @return CSV row
     */
    public abstract String[] toCSV(T object);

}
