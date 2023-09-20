package xhr.data;

import xhr.App;
import xhr.modules.Client;
import xhr.modules.Equipment;

import java.util.Set;
import java.util.stream.Collectors;

public class EquipmentDataManager extends DataManager<Equipment> {

    public EquipmentDataManager() {
        super(App.DATA_PATH.resolve("equipments.csv"), new String[]{"id", "name", "dailyPrice"});
    }

    @Override
    public Equipment fromCSV(String[] row) {
        return new Equipment(Integer.parseInt(row[0]), row[1], Double.parseDouble(row[2]), Boolean.parseBoolean(row[3]));
    }

    @Override
    public String[] toCSV(Equipment equipment) {
        return new String[]{String.valueOf(equipment.getId()), equipment.getName(), String.valueOf(equipment.getDailyPrice()), String.valueOf(equipment.isPriority())};
    }

    /**
     * Returns all equipments with the given name fragment.
     *
     * @param name name fragment
     * @return all equipments with the given name fragment
     */
    public Set<Equipment> getByNameFragment(String name) {
        return this.data.stream().filter(equipment -> equipment.getName().contains(name)).collect(Collectors.toSet());
    }

}
