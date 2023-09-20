package xhr.modules;

import com.opencsv.exceptions.CsvException;
import xhr.DataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {

    public static final Path CLIENT_DATA_PATH = DataManager.DATA_PATH.resolve("clients.csv");

    private int id;

    private String name;

    private final List<Rent> rents = new ArrayList<>();

    /**
     * Creates a new Client.
     *
     * @param id client ID
     * @param name client name
     */
    public Client(int id, String name) {
        init(id, name);
    }

    /**
     * Creates a new Client from CSV fields.
     *
     * @param fields fields from a 
     */
    public Client(String[] fields) {
        init(Integer.parseInt(fields[0]), fields[1]);
    }

    private void init(int id, String name) {

        Objects.requireNonNull(name);

        this.id = id;
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRentsAmount() {
        return this.rents.size();
    }

    public void addRent(Rent rent) {
        this.rents.add(rent);
    }

    public void save() throws IOException, CsvException {
        DataManager.appendOrUpdateObject(CLIENT_DATA_PATH, new String[]{"id", "name"}, new String[]{String.valueOf(id), name}, 0);
    }

    @Override
    public String toString() {
        return "Cliente: " + this.name + " (ID: " + this.id + ")";
    }

    public static Client searchById(int clientId) throws IOException, CsvException {
        return DataManager.readObject(CLIENT_DATA_PATH, "id", value -> value.equals(String.valueOf(clientId)), Client::new);
    }

    public static List<Client> searchByName(String name) throws IOException, CsvException {
        return DataManager.readObjects(CLIENT_DATA_PATH, "name", value -> value.toLowerCase().contains(name.toLowerCase()), Client::new);
    }

}
