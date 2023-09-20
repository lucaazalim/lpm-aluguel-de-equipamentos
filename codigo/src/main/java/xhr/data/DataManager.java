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

public abstract class DataManager<T extends Identifiable> {

    protected final List<T> data = new ArrayList<>();

    private final Path path;
    private final String[] header;

    public DataManager(Path path, String[] header) {
        this.path = path;
        this.header = header;
    }

    public Collection<T> getAll() {
        return new ArrayList<>(data);
    }

    public T getById(int id) {
        return this.data.stream().filter(object -> object.getId() == id).findFirst().orElse(null);
    }

    public int getNextId() {
        return this.data.stream().mapToInt(Identifiable::getId).max().orElse(0) + 1;
    }

    public void add(T object) {
        this.data.add(object);
    }

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

    public abstract T fromCSV(String[] row);

    public abstract String[] toCSV(T object);

}
