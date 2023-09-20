package xhr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import xhr.modules.Client;
import xhr.modules.Equipment;
import xhr.modules.Rent;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EquipmentTest {

    private static final Equipment equipment = new Equipment(1, "teste", 12.50);
    private static final Client client = new Client(1, "cliente");
    private static final List<Rent> rents = new ArrayList<>();
    private static final Rent rent = new Rent(1, LocalDate.of(2023, 9, 10), LocalDate.of(2023, 9, 17),  client, equipment);

    @BeforeAll
    public static void createRent() {
        equipment.addRent(rent);
        rents.add(rent);
    }

    @Test
    public void rentEquipmentAvailable() {
        assertEquals(rents, equipment.getRents(), "Should add rent to rent list in equipment");
    }

    @Test
    public void rentEquipmentUnavailable() {
        Rent ilegalRent = new Rent(1, LocalDate.of(2023, 9, 14), LocalDate.of(2023, 9, 16), client, equipment);
        equipment.addRent(ilegalRent);
        assertEquals(rents, equipment.getRents(), "Shouldn't add rent to rent list in equipment");
    }

    @Test
    public void checkIsRetened() {
        boolean isRetened = equipment.isRented(LocalDate.of(2023, 9, 14), LocalDate.of(2023, 9, 16));
        assertTrue(isRetened, "Should return true");
    }

    @Test
    public void deleteRent() {
        equipment.deleteRent(2);
        assertEquals(rents, equipment.getRents(), "Should delete rent from rent list in equipment");
    }
}
