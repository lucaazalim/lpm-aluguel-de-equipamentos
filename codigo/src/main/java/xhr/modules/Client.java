package xhr.modules;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import xhr.DataManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {

    private static int counter = 0;

    private final int id;

    private final String name;

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

    public List<Rent> getRents() {
        return rents;
    }

    public void rentEquipment(Rent rent) {
        this.rents.add(rent);
    }

    public void register() throws IOException {
        DataManager.append(DataManager.CLIENT_DATA_PATH, new String[]{"ID", "Name"}, new String[]{String.valueOf(id), name});
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", rents=" + this.rents +
                '}';
    }

    public static int getCounter() {
        return counter;
    }

    public static Client retrieveClient(int clientId) throws IOException, CsvException {
        return DataManager.read(DataManager.CLIENT_DATA_PATH, 0, String.valueOf(clientId), fields -> new Client(fields[1]));
    }

}
