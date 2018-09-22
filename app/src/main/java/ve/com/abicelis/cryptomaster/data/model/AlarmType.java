package ve.com.abicelis.cryptomaster.data.model;

import android.content.Context;
import android.support.annotation.StringRes;

import ve.com.abicelis.cryptomaster.R;

/**
 * Created by abicelis on 11/9/2018.
 */
public enum AlarmType {
    ABOVE(R.string.alarm_type_above, R.string.alarm_type_above_description, R.string.alarm_type_above_description_2),
    BELOW(R.string.alarm_type_below, R.string.alarm_type_below_description, R.string.alarm_type_below_description_2);


    private @StringRes int friendlyName;
    private @StringRes int description;
    private @StringRes int description2;


    AlarmType(@StringRes int friendlyName, @StringRes int description, @StringRes int description2) {
        this.friendlyName = friendlyName;
        this.description = description;
        this.description2 = description2;
    }


    public @StringRes int getFriendlyNameRes() {
        return friendlyName;
    }

    public String getFriendlyName(Context context) {
        return context.getString(friendlyName);
    }

    public @StringRes int getDescriptionRes() {
        return description;
    }
    public @StringRes int getDescription2Res() {
        return description2;
    }

    public String getDescription(Context context) {
        return context.getString(description);
    }
    public String getDescription2(Context context) {
        return context.getString(description2);
    }
}
