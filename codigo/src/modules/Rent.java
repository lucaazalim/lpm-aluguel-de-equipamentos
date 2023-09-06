package modules;
import java.time.LocalDate;
import java.time.Period;

public class Rent {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private Client client;
    private Equipment equipment;
    private static int counter = 0;

    public static int getCounter() {
        return counter;
    }

    Rent(LocalDate startDate, LocalDate endDate, Client client, Equipment equipment) {
        this.id = counter;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
        this.equipment = equipment;
        this.price = calculeteTotalPrice();
        counter++;
    }

    public int getId(){
        return id;
    }

    public LocalDate getStartDate(){
        return startDate;
    }

    public LocalDate getEndDate(){
        return endDate;
    }

    public double getPrice(){
        return price;
    }

    public Client getClient(){
        return client;
    }

    private double calculeteTotalPrice() {
        Period difference = Period.between(startDate, endDate);
        int totalDays = difference.getDays();
        return equipment.getDailyPrice() * totalDays;
    }
}
