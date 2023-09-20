package xhr.modules;
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
    boolean priority;

    /**
     * Creates a new Equipment.
     *
     * @param id equipment ID
     * @param name equipment name
     * @param dailyPrice equipment daily price
     * @param priority whether the equipment is priority or not
     */
    public Equipment(int id, String name, double dailyPrice, boolean priority) {

        Objects.requireNonNull(name);

        this.id = id;
        this.name = name;
        this.dailyPrice = dailyPrice;
        this.priority = priority;

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

        double totalPrice;

        if(this.isPriority()) {

            totalPrice = 0.0;
            double dailyPrice = this.getDailyPrice();

            for (int i = 1; i <= totalDays; i++) {

                totalPrice += dailyPrice;

                if (i % 3 == 0) {
                    dailyPrice += this.getDailyPrice() * 0.15;
                }

            }

        } else {

            totalPrice = this.getDailyPrice() * totalDays;

        }

        return totalPrice;

    }

    /**
     * Returns the equipment rents
     *
     * @return equipment rents
     */
    public List<Rent> getRents() {
        return this.rents;
    }

    /**
     * Returns if the equipment is priority
     *
     * @return true | false
     */
    public boolean isPriority() {
        return this.priority;
    }

    /**
     * Add new rent if it's period is available
     *
     * @throws PriorityEquipmentRentPeriodExceededException if priority equipment is rented for more than the max days
     * @throws EquipmentAlreadyRentedInPeriodException if equipment is already rented in this period
     * @param rent rent to add
     */
    public void addRent(Rent rent) {

        if(this.isPriority() && rent.getPeriod().getDays() > 10) {
            throw new PriorityEquipmentRentPeriodExceededException();
        }

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
        boolean isRetened = false;
        for(Rent rent : rents){
            if (startDate.isEqual(rent.getEndDate()) || endDate.isEqual(rent.getStartDate()))
                isRetened = true;
            else if (startDate.isBefore(rent.getEndDate()) && endDate.isAfter(rent.getStartDate()))
                isRetened = true;
        }
        return isRetened;
    }

    @Override
    public int getId() {
        return this.id;
    }


}
