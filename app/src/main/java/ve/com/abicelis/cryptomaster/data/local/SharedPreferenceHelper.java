package ve.com.abicelis.cryptomaster.data.local;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.CryptoMasterApplication;
import ve.com.abicelis.cryptomaster.data.model.AlarmFrequency;
import ve.com.abicelis.cryptomaster.data.model.AppThemeType;
import ve.com.abicelis.cryptomaster.data.model.CoinsToFetch;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.data.model.StartFragment;

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
            Timber.w("getCoinsToFetch() found null, setting TOP_50");
            pref = CoinsToFetch.TOP_50;
            setCoinsToFetch(pref);
        }

        return pref;
    }
    public void setCoinsToFetch(CoinsToFetch value) {
        mSharedPreferences.edit().putString(Constants.SHARED_PREFERENCE_COINS_TO_FETCH, value.name()).apply();
    }







    /* CURRENCY */
    public Currency getDefaultCurrency() {
        String value = mSharedPreferences.getString(Constants.SHARED_PREFERENCE_CURRENCY, null);
        Currency pref;
        try {
            pref = Currency.valueOf(value);
        } catch (Exception e) {
            pref = null;
        }

        if(pref == null) {
            Timber.w("getDefaultCurrency() found null, setting USD");
            pref = Currency.USD;
            setDefaultCurrency(pref);
        }

        return pref;
    }
    public void setDefaultCurrency(Currency value) {
        mSharedPreferences.edit().putString(Constants.SHARED_PREFERENCE_CURRENCY, value.name()).apply();
    }




    /* START FRAGMENT */
    public StartFragment getStartFragment() {
        String value = mSharedPreferences.getString(Constants.SHARED_PREFERENCE_START_FRAGMENT, null);
        StartFragment pref;
        try {
            pref = StartFragment.valueOf(value);
        } catch (Exception e) {
            pref = null;
        }

        if(pref == null) {
            Timber.w("getStartFragment() found null, setting COINS");
            pref = StartFragment.COINS;
            setStartFragment(pref);
        }

        return pref;
    }
    public void setStartFragment(StartFragment value) {
        mSharedPreferences.edit().putString(Constants.SHARED_PREFERENCE_START_FRAGMENT, value.name()).apply();
    }



    /* ALARM FREQUENCY */
    public AlarmFrequency getAlarmFrequency() {
        String value = mSharedPreferences.getString(Constants.SHARED_PREFERENCE_ALARM_FREQUENCY, null);
        AlarmFrequency pref;
        try {
            pref = AlarmFrequency.valueOf(value);
        } catch (Exception e) {
            pref = null;
        }

        if(pref == null) {
            Timber.w("getAlarmFrequency() found null, setting _1_HOUR");
            pref = AlarmFrequency._1_HOUR;
            setAlarmFrequency(pref);
        }

        return pref;
    }
    public void setAlarmFrequency(AlarmFrequency value) {
        mSharedPreferences.edit().putString(Constants.SHARED_PREFERENCE_ALARM_FREQUENCY, value.name()).apply();
    }



    /* BTC DEFAULT AT COIN DETAIL ACTIVITY */
    public boolean isBtcDefaultAtCoinDetailActivity() {
        return mSharedPreferences.getBoolean(Constants.SHARED_PREFERENCE_COIN_DETAIL_BTC_DEFAULT, true);
    }
    public void setBtcDefaultAtCoinDetailActivity(boolean value) {
        mSharedPreferences.edit().putBoolean(Constants.SHARED_PREFERENCE_COIN_DETAIL_BTC_DEFAULT, value).apply();
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
            Timber.w("getAppThemeType() found null, setting DARK");
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
