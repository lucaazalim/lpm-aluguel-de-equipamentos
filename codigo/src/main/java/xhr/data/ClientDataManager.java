package xhr.data;

import xhr.App;
import xhr.modules.Client;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDataManager extends DataManager<Client> {

    public ClientDataManager() {
        super(App.DATA_PATH.resolve("clients.csv"), new String[]{"id", "name"});
    }

    @Override
    public Client fromCSV(String[] row) {
        return new Client(Integer.parseInt(row[0]), row[1]);
    }

    @Override
    public String[] toCSV(Client client) {
        return new String[]{String.valueOf(client.getId()), client.getName()};
    }

    public Set<Client> getByNameFragment(String name) {
        return this.data.stream().filter(client -> client.getName().contains(name)).collect(Collectors.toSet());
    }

}
