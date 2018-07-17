package ve.com.abicelis.cryptomaster.application;

import android.content.Context;
import android.support.annotation.StringRes;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;

/**
 * Created by abicelis on 25/5/2018.
 * App-wide Messages
 * ERROR_
 * NOTICE_
 * SUCCESS_
 *
 */

public enum Message {
    ERROR_UNEXPECTED(R.string.error_unexpected, SnackbarUtil.SnackbarType.ERROR),
    COIN_FRAGMENT_TYPE_MISSING(R.string.coin_fragment_type_missing, SnackbarUtil.SnackbarType.ERROR),
    COULD_NOT_FETCH_FRESH_COIN_DATA(R.string.could_not_fetch_fresh_coin_data, SnackbarUtil.SnackbarType.NOTICE),
    COULD_NOT_FETCH_GRAPH_DATA(R.string.could_not_fetch_graph_data, SnackbarUtil.SnackbarType.NOTICE),

    ;

    @StringRes
    int friendlyName;
    SnackbarUtil.SnackbarType messageType;

    Message(@StringRes int friendlyName, SnackbarUtil.SnackbarType messageType) {
        this.friendlyName = friendlyName;
        this.messageType = messageType;
    }


    public @StringRes
    int getFriendlyNameRes() {
        return friendlyName;
    }
    public SnackbarUtil.SnackbarType getMessageType() {
        return messageType;
    }
    public String getFriendlyName(Context context) {
        return CryptoMasterApplication.getAppContext().getString(friendlyName);
    }

}
