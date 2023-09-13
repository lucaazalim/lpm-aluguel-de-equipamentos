package modules;

import java.util.List;
import java.util.Objects;

public class Client {

    private static int counter = 0;

    private final int id;

    private final String name;

    private List<Rent> rents;

    public Client(String name) {

        Objects.requireNonNull(name);

        this.id = counter++;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Rent> getRents() {
        return rents;
    }

    public void rentEquipment(Rent rent) {
        this.rents.add(rent);
    }

    public static int getCounter() {
        return counter;
    }
    
}
