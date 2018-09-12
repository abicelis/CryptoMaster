package ve.com.abicelis.cryptomaster.ui.alarm;

import java.util.List;

import ve.com.abicelis.cryptomaster.data.model.Alarm;
import ve.com.abicelis.cryptomaster.ui.base.MvpView;

/**
 * Created by abicelis on 2/9/2018.
 */
interface AlarmMvpView extends MvpView {
    void showAlarms(List<Alarm> alarmList);

}
