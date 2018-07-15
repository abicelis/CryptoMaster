package ve.com.abicelis.cryptomaster.data.model;

import android.content.Context;
import android.support.annotation.StringRes;

import ve.com.abicelis.cryptomaster.R;

/**
 * Created by abicelis on 14/7/2018.
 */
public enum CoinsToFetch {
    TOP_50(R.string.coins_to_fetch_top_50),
    TOP_100(R.string.coins_to_fetch_top_100),
    TOP_500(R.string.coins_to_fetch_top_500),
    ALL(R.string.coins_to_fetch_all);

    private @StringRes int friendlyNameRes;

    CoinsToFetch(int friendlyNameRes) {
        this.friendlyNameRes = friendlyNameRes;
    }

    public int getFriendlyNameRes() {
        return friendlyNameRes;
    }

    public String getFriendlyName(Context context) {
        return context.getString(friendlyNameRes);
    }
}
