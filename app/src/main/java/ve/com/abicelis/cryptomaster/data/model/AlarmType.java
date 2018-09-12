package ve.com.abicelis.cryptomaster.data.model;

import android.content.Context;
import android.support.annotation.StringRes;

import ve.com.abicelis.cryptomaster.R;

/**
 * Created by abicelis on 11/9/2018.
 */
public enum AlarmType {
    ABOVE(R.string.alarm_type_above, R.string.alarm_type_above_description),
    BELOW(R.string.alarm_type_above, R.string.alarm_type_above_description);


    private @StringRes int friendlyName;
    private @StringRes int description;


    AlarmType(@StringRes int friendlyName, @StringRes int description) {
        this.friendlyName = friendlyName;
        this.description = description;
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

    public String getDescription(Context context) {
        return context.getString(description);
    }
}
