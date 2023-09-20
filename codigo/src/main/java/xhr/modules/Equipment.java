package xhr.modules;
import xhr.DataManager;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.exceptions.CsvException;

public class Equipment {

    public static final Path EQUIPMENT_DATA_PATH = DataManager.DATA_PATH.resolve("equipments.csv");

    private int id;
    private String name;
    private double dailyPrice;
    private List<Rent> rents;

    public Equipment(int id, String name, double dailyPrice) {
        init(id, name, dailyPrice);
    }

    public Equipment(String[] fields) {
        init(Integer.parseInt(fields[0]), fields[1], Double.parseDouble(fields[2]));
    }

    private void init(int id, String name, double dailyPrice) {
        this.id = id;
        this.name = name;
        this.dailyPrice = dailyPrice;
        this.rents = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public double getDailyPrice() {
        return this.dailyPrice;
    }

    public List<Rent> getRents() {
        return this.rents;
    }

    /**
     * Add new rent if it's period is available
     * @param rent rent to add
     */
    public void addRent(Rent rent) {
        if (isRented(rent.getStartDate(), rent.getEndDate())) return;
        this.rents.add(rent);
    }

    /**
     * Delete rent if it's id is passed correctly
     * @param id rent id to delete
     */
    public void deleteRent(int id) {
        for(int i = 0; i < this.rents.size(); i++) {
            if (this.rents.get(i).getId() == id) {
                this.rents.remove(i);
            }
        }
    }

    /**
     * Checks if this period is available to rent
     * @param startDate first date of rent
     * @param endDate last day of rent
     * @return true | false
     */
    public boolean isRented(LocalDate startDate, LocalDate endDate) {
        boolean isRetened = false;
        for(Rent rent : rents){
            if (startDate.isEqual(rent.getEndDate()) || endDate.isEqual(rent.getStartDate()))
                isRetened = true;
            else if (startDate.isBefore(rent.getEndDate()) && endDate.isAfter(rent.getStartDate()))
                isRetened = true;
        }
        return isRetened;
    }

    /**
     * Searches for a equipment by id.
     *
     * @param equipmentId equipment id
     * @return the equipment with the given id
     * @throws IOException  If an I/O error occurs while reading the CSV file.
     * @throws CsvException If there is an issue with CSV file parsing.
     */
    public static Equipment searchById(int equipmentId) throws IOException, CsvException {
        return DataManager.readObject(EQUIPMENT_DATA_PATH, "id", value -> value.equals(String.valueOf(equipmentId)), Equipment::new);
    }

    /**
     * Save equipment in cvs file
     *
     * @throws IOException if an I/O error occurs
     * @throws CsvException if an error occurs while reading or writing a CSV file
     */
    public void save() throws IOException, CsvException {
        DataManager.appendOrUpdateObject(EQUIPMENT_DATA_PATH, new String[]{"id", "name", "dailyPrice", "rents"}, new String[]{String.valueOf(id), name, String.valueOf(dailyPrice), String.valueOf(rents)}, 0);
    }
}
