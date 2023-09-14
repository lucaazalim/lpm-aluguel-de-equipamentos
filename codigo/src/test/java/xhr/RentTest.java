package xhr;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

public class RentTest {
    
    /*@Test
    public void testIsPeriodValid(){
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(2023,9,24);
        boolean isPeriodValid = startDate.isBefore(endDate);
        assertTrue(isPeriodValid);

        LocalDate starDateFalse = LocalDate.now();
        LocalDate endDateFalse = LocalDate.of(2023, 9, 11);
        boolean isPeriodInvalid = starDateFalse.isBefore(endDateFalse);
        assertFalse(isPeriodInvalid);
    }
    
    @Test
    public void testCalculeteTotalPrice(){
        LocalDate starDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(2023, 9,18);
        Period difference = Period.between(starDate, endDate);
        int totalDays = difference.getDays();
        double dailyPrice = 100.00;
        double totalPrice = totalDays * dailyPrice;
        double expected = 500.00;
        assertEquals(expected, totalPrice, 0.01);
    }*/

}
