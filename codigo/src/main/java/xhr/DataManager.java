package xhr;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import xhr.modules.Client;
import xhr.modules.Rent;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class DataManager {

    public static Path DATA_PATH = Path.of("data");

    public static Path CLIENT_DATA_PATH = DATA_PATH.resolve("clients.csv");

    public static Path EQUIPMENT_DATA_PATH = DATA_PATH.resolve("equipments.csv");

    public static Path RENT_DATA_PATH = DATA_PATH.resolve("rents.csv");

    public static void setup() throws IOException {

        if (Files.notExists(DATA_PATH)) {

            Files.createDirectory(DATA_PATH);

        }

    }

    public static void append(Path path, String[] header, String[] data) throws IOException {

        if(Files.notExists(path) && header != null) {
            Files.createFile(path);
            append(path, null, header);
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString(), true))) {

            List<String[]> dataToWrite = new ArrayList<>();
            dataToWrite.add(data);
            writer.writeAll(dataToWrite);

        }

    }

    public static <T> T read(Path path, int searchKeyIndex, String searchValue, Function<String[], T> content) throws IOException, CsvException {

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
