package xhr.modules;

import xhr.data.ClientDataManager;

import java.util.*;

public class Client implements Identifiable {

    public static final ClientDataManager DATA = new ClientDataManager();

    private final int id;

    private final String name;

    private final List<Rent> rents = new ArrayList<>();

    /**
     * Creates a new Client.
     *
     * @param id client ID
     * @param name client name
     */
    public Client(int id, String name) {

        Objects.requireNonNull(name);

        this.id = id;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    /**
     * Returns a copy of the client's rent list.
     *
     * @return copy of the client's rent list
     */
    public List<Rent> getRents() {
        return new ArrayList<>(this.rents);
    }

    /**
     * Adds a rent to the client's rent list.
     *
     * @param rent rent to be added
     */
    public void addRent(Rent rent) {
        this.rents.add(rent);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Cliente: " + this.name + " - ID: " + this.id;
    }

}
