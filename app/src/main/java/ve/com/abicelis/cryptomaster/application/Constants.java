package ve.com.abicelis.cryptomaster.application;

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
    public static final int    MISC_IMAGE_JPEG_COMPRESSION_PERCENTAGE = 50;
    public static final int    MISC_ALPHA_ANIMATIONS_DURATION = 200;
    public static final int    MISC_REVEAL_ANIMATION_DURATION = 1000;
    public static final int    MISC_UI_DEBOUNCE_TIME_MILLISECONDS = 300;


    /* HOME ACTIVITY */
    public static final int    MISC_START_HOME_PAGE = 2;

    /* COIN FRAGMENT */
    public static final int    COIN_FRAGMENT_FAVORITE_DELAY = 3000;


    /* FLIGHT ACTIVITY */
//    public static final String  EXTRA_ACTIVITY_FLIGHT_TRIP_ID = "EXTRA_ACTIVITY_FLIGHT_TRIP_ID";
//    public static final String  EXTRA_ACTIVITY_FLIGHT_FLIGHT_ID = "EXTRA_ACTIVITY_FLIGHT_FLIGHT_ID";
//    public static final String  EXTRA_ACTIVITY_FLIGHT_FLIGHT_POSITION = "EXTRA_ACTIVITY_FLIGHT_FLIGHT_POSITION";
//    public static final String  TAG_ACTIVITY_FLIGHT_AIRPORT_AIRLINE_SEARCH_FRAGMENT = "TAG_ACTIVITY_FLIGHT_AIRPORT_AIRLINE_SEARCH_FRAGMENT";
//    public static final String  TAG_ACTIVITY_FLIGHT_DATE_SELECT_FRAGMENT = "TAG_ACTIVITY_FLIGHT_DATE_SELECT_FRAGMENT";
//    public static final String  TAG_ACTIVITY_FLIGHT_NUMBER_SELECT_FRAGMENT = "TAG_ACTIVITY_FLIGHT_NUMBER_SELECT_FRAGMENT";
//    public static final String  TAG_ACTIVITY_FLIGHT_FLIGHT_RESULTS_FRAGMENT = "TAG_ACTIVITY_FLIGHT_FLIGHT_RESULTS_FRAGMENT";



    /* SHARED PREFERENCES */
    public static final String  SHARED_PREFERENCE_TIME_FORMAT = "SHARED_PREFERENCE_TIME_FORMAT";
    public static final String  SHARED_PREFERENCE_DATE_FORMAT = "SHARED_PREFERENCE_DATE_FORMAT";
    public static final String  SHARED_PREFERENCE_APP_THEME_TYPE = "SHARED_PREFERENCE_APP_THEME_TYPE";
    public static final String  SHARED_PREFERENCE_SEPARATOR = "-";
    public static final String  SHARED_PREFERENCE_FAVORITE_COINS = "SHARED_PREFERENCE_FAVORITE_COINS";


    /* ROOM DATABASE */
    public static final String ROOM_DATABASE_NAME = "crypto_master.db";
    public static final String ROOM_DATABASE_CALENDAR_CONVERTER_SEPARATOR = "!!!";
    public static final int    ROOM_DATABASE_MAX_SEARCH_RESULTS = 10;


    /* COINMARKETCAP API */
    public static final String COINMARKETCAP_BASE_URL = "https://api.coinmarketcap.com/v2/";
    public static final String COINMARKETCAP_S2_BASE_URL = "https://s2.coinmarketcap.com/";
    public static final String COINMARKETCAP_GRAPHS_BASE_URL = "https://graphs2.coinmarketcap.com/";
    public static final String COINMARKETCAP_ICONS_BASE_URL = "https://s2.coinmarketcap.com/static/img/coins/16x16/%1$s.png";

    /* CRYPTOCOMPARE API */
    public static final String CRYPTOCOMPARE_BASE_URL = "https://api.qwant.com/api/search/";

}
