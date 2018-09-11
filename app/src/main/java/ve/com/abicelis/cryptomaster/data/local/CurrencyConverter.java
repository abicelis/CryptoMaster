package ve.com.abicelis.cryptomaster.data.local;

import android.arch.persistence.room.TypeConverter;

import timber.log.Timber;
import ve.com.abicelis.cryptomaster.data.model.Currency;

/**
 * Created by abicelis on 11/9/2018.
 */
public class CurrencyConverter {


    @TypeConverter
    public static Currency toCurrency(String currencyString) {
        if(currencyString == null)
            return null;
        try {
            return Currency.valueOf(currencyString);
        } catch (IllegalArgumentException e) {
            Timber.e(e, "Invalid currencyString, cannot parse into Currency");
            return null;
        }
    }

    @TypeConverter
    public static String toStr(Currency currency) {
        if(currency == null)
            return null;
        return currency.name();
    }

}
