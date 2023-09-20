package xhr.data;

import xhr.App;
import xhr.modules.Client;
import xhr.modules.Equipment;
import xhr.modules.Rent;

import java.nio.file.Path;

public class EquipmentDataManager extends DataManager<Equipment> {

    public EquipmentDataManager() {
        super(App.DATA_PATH.resolve("equipments.csv"), new String[]{"id", "name", "dailyPrice"});
    }

    @Override
    public Equipment fromCSV(String[] row) {
        return new Equipment(Integer.parseInt(row[0]), row[1], Double.parseDouble(row[2]));
    }

    @Override
    public String[] toCSV(Equipment equipment) {
        return new String[]{String.valueOf(equipment.getId()), equipment.getName(), String.valueOf(equipment.getDailyPrice())};
    }

}
