package ve.com.abicelis.cryptomaster.application;

import com.github.mikephil.charting.animation.Easing;

import ve.com.abicelis.cryptomaster.data.model.CoinsSortType;

/**
 * Created by abicelis on 24/5/2018.
 * App-wide constants file
 * Element	                    Field Name Prefix
 * SharedPreferences	        PREF_
 * Bundle	                    BUNDLE_
 * Fragment Arguments	        ARGUMENT_
 * Intent Extra	                EXTRA_
 * Intent Action	            ACTION_
 * Start Activity for result    RESULT_
 * Constant                     CONST_
 * Miscellaneous                MISC_
 */

public class Constants {

    /* MISC */
    public static final int                     MISC_OK_HTTP_READ_TIMEOUT_SECONDS =                 2;
    public static final int                     MISC_OK_HTTP_CONNECT_TIMEOUT_SECONDS =              2;
    public static final int                     MISC_MAX_CHART_ENTRIES =                            100;
    public static final int                     MISC_MAX_EXCHANGES_ENTRIES =                        20;
    public static final int                     MISC_MAX_DECIMAL_NUMBERS =                          5;
    public static final int                     MISC_BILLION_DIVIDER =                              1000000000;
    public static final CoinsSortType           EXTRA_DEFAULT_COINS_SORT_TYPE =                     CoinsSortType.MCAP_DESCENDING;
    public static final Easing.EasingOption     MISC_CHART_ANIMATION_EASING =                       Easing.EasingOption.EaseInOutSine;
    public static final int                     MISC_CHART_ANIMATION_DURATION =                     500;
    public static final int                     MISC_COINMARKETCAP_BTC_ID =                         1;
    public static final String                  ALARM_WORKER_TAG =                                  "ALARM_WORKER_TAG";


    /* NEW ALARM ACTIVITY */
    public static final int                     NEW_ALARM_ACTIVITY_RANKED_CACHED_COINS_COINT =             9;


    /* COIN DETAIL ACTIVITY */
    public static final String                  EXTRA_COIN_DETAIL_COIN_ID =                          "EXTRA_COIN_DETAIL_COIN_ID";
    public static final long                    COIN_DETAIL_ACTIVITY_EXCHANGE_PRICE_MAX_DECIMALS =   10;


    /* COIN FRAGMENT */
    public static final int                     COINS_FRAGMENT_FAVORITE_DELAY =                     3000;
    public static final String                  COINS_FRAGMENT_TYPE =                               "COINS_FRAGMENT_TYPE";
    public static final long                    COINS_FRAGMENT_MAX_SECONDS_SINCE_LAST_UPDATE =      1; //300;    //60 * 5. 5min
    public static final long                    COINS_FRAGMENT_COIN_LIST_MAX_DECIMALS =             4;


    /* SHARED PREFERENCES */
    public static final String                  SHARED_PREFERENCE_APP_THEME_TYPE =                  "SHARED_PREFERENCE_APP_THEME_TYPE";
    public static final String                  SHARED_PREFERENCE_COINS_TO_FETCH =                  "SHARED_PREFERENCE_COINS_TO_FETCH";
    public static final String                  SHARED_PREFERENCE_CURRENCY =                        "SHARED_PREFERENCE_CURRENCY";
    public static final String                  SHARED_PREFERENCE_START_FRAGMENT =                  "SHARED_PREFERENCE_START_FRAGMENT";
    public static final String                  SHARED_PREFERENCE_ALARM_FREQUENCY =                 "SHARED_PREFERENCE_ALARM_FREQUENCY";
    public static final String                  SHARED_PREFERENCE_COIN_DETAIL_BTC_DEFAULT =         "SHARED_PREFERENCE_COIN_DETAIL_BTC_DEFAULT";


    /* ROOM DATABASE */
    public static final String                  ROOM_DATABASE_NAME =                                "crypto_master.db";
    public static final int                     ROOM_DATABASE_VERSION =                             6;


    /* COINMARKETCAP API */
    public static final String                  COINMARKETCAP_BASE_URL =                            "https://api.coinmarketcap.com/v2/";
    public static final String                  COINMARKETCAP_S2_BASE_URL =                         "https://s2.coinmarketcap.com/";
    public static final String                  COINMARKETCAP_GRAPHS_BASE_URL =                     "https://graphs2.coinmarketcap.com/";
    public static final String                  COINMARKETCAP_ICONS_16_PX_BASE_URL =                "https://s2.coinmarketcap.com/static/img/coins/16x16/%1$s.png";
    public static final String                  COINMARKETCAP_ICONS_64_PX_BASE_URL =                "https://s2.coinmarketcap.com/static/img/coins/64x64/%1$s.png";


    /* CRYPTOCOMPARE API */
    public static final String                  CRYPTOCOMPARE_BASE_URL =                            "https://www.cryptocompare.com/api/";
    public static final String                  CRYPTOCOMPARE_MIN_BASE_URL =                        "https://min-api.cryptocompare.com/data/";

}
