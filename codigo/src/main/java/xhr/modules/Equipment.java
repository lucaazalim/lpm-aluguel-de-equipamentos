package xhr.modules;
import xhr.data.EquipmentDataManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Equipment implements Identifiable {

    public static final EquipmentDataManager DATA = new EquipmentDataManager();

    private final int id;
    private final String name;
    private final double dailyPrice;
    private final List<Rent> rents = new ArrayList<>();
    boolean priority;

    public Equipment(int id, String name, double dailyPrice, boolean priority) {

        this.id = id;
        this.name = name;
        this.dailyPrice = dailyPrice;
        this.priority = priority;

    }

    public String getName() {
        return this.name;
    }

    public double getDailyPrice() {
        return this.dailyPrice;
    }

    public double getTotalPrice(int totalDays) {

        double totalPrice;

        if(this.isPriority()) {

            totalPrice = 0.0;
            double dailyPrice = this.getDailyPrice();

            for (int i = 1; i <= totalDays; i++) {

                totalPrice += dailyPrice;

                if (i % 3 == 0) {
                    dailyPrice += totalDays * 0.15;
                }

            }

        } else {

            totalPrice = this.getDailyPrice() * totalDays;

        }

        return totalPrice;

    }

    public List<Rent> getRents() {
        return this.rents;
    }

    public boolean isPriority() {
        return this.priority;
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

    @Override
    public int getId() {
        return this.id;
    }


}
