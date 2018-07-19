package ve.com.abicelis.cryptomaster.data.model;

/**
 * Created by abicelis on 19/7/2018.
 */
public enum StartFragment {

    ALERTS(0, "Alerts Page"),
    MARKET(1, "Market Page"),
    COINS(2, "Coins Page"),
    FAVORITES(3, "Favorite Coins Page")
    ;

    private int mFragmentIndex;
    private String mFriendlyName;

    StartFragment(int index, String friendlyName) {
        mFragmentIndex = index;
        mFriendlyName = friendlyName;
    }

    public int getFragmentIndex() { return mFragmentIndex; }
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
