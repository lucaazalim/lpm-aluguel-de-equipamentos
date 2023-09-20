package xhr.modules;

import xhr.data.RentDataManager;

import java.time.LocalDate;
import java.time.Period;

public class Rent implements Identifiable {

    public static final RentDataManager DATA = new RentDataManager();

    private final int id;
    private final LocalDate startDate, endDate;
    private final Client client;
    private final Equipment equipment;
    private final double price;

    /**
     * Creates a new Rent.
     *
     * @param id rent ID
     * @param startDate rent start date
     * @param endDate rent end date
     * @param client rent client
     * @param equipment rent equipment
     */
    public Rent(int id, LocalDate startDate, LocalDate endDate, Client client, Equipment equipment) {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid period.");
        }

        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
        this.equipment = equipment;
        this.price = this.equipment.getTotalPrice(this.getPeriod().getDays());

    }

    /**
     * Returns the rent start date.
     *
     * @return rent start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Returns the rent end date.
     *
     * @return rent end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Returns the rent period.
     *
     * @return rent period
     */
    public Period getPeriod() {
        return Period.between(this.startDate, this.endDate);
    }

    /**
     * Returns the rent price.
     *
     * @return rent price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the rent client.
     *
     * @return rent client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Returns the rent equipment.
     *
     * @return rent equipment
     */
    public Equipment getEquipment() {
        return equipment;
    }

    @Override
    public int getId() {
        return id;
    }

}