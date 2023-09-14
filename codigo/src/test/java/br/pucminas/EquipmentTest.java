package br.pucminas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.pucminas.modules.Client;
import br.pucminas.modules.Equipment;
import br.pucminas.modules.Rent;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EquipmentTest {

    private static final Equipment equipment = new Equipment("teste", 12.50);
    private static final Client client = new Client("cliente");
    private static final List<Rent> rents = new ArrayList<>();
    private static final Rent rent = new Rent(LocalDate.of(2023, 9, 17), client, equipment);

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
        Rent rent = new Rent(LocalDate.of(2023, 9, 14), LocalDate.of(2023, 9, 16), client, equipment);
        equipment.addRent(rent);
        assertEquals(rents, equipment.getRents(), "Shouldn't add rent to rent list in equipment");
    }

    @Test
    public void checkIsRetened() {
        boolean isRetened = equipment.isRetened(LocalDate.of(2023, 9, 14), LocalDate.of(2023, 9, 16));
        assertTrue(isRetened, "Should return true");
    }

    @Test
    public void deleteRent() {
        equipment.deleteRent(2);
        assertEquals(rents, equipment.getRents(), "Should delete rent from rent list in equipment");
    }
}
