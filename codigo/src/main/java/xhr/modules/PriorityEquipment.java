package xhr.modules;

import xhr.exceptions.PriorityEquipmentRentPeriodExceededException;

public class PriorityEquipment extends Equipment {

    public PriorityEquipment(int id, String name, double dailyPrice) {
        super(id, name, dailyPrice);
    }

    @Override
    public double getTotalPrice(int totalDays) {
        
        double totalPrice = 0;
        double dailyPrice = this.getDailyPrice();

        for (int i = 1; i <= totalDays; i++) {

            totalPrice += dailyPrice;

            if (i % 3 == 0) {
                dailyPrice += this.getDailyPrice() * 0.15;
            }

        }
        return totalPrice;
    }

    @Override
    public void addRent(Rent rent) {
        
        if(rent.getPeriod() > 10) {
            throw new PriorityEquipmentRentPeriodExceededException();
        }

        super.addRent(rent);

    }

}
