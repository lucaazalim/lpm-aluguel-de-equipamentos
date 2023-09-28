package xhr.modules;

import xhr.Utils;
import xhr.data.RentDataManager;

import java.time.LocalDate;
import java.time.Period;

public class Rent implements Identifiable {

    public static final RentDataManager DATA = new RentDataManager();

    private int id;
    private LocalDate startDate, endDate;
    private Client client;
    private Equipment equipment;
    private double price;

    /**
     * Creates a new Rent.
     *
     * @param id rent ID
     * @param startDate rent start date
     * @param endDate rent end date
     * @param client rent client
     * @param equipment rent equipment
     * @param price rent price
     */
    public Rent(int id, LocalDate startDate, LocalDate endDate, Client client, Equipment equipment, double price) {
        init(id, startDate, endDate, client, equipment, price);
    }

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
        init(id, startDate, endDate, client, equipment, null);
    }

    public void init(int id, LocalDate startDate, LocalDate endDate, Client client, Equipment equipment, Double price) {

        if (startDate.isAfter(endDate) || startDate.isEqual(endDate)) {
            throw new IllegalArgumentException("Invalid period.");
        }

        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
        this.equipment = equipment;
        this.price = price == null ? this.equipment.getTotalPrice(this.getPeriod()) : price;

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
     * Returns the rent period in days.
     *
     * @return rent period
     */
    public int getPeriod() {
        return Period.between(this.startDate, this.endDate).getDays();
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
        return "Aluguel: " + this.equipment.getName()
                + " - ID: " + this.id
                + " - Cliente: " + this.client.getName()
                + " - Equipamento: " + this.equipment.getName()
                + " - Preço: " + Utils.formatCurrency(this.price)
                + " - Data: " + this.startDate + " até " + this.endDate;
    }
}