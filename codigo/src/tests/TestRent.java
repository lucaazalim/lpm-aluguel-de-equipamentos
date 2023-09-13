package tests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

public class TestRent {
    

    @Test
    public void testIsPeriodValid(){
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(2023,9,24);
        boolean isPeriodValid = startDate.isBefore(endDate);
        assertEquals(true, isPeriodValid);
    }
}
