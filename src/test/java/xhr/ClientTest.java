package xhr;

import xhr.modules.Client;
import xhr.modules.Equipment;
import xhr.modules.Rent;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientTest {

    private static Client client;

    @Test
    @Order(1)
    public void testClientCreation() {

        assertDoesNotThrow(() -> {
            client = new Client(1, "Luca Ferrari Azalim");
        }, "Client creation should not throw an exception");

        assertThrows(NullPointerException.class, () -> {
            client = new Client(2, null);
        }, "Client creation should throw a NullPointerException");

        assertEquals("Luca Ferrari Azalim", client.getName(), "Client name should be Luca Ferrari Azalim");

    }

    @Test
    public void testClientRentEquipment() {

        Equipment equipment = new Equipment(1, "Test Equipment", 10.0);
        Rent rent = new Rent(1, LocalDate.now(), LocalDate.now().plusDays(10), client, equipment);

        assertDoesNotThrow(() -> client.addRent(rent), "Client rent equipment should not throw an exception");

        assertEquals(1, client.getRents().size(), "Client rents should have 1 rent");

    }

}
