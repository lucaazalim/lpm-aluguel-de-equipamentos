package xhr.modules;

import com.opencsv.exceptions.CsvException;
import xhr.CSVDataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {

    private static int counter = 0;

    private static final Path clientDataPath = CSVDataManager.DATA_PATH.resolve("clients.csv");

    private final int id;

    private String name;

    private final List<Rent> rents = new ArrayList<>();

    public Client(String name) {

        Objects.requireNonNull(name);

        this.id = ++counter;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    public List<Rent> getRents() {
        return rents;
    }

    public void rentEquipment(Rent rent) {
        this.rents.add(rent);
    }

    public void save() throws IOException, CsvException {
        CSVDataManager.appendOrUpdateObject(clientDataPath, new String[]{"ID", "Name"}, new String[]{String.valueOf(id), name}, 0);
    }

    @Override
    public String toString() {
        return "Cliente: " + name + " (ID: " + id + ")";
    }

    public static int getCounter() {
        return counter;
    }

    public static Client searchById(int clientId) throws IOException, CsvException {
        return CSVDataManager.readObject(clientDataPath, 0, String.valueOf(clientId), fields -> new Client(fields[1]));
    }

}
