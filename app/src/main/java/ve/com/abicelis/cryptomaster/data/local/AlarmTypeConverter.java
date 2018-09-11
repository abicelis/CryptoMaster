package ve.com.abicelis.cryptomaster.data.local;

import android.arch.persistence.room.TypeConverter;

import timber.log.Timber;
import ve.com.abicelis.cryptomaster.data.model.AlarmType;

/**
 * Created by abicelis on 11/9/2018.
 */
public class AlarmTypeConverter {


    @TypeConverter
    public static AlarmType toAlarmType(String alarmTypeString) {
        if(alarmTypeString == null)
            return null;
        try {
            return AlarmType.valueOf(alarmTypeString);
        } catch (IllegalArgumentException e) {
            Timber.e(e, "Invalid alarmTypeString, cannot parse into AlarmType");
            return null;
        }
    }

    @TypeConverter
    public static String toStr(AlarmType alarmType) {
        if(alarmType == null)
            return null;
        return alarmType.name();
    }

}
