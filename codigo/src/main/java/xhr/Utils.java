package xhr;

import java.text.NumberFormat;

public class Utils {

    public static String formatCurrency(double value) {
        return NumberFormat.getCurrencyInstance().format(value);
    }

}
