package xhr.modules;

import com.opencsv.exceptions.CsvException;
import xhr.DataManager;

import java.io.IOException;
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

    public void writeToFile() throws IOException {
        DataManager.appendToFile(DataManager.CLIENT_DATA_PATH, new String[]{"ID", "Name"}, new String[]{String.valueOf(id), name});
    }

    @Override
    public String toString() {
        return "Cliente: " + name + " (ID: " + id + ")";
    }

    public static int getCounter() {
        return counter;
    }

    public static Client readFromFile(int clientId) throws IOException, CsvException {
        return DataManager.readObjectFromFile(DataManager.CLIENT_DATA_PATH, 0, String.valueOf(clientId), fields -> new Client(fields[1]));
    }

}
