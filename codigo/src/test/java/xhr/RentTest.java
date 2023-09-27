package xhr;

import xhr.modules.Client;
import xhr.modules.Equipment;
import xhr.modules.Rent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RentTest {
    
    private Client client;
    private Equipment equipment;
    private Rent rent;

    @BeforeEach
    public void setUp(){
        client = new Client(1,"João");
        equipment = new Equipment(1, "Betoneira", 100);
    }

    @Test
    public void testValidRent(){
        LocalDate  starDate = LocalDate.now();
        LocalDate endDate = starDate.plusDays(5);
        rent =  new Rent(1, starDate, endDate, client, equipment);
        
        assertEquals(1, rent.getId());
        assertEquals(starDate, rent.getStartDate());
        assertEquals(endDate, rent.getEndDate());
        assertEquals(client, rent.getClient());
        
        double expectedPrice = equipment.getDailyPrice() * 5;
        assertEquals(expectedPrice, rent.getPrice(), 0.01);
    }

    @Test 
    public void testInvalidRent(){
        LocalDate starDate = LocalDate.now();
        LocalDate endDate = starDate.plusDays(7);
        rent =  new Rent(1, starDate, endDate, client, equipment);

        assertEquals(1, rent.getId());
        assertEquals(starDate, rent.getStartDate());
        assertEquals(endDate, rent.getEndDate());
        assertEquals(client, rent.getClient());
        
        assertEquals(700, rent.getPrice(), 0.01);
    }

}
