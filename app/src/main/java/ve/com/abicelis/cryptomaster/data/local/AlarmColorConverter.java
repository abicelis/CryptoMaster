package ve.com.abicelis.cryptomaster.data.local;

import android.arch.persistence.room.TypeConverter;

import timber.log.Timber;
import ve.com.abicelis.cryptomaster.data.model.AlarmColor;

/**
 * Created by abicelis on 11/9/2018.
 */
public class AlarmColorConverter {


    @TypeConverter
    public static AlarmColor toAlarmColor(String alarmColorString) {
        if(alarmColorString == null)
            return null;
        try {
            return AlarmColor.valueOf(alarmColorString);
        } catch (IllegalArgumentException e) {
            Timber.e(e, "Invalid alarmColorString, cannot parse into AlarmColor");
            return null;
        }
    }

    @TypeConverter
    public static String toStr(AlarmColor alarmColor) {
        if(alarmColor == null)
            return null;
        return alarmColor.name();
    }

}
