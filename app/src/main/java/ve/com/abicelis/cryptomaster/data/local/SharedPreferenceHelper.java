package ve.com.abicelis.cryptomaster.data.local;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.CryptoMasterApplication;
import ve.com.abicelis.cryptomaster.data.model.AppThemeType;
import ve.com.abicelis.cryptomaster.data.model.CoinsToFetch;
import ve.com.abicelis.cryptomaster.util.LongUtil;

/**
 * Created by abice on 1/4/2017.
 */

public class SharedPreferenceHelper {

    private SharedPreferences mSharedPreferences;

    public SharedPreferenceHelper() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(CryptoMasterApplication.getAppContext());
    }

    /* COINS TO FETCH */
    public CoinsToFetch getCoinsToFetch() {
        String value = mSharedPreferences.getString(Constants.SHARED_PREFERENCE_COINS_TO_FETCH, null);
        CoinsToFetch pref;
        try {
            pref = CoinsToFetch.valueOf(value);
        } catch (Exception e) {
            pref = null;
        }

        if(pref == null) {
            Timber.d("getDateFormat() found null, setting TOP_50");
            pref = CoinsToFetch.TOP_50;
            setCoinsToFetch(pref);
        }

        return pref;
    }
    public void setCoinsToFetch(CoinsToFetch value) {
        mSharedPreferences.edit().putString(Constants.SHARED_PREFERENCE_COINS_TO_FETCH, value.name()).apply();
    }




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
