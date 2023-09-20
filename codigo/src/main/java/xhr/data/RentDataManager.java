package xhr.data;

import com.opencsv.exceptions.CsvException;
import xhr.App;
import xhr.modules.Client;
import xhr.modules.Equipment;
import xhr.modules.Rent;

import java.io.IOException;
import java.time.LocalDate;

public class RentDataManager extends DataManager<Rent> {

    public RentDataManager() {
        super(App.DATA_PATH.resolve("rents.csv"), new String[]{"id", "startDate", "endDate", "clientId", "equipmentId"});
    }

    @Override
    public Rent fromCSV(String[] row) {
        return new Rent(
                Integer.parseInt(row[0]),
                LocalDate.parse(row[1], App.DATE_FORMATTER),
                LocalDate.parse(row[2], App.DATE_FORMATTER),
                Client.DATA.getById(Integer.parseInt(row[3])),
                Equipment.DATA.getById(Integer.parseInt(row[4]))
        );
    }

    @Override
    public String[] toCSV(Rent rent) {
        return new String[]{
                String.valueOf(rent.getId()),
                rent.getStartDate().format(App.DATE_FORMATTER),
                rent.getEndDate().format(App.DATE_FORMATTER),
                String.valueOf(rent.getClient().getId()),
                String.valueOf(rent.getEquipment().getId())
        };
    }

    @Override
    public void load() throws IOException, CsvException {

        super.load();

        for(Rent rent : this.getAll()) {
            rent.getEquipment().addRent(rent);
        }

    }
}
