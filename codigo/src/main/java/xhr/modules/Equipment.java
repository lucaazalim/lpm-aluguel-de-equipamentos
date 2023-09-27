package xhr.modules;

import xhr.Utils;
import xhr.data.EquipmentDataManager;
import xhr.exceptions.EquipmentAlreadyRentedInPeriodException;
import xhr.exceptions.PriorityEquipmentRentPeriodExceededException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Equipment implements Identifiable {

    public static final EquipmentDataManager DATA = new EquipmentDataManager();

    private final int id;
    private final String name;
    private final double dailyPrice;
    private final List<Rent> rents = new ArrayList<>();

    /**
     * Creates a new Equipment.
     *
     * @param id equipment ID
     * @param name equipment name
     * @param dailyPrice equipment daily price
     */
    public Equipment(int id, String name, double dailyPrice) {

        Objects.requireNonNull(name);

        this.id = id;
        this.name = name;
        this.dailyPrice = dailyPrice;

    }

    /**
     * Returns the equipment name
     *
     * @return equipment name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the equipment daily price
     *
     * @return equipment daily price
     */
    public double getDailyPrice() {
        return this.dailyPrice;
    }

    /**
     * Returns the equipment total price based on the total days of rent
     *
     * @param totalDays total days of rent
     * @return equipment total price
     */
    public double getTotalPrice(int totalDays) {

        return this.getDailyPrice() * totalDays;

    }

    /**
     * Returns a copy of the equipment's rent list.
     *
     * @return copy of the equipment's rent list
     */
    public List<Rent> getRents() {
        return new ArrayList<>(this.rents);
    }

    /**
     * Add new rent if it's period is available
     *
     * @throws PriorityEquipmentRentPeriodExceededException if priority equipment is rented for more than the max days
     * @throws EquipmentAlreadyRentedInPeriodException if equipment is already rented in this period
     * @param rent rent to add
     */
    public void addRent(Rent rent) {

        if (this.isRented(rent.getStartDate(), rent.getEndDate())) {
            throw new EquipmentAlreadyRentedInPeriodException();
        }

        this.rents.add(rent);

    }

    /**
     * Delete rent if it's id is passed correctly.
     *
     * @param id rent id to delete
     */
    public void deleteRent(int id) {
        this.rents.removeIf(rent -> rent.getId() == id);
    }

    /**
     * Checks if this period is available to rent.
     *
     * @param startDate first date of rent
     * @param endDate last day of rent
     * @return true | false
     */
    public boolean isRented(LocalDate startDate, LocalDate endDate) {
        for(Rent rent : rents){
            if (rent.isNotValidPeriod(startDate, endDate)) return true;
        }
        return false;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Equipamento: " + this.name
                + " - ID: " + this.id
                + " - Preço diário: " + Utils.formatCurrency(this.dailyPrice);
    }

}
