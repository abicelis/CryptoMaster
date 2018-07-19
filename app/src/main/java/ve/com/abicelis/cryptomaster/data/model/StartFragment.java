package ve.com.abicelis.cryptomaster.data.model;

/**
 * Created by abicelis on 19/7/2018.
 */
public enum StartFragment {

    ALERTS(0),
    MARKET(1),
    COINS(2),
    FAVORITES(3)
    ;

    private int fragmentIndex;

    StartFragment(int index) {
        fragmentIndex = index;
    }

    public int getFragmentIndex() { return fragmentIndex; }


}
