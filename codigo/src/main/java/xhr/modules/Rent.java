package xhr.modules;

import com.opencsv.exceptions.CsvException;
import xhr.DataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Period;


public class Rent {

    public static final Path RENT_DATA_PATH = DataManager.DATA_PATH.resolve("rents.csv");

    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Client client;
    private Equipment equipment;
    private double price;

    public Rent(int id, LocalDate startDate, LocalDate endDate, Client client, Equipment equipment) {
        init(id, startDate, endDate, client, equipment);
    }

    public Rent(int id, LocalDate endDate, Client client, Equipment equipment) {
        init(id, LocalDate.now(), endDate, client, equipment);
    }

    public Rent(String[] fields) {
        init(Integer.parseInt(fields[0]), LocalDate.parse(fields[1]),LocalDate.parse(fields[2]), client, equipment);
    }

    private void init(int id, LocalDate startDate, LocalDate endDate, Client client, Equipment equipment) {

        if (!isPeriodValid(startDate, endDate)) {
            return;
        }

        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
        this.equipment = equipment;
        this.price = calculateTotalPrice();

    }

    public int getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getPrice() {
        return price;
    }

    public Client getClient() {
        return client;
    }

    private boolean isPeriodValid(LocalDate startDate, LocalDate endDate) {
        return startDate.isBefore(endDate);
    }

    private double calculateTotalPrice() {
        Period difference = Period.between(startDate, endDate);
        int totalDays = difference.getDays();
        return equipment.getDailyPrice() * totalDays;
    }

    public void save() throws IOException, CsvException{
        DataManager.appendOrUpdateObject(RENT_DATA_PATH, new String[]{"id", "startDate", "endDate", "client", "equipment", "price"}, new String[]{String.valueOf(id)},0);
    }

    public static Rent searchById(int rentId) throws IOException, CsvException{
        return DataManager.readObject(RENT_DATA_PATH, "id", value -> value.equals(String.valueOf(rentId)), Rent::new);
    }
}
