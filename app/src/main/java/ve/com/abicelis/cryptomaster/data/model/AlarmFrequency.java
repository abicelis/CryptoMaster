package ve.com.abicelis.cryptomaster.data.model;

/**
 * Created by abicelis on 19/7/2018.
 */
public enum AlarmFrequency {
    _15_MINUTES(15, "15 Minutes"),
    _25_MINUTES(25, "25 Minutes"),
    _40_MINUTES(40, "40 Minutes"),
    _1_HOUR(60, "1 Hour"),
    _2_HOURS(120, "2 Hours"),
    _5_HOUR(300, "5 Hours"),
    _12_HOUR(240, "12 Hours"),
    _1_DAY(480, "1 Day")
    ;

    private int mFrequencyMinutes;
    private String mFriendlyName;

    AlarmFrequency(int frequencyMinutes, String friendlyName) {
        mFrequencyMinutes = frequencyMinutes;
        mFriendlyName = friendlyName;
    }

    public int getFrequencyMinutes() { return mFrequencyMinutes; }
    public String getFriendlyName() { return mFriendlyName; }


    public static CharSequence[] getEntries() {
        CharSequence[] array = new CharSequence[values().length];
        for (int i = 0; i < values().length; i++)
            array[i] = values()[i].getFriendlyName();

        return array;
    }

    public static CharSequence[] getEntryValues() {
        CharSequence[] array = new CharSequence[values().length];
        for (int i = 0; i < values().length; i++)
            array[i] = values()[i].name();

        return array;
    }

}
