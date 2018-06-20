package ve.com.abicelis.cryptomaster.data.local;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.CryptoMasterApplication;
import ve.com.abicelis.cryptomaster.data.model.AppThemeType;
import ve.com.abicelis.cryptomaster.util.IntUtil;

/**
 * Created by abice on 1/4/2017.
 */

public class SharedPreferenceHelper {

    private SharedPreferences mSharedPreferences;

    public SharedPreferenceHelper() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(CryptoMasterApplication.getAppContext());
    }


    /* RECENT AIRPORTS */
    public int[] getFavoriteCoins() {
        String favoriteCoins = mSharedPreferences.getString(Constants.SHARED_PREFERENCE_FAVORITE_COINS, null);

        if(favoriteCoins != null)
            return IntUtil.ToIntArray(favoriteCoins.split(Constants.SHARED_PREFERENCE_SEPARATOR));

        return new int[0];
    }

    public boolean isFavoriteCoin(int coinId) {
        String favoriteCoins = mSharedPreferences.getString(Constants.SHARED_PREFERENCE_FAVORITE_COINS, null);

        if(favoriteCoins == null)
            return false;

        int[] coinArray = IntUtil.ToIntArray(favoriteCoins.split(Constants.SHARED_PREFERENCE_SEPARATOR));


        for (int coin : coinArray) {
            if (coinId == coin)
                return true;
        }
        return false;
    }

    public void setCoinAsFavorite(int coinId) {
        String favoriteCoins = mSharedPreferences.getString(Constants.SHARED_PREFERENCE_FAVORITE_COINS, null);

        if(favoriteCoins == null) {
            mSharedPreferences.edit().putString(Constants.SHARED_PREFERENCE_FAVORITE_COINS, String.valueOf(coinId)).apply();
            return;
        }

        int[] coinArray = IntUtil.ToIntArray(favoriteCoins.split(Constants.SHARED_PREFERENCE_SEPARATOR));

        //Skip if already favorite
        for (int coin : coinArray) {
            if (coin == coinId)
                return;
        }

            mSharedPreferences.edit().putString(Constants.SHARED_PREFERENCE_FAVORITE_COINS, favoriteCoins
                    + Constants.SHARED_PREFERENCE_SEPARATOR
                    + String.valueOf(coinId)).apply();
    }

    public void removeCoinFromFavorites(int coinId) {
        String favoriteCoins = mSharedPreferences.getString(Constants.SHARED_PREFERENCE_FAVORITE_COINS, null);

        //If null, we're done
        if(favoriteCoins == null)
            return;

        int[] coinArray = IntUtil.ToIntArray(favoriteCoins.split(Constants.SHARED_PREFERENCE_SEPARATOR));

        //Skip if already favorite
        String newCoins = "";
        for (int coin : coinArray) {
            if (coin != coinId)
                newCoins += String.valueOf(coin) + Constants.SHARED_PREFERENCE_SEPARATOR;
        }
        if(newCoins.length() > 0) {
            newCoins = newCoins.substring(0, newCoins.length() - 1);
            mSharedPreferences.edit().putString(Constants.SHARED_PREFERENCE_FAVORITE_COINS, newCoins).apply();
        } else {
            mSharedPreferences.edit().putString(Constants.SHARED_PREFERENCE_FAVORITE_COINS, null).apply();
        }

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
