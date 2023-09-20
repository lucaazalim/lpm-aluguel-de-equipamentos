package xhr.modules;

import xhr.data.RentDataManager;

import java.time.LocalDate;
import java.time.Period;

public class Rent implements Identifiable {

    public static final RentDataManager DATA = new RentDataManager();

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

    private void init(int id, LocalDate startDate, LocalDate endDate, Client client, Equipment equipment) {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid period.");
        }

        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
        this.equipment = equipment;
        this.price = this.equipment.getTotalPrice(Period.between(this.startDate, this.endDate).getDays());

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

    public Equipment getEquipment() {
        return equipment;
    }

    @Override
    public int getId() {
        return id;
    }

}