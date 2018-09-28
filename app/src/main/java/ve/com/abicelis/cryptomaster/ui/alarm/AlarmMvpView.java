package ve.com.abicelis.cryptomaster.ui.alarm;

import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;

import java.util.List;

import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.model.Alarm;
import ve.com.abicelis.cryptomaster.ui.base.MvpView;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;

/**
 * Created by abicelis on 2/9/2018.
 */
interface AlarmMvpView extends MvpView {
    void showAlarmMessage(boolean enabled, String fromCurrencyCode, String toCurrencyCode);
    void showAlarms(List<Alarm> alarmList);
}
