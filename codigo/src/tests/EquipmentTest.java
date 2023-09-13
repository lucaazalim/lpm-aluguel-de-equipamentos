package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modules.Client;
import modules.Equipment;
import modules.Rent;

public class EquipmentTest {
    private Equipment equipment = new Equipment("teste", 12.50);
    private Client client = new Client();
    private List<Rent> rents = new ArrayList<>();
    private Rent rent = new Rent(LocalDate.of(2023, 9, 17), client, equipment);

    @Before
    public void createRent() {
        equipment.addRent(rent);
        rents.add(rent);
    }

    @Test
    public void rentEquipmentAvailable() {
        assertEquals(rents, equipment.getRents(), "Should add rent to rent list in equipment");
    }

    @Test
    public void rentEquipmentUnavailable() {
        Rent rent = new Rent(LocalDate.of(2023, 9, 14), LocalDate.of(2023, 9, 16), client, equipment);
        equipment.addRent(rent);
        assertEquals(rents, equipment.getRents(), "Shouldn't add rent to rent list in equipment");
    }

    @Test
    public void checkIsRetened() {
        boolean isRetened = equipment.isRetened(LocalDate.of(2023, 9, 14), LocalDate.of(2023, 9, 16));
        assertEquals(true, isRetened, "Should return true");
    }

    @Test
    public void deleteRent() {
        equipment.deleteRent(2);
        assertEquals(rents, equipment.getRents(), "Should delete rent from rent list in equipment");
    }
}
