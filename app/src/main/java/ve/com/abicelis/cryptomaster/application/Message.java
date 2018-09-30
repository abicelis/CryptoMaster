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
    COIN_DETAIL_COIN_ID_MISSING(R.string.coin_detail_coin_id_missing, SnackbarUtil.SnackbarType.ERROR),
    COULD_NOT_FETCH_FRESH_COIN_DATA(R.string.could_not_fetch_fresh_coin_data, SnackbarUtil.SnackbarType.NOTICE),
    COULD_NOT_FETCH_QUOTE(R.string.could_not_fetch_quote, SnackbarUtil.SnackbarType.ERROR),
    COULD_NOT_FETCH_COIN_DATA(R.string.could_not_fetch_coin_data, SnackbarUtil.SnackbarType.NOTICE),
    COULD_NOT_FETCH_CHART_DATA(R.string.could_not_fetch_chart_data, SnackbarUtil.SnackbarType.NOTICE),
    COULD_NOT_FETCH_EXCHANGE_DATA(R.string.could_not_fetch_exchange_data, SnackbarUtil.SnackbarType.NOTICE),
    COULD_NOT_FETCH_ALARM_DATA(R.string.could_not_fetch_alarm_data, SnackbarUtil.SnackbarType.NOTICE),
    COULD_NOT_INSERT_ALARM(R.string.could_not_insert_alarm, SnackbarUtil.SnackbarType.ERROR),
    COULD_NOT_UPDATE_ALARM(R.string.could_not_update_alarm, SnackbarUtil.SnackbarType.ERROR),
    SUCCESS_INSERTING_ALARM(R.string.success_inserting_alarm, SnackbarUtil.SnackbarType.SUCCESS),
    SUCCESS_UPDATING_ALARM(R.string.success_updating_alarm, SnackbarUtil.SnackbarType.SUCCESS),
    DUPLICATE_BASE_QUOTE_COIN(R.string.duplicate_base_quote_coin, SnackbarUtil.SnackbarType.NOTICE),
    DUPLICATE_QUOTE_BASE_COIN(R.string.duplicate_quote_base_coin, SnackbarUtil.SnackbarType.NOTICE),

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
