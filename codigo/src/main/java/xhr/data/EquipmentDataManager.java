package xhr.data;

import xhr.App;
import xhr.modules.Equipment;
import xhr.modules.PriorityEquipment;

import java.util.Set;
import java.util.stream.Collectors;

public class EquipmentDataManager extends DataManager<Equipment> {

    public EquipmentDataManager() {
        super(App.DATA_PATH.resolve("equipments.csv"), new String[]{"id", "name", "dailyPrice"});
    }

    @Override
    public Equipment fromCSV(String[] row) {

        Equipment equipment;
        EquipmentType type = EquipmentType.valueOf(row[3]);

        if(type == EquipmentType.PRIORITY) {
            equipment = new PriorityEquipment(Integer.parseInt(row[0]), row[1], Double.parseDouble(row[2]));
        } else {
            equipment = new Equipment(Integer.parseInt(row[0]), row[1], Double.parseDouble(row[2]));
        }

        return equipment;
        
    }

    @Override
    public String[] toCSV(Equipment equipment) {

        EquipmentType type;

        if(equipment instanceof PriorityEquipment) {
            type = EquipmentType.PRIORITY;
        } else {
            type = EquipmentType.REGULAR;
        }

        return new String[]{
            String.valueOf(equipment.getId()),
            equipment.getName(), 
            String.valueOf(equipment.getDailyPrice()),
            String.valueOf(type.name())
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
        REGULAR,
        PRIORITY;
    }

}
