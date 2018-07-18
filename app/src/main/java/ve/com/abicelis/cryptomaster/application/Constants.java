package ve.com.abicelis.cryptomaster.application;

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
    public static final int MISC_OK_HTTP_READ_TIMEOUT_SECONDS = 5;
    public static final int MISC_OK_HTTP_CONNECT_TIMEOUT_SECONDS = 5;
    public static final int MISC_MAX_CHART_ENTRIES = 100;
    public static final CoinsSortType EXTRA_DEFAULT_COINS_SORT_TYPE = CoinsSortType.MCAP_DESCENDING;


    /* HOME ACTIVITY */
    public static final int    MISC_START_HOME_PAGE = 2;

    /* COIN DETAIL ACTIVITY */
    public static final String EXTRA_COIN_DETAIL_COIN_ID = "EXTRA_COIN_DETAIL_COIN_ID";

    /* COIN FRAGMENT */
    public static final int     COINS_FRAGMENT_FAVORITE_DELAY = 3000;
    public static final String  COINS_FRAGMENT_TYPE = "COINS_FRAGMENT_TYPE";
    //public static final long    COINS_FRAGMENT_MAX_SECONDS_SINCE_LAST_UPDATE = 300;    //60 * 5. 5min
    public static final long    COINS_FRAGMENT_MAX_SECONDS_SINCE_LAST_UPDATE = 1;


    /* SHARED PREFERENCES */
    public static final String  SHARED_PREFERENCE_APP_THEME_TYPE = "SHARED_PREFERENCE_APP_THEME_TYPE";
    public static final String  SHARED_PREFERENCE_COINS_TO_FETCH = "SHARED_PREFERENCE_COINS_TO_FETCH";
    public static final String  SHARED_PREFERENCE_CURRENCY = "SHARED_PREFERENCE_CURRENCY";


    /* ROOM DATABASE */
    public static final String ROOM_DATABASE_NAME = "crypto_master.db";


    /* COINMARKETCAP API */
    public static final String COINMARKETCAP_BASE_URL = "https://api.coinmarketcap.com/v2/";
    public static final String COINMARKETCAP_S2_BASE_URL = "https://s2.coinmarketcap.com/";
    public static final String COINMARKETCAP_GRAPHS_BASE_URL = "https://graphs2.coinmarketcap.com/";
    public static final String COINMARKETCAP_ICONS_BASE_URL = "https://s2.coinmarketcap.com/static/img/coins/16x16/%1$s.png";

    /* CRYPTOCOMPARE API */
    public static final String CRYPTOCOMPARE_BASE_URL = "https://api.qwant.com/api/search/";

}
