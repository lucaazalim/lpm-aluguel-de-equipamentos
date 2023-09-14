package xhr.modules;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Equipment {
    private int id;
    private String name;
    private double dailyPrice;
    private List<Rent> rents;
    private static int counter = 1;

    public Equipment(String name, double dailyPrice) {
        init(name, dailyPrice);
    }

    private void init(String name, double dailyPrice) {
        this.id = ++counter;
        this.name = name;
        this.dailyPrice = dailyPrice;
        this.rents = new ArrayList<>();
    }

    public static int getCounter() {
        return counter;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDailyPrice() {
        return this.dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public List<Rent> getRents() {
        return this.rents;
    }

    public void addRent(Rent rent) {
        if (isRetened(rent.getStartDate(), rent.getEndDate())) return;
        this.rents.add(rent);
    }

    public void deleteRent(int id) {
        for(int i = 0; i < this.rents.size(); i++) {
            if (this.rents.get(i).getId() == id) {
                this.rents.remove(i);
            }
        }
    }

    public boolean isRetened(LocalDate startDate, LocalDate endDate) {
        boolean isRetened = false;
        for(int i = 0; i < this.rents.size(); i++) {
            if (startDate.isEqual(this.rents.get(i).getEndDate()) || endDate.isEqual(this.rents.get(i).getStartDate()))
                isRetened = true;
            else if (startDate.isBefore(this.rents.get(i).getEndDate()) && endDate.isAfter(this.rents.get(i).getStartDate()))
                isRetened = true;
        }
        return isRetened;
    }
}
