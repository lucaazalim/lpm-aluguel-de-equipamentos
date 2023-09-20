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

    public Equipment getEquipment() {
        return equipment;
    }

    private boolean isPeriodValid(LocalDate startDate, LocalDate endDate) {
        return startDate.isBefore(endDate);
    }

    private double calculateTotalPrice() {
        Period difference = Period.between(startDate, endDate);
        int totalDays = difference.getDays();

        if (!this.equipment.isPriority()) {
            return equipment.getDailyPrice() * totalDays;
        }
        double amountToPay = 0, pricePerDay, originalRate;
        pricePerDay = originalRate = equipment.getDailyPrice();
        for (int i = 1; i <= totalDays; i++) {
            amountToPay += pricePerDay;
            if (i % 3 == 0) {
                pricePerDay += originalRate * 0.15;
            }
        }
        return amountToPay;
    }
}