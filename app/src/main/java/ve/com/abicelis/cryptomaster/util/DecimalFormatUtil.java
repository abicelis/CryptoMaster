package ve.com.abicelis.cryptomaster.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by abicelis on 29/9/2018.
 */
public class DecimalFormatUtil {

    public static String formatDecimals(double value) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("#.########", otherSymbols);

        return df.format(value);
    }
}
