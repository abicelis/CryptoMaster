package ve.com.abicelis.cryptomaster.data.local;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.CryptoMasterApplication;
import ve.com.abicelis.cryptomaster.data.model.AppThemeType;

/**
 * Created by abice on 1/4/2017.
 */

public class SharedPreferenceHelper {

    private SharedPreferences mSharedPreferences;

    public SharedPreferenceHelper() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(CryptoMasterApplication.getAppContext());
    }


//    /* TIME FORMAT */
//    public TimeFormat getTimeFormat() {
//        String value = mSharedPreferences.getString(Constants.SHARED_PREFERENCE_TIME_FORMAT, null);
//        TimeFormat pref;
//        try {
//            pref = TimeFormat.valueOf(value);
//        } catch (Exception e) {
//            pref = null;
//        }
//
//        if(pref == null) {
//            Timber.d("getTimeFormat() found null, setting HOUR_24");
//            pref = TimeFormat.HOUR_24;
//            setTimeFormat(pref);
//        }
//
//        return pref;
//    }
//    public void setTimeFormat(TimeFormat value) {
//        mSharedPreferences.edit().putString(Constants.SHARED_PREFERENCE_TIME_FORMAT, value.name()).apply();
//    }
//
//    /* DATE FORMAT */
//    public DateFormat getDateFormat() {
//        String value = mSharedPreferences.getString(Constants.SHARED_PREFERENCE_DATE_FORMAT, null);
//        DateFormat pref;
//        try {
//            pref = DateFormat.valueOf(value);
//        } catch (Exception e) {
//            pref = null;
//        }
//
//        if(pref == null) {
//            Timber.d("getDateFormat() found null, setting PRETTY_DATE");
//            pref = DateFormat.PRETTY_DATE;
//            setDateFormat(pref);
//        }
//
//        return pref;
//    }
//    public void setDateFormat(DateFormat value) {
//        mSharedPreferences.edit().putString(Constants.SHARED_PREFERENCE_DATE_FORMAT, value.name()).apply();
//    }




    /* APP THEME TYPE */
    public AppThemeType getAppThemeType() {
        String value = mSharedPreferences.getString(Constants.SHARED_PREFERENCE_APP_THEME_TYPE, null);
        AppThemeType pref;
        try {
            pref = AppThemeType.valueOf(value);
        } catch (Exception e) {
            pref = null;
        }

        if(pref == null) {
            Timber.d("getAppThemeType() found null, setting DARK");
            pref = AppThemeType.DARK;
            setAppThemeType(pref);
        }

        return pref;
    }
    public AppThemeType toggleAppThemeType() {
        AppThemeType appTheme = getAppThemeType();
        appTheme = (appTheme==AppThemeType.DARK ? AppThemeType.LIGHT : AppThemeType.DARK);
        mSharedPreferences.edit().putString(Constants.SHARED_PREFERENCE_APP_THEME_TYPE, appTheme.name()).apply();
        return appTheme;
    }
    public void setAppThemeType(AppThemeType value) {
        mSharedPreferences.edit().putString(Constants.SHARED_PREFERENCE_APP_THEME_TYPE, value.name()).apply();
    }
}
