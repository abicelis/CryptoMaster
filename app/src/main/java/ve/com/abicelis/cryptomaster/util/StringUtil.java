package ve.com.abicelis.cryptomaster.util;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Created by abicelis on 16/6/2018.
 */
public class StringUtil {

    public static String withSuffix(double count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format(Locale.getDefault(), "%.1f%c",
                count / Math.pow(1000, exp),
                "KMBtqQsSond".charAt(exp-1));
    }


    public static String doubleMaxTwoDecimals(double input) {
        DecimalFormat df = new DecimalFormat("0.####");
        return df.format(input);
    }

}
