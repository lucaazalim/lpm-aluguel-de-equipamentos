package xhr.data;

import xhr.App;
import xhr.modules.Equipment;
import xhr.modules.PriorityEquipment;

import java.util.Set;
import java.util.stream.Collectors;

public class EquipmentDataManager extends DataManager<Equipment> {

    public EquipmentDataManager() {
        super(App.DATA_PATH.resolve("equipments.csv"), new String[]{"id", "name", "dailyPrice", "type"});
    }

    @Override
    public Equipment fromCSV(String[] row) {
        return EquipmentType.valueOf(row[3]).fromCSV(row);
    }

    @Override
    public String[] toCSV(Equipment equipment) {

        EquipmentType type = equipment instanceof PriorityEquipment ? EquipmentType.PRIORITY : EquipmentType.REGULAR;

        return new String[]{
            String.valueOf(equipment.getId()),
            equipment.getName(), 
            String.valueOf(equipment.getDailyPrice()),
            type.name()
        };
    
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

    private enum EquipmentType {
        REGULAR {
            @Override
            public Equipment fromCSV(String[] row) {
                return new Equipment(Integer.parseInt(row[0]), row[1], Double.parseDouble(row[2]));
            }
        },
        PRIORITY {
            @Override
            public Equipment fromCSV(String[] row) {
                return new PriorityEquipment(Integer.parseInt(row[0]), row[1], Double.parseDouble(row[2]));
            }
        };

        public abstract Equipment fromCSV(String[] row);

    }

}
