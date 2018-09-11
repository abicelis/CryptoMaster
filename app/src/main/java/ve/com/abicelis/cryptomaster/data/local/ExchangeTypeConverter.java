package ve.com.abicelis.cryptomaster.data.local;

import android.arch.persistence.room.TypeConverter;

import timber.log.Timber;
import ve.com.abicelis.cryptomaster.data.model.ExchangeType;

/**
 * Created by abicelis on 11/9/2018.
 */
public class ExchangeTypeConverter {


    @TypeConverter
    public static ExchangeType toExchangeType(String exchangeTypeString) {
        if(exchangeTypeString == null)
            return null;
        try {
            return ExchangeType.valueOf(exchangeTypeString);
        } catch (IllegalArgumentException e) {
            Timber.e(e, "Invalid exchangeTypeString, cannot parse into ExchangeType");
            return null;
        }
    }

    @TypeConverter
    public static String toStr(ExchangeType exchangeType) {
        if(exchangeType == null)
            return null;
        return exchangeType.name();
    }

}
