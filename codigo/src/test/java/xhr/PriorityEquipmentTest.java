package xhr;

import org.junit.jupiter.api.Test;
import xhr.modules.Equipment;
import xhr.modules.PriorityEquipment;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityEquipmentTest {

    private static final PriorityEquipment priorityEquipment = new PriorityEquipment(1, "testePrioritário", 100);

    @Test
    public void testPriorityEquipmentTotalPrice() {

        Equipment priorityEquipment = new PriorityEquipment(1, "testePrioritário", 100);

        helperTestPriorityEquipmentTotalPrice(100, 1);
        helperTestPriorityEquipmentTotalPrice(530, 5);
        helperTestPriorityEquipmentTotalPrice(1035, 9);

    }

    public static void helperTestPriorityEquipmentTotalPrice(double expected, int totalDays) {
        assertEquals(expected, priorityEquipment.getTotalPrice(totalDays),"Priority equipment total price returns correct value for " + totalDays + " day(s) rent.");
    }

}
