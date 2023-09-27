package xhr.modules;

import xhr.data.RentDataManager;

import java.time.LocalDate;
import java.time.Period;

public class Rent implements Identifiable {

    public static final RentDataManager DATA = new RentDataManager();

    private final int id, period;
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
        this.period = Period.between(this.startDate, this.endDate).getDays();
        this.price = this.equipment.getTotalPrice(this.period);

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
     * Returns the rent price.
     *
     * @return rent price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the rent period.
     *
     * @return rent period
     */
    public int getPeriod() {
        return period;
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

    /**
     * Verify if there isn't any rent in the passed dates
     * 
     * @param startDate first date of rent
     * @param endDate last day of rent
     * @return true | false
     */
    public boolean isNotValidPeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate.isEqual(this.endDate) || endDate.isEqual(this.startDate))
            return true;
        else if (startDate.isBefore(this.endDate) && endDate.isAfter(this.startDate))
            return true;
        return false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Aluguel: " + this.equipment.getName() + " (ID: " + this.id + ")";
    }
}