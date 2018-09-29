package ve.com.abicelis.cryptomaster.ui.alarm;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.Alarm;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;
import ve.com.abicelis.cryptomaster.util.RxJavaUtil;

/**
 * Created by abicelis on 2/9/2018.
 */
public class AlarmPresenter extends BasePresenter<AlarmMvpView> {

    DataManager mDataManager;

    public AlarmPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void getAlarms() {

        addDisposable(mDataManager.getAlarmsSortedByEnabled()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( alarms -> {
                    getMvpView().showAlarms(alarms);
                }, throwable -> {
                    Timber.e(throwable, "Error getting alarms");
                    getMvpView().showMessage(Message.COULD_NOT_FETCH_ALARM_DATA, null);
                }));
    }

    public void alarmEnabledOrDisabled(Alarm alarm, boolean enabled) {
        if(enabled) {
            addDisposable(mDataManager.enableAlarm(alarm.getId())
                    .compose(RxJavaUtil.applySchedulersAndroidMainThread())
                    .subscribe( () -> getMvpView().showAlarmMessage(enabled, alarm.getFromCurrency().getCode(), alarm.getToCoinCode())
                    ,throwable -> {
                        getAlarms();
                        getMvpView().showMessage(Message.COULD_NOT_UPDATE_ALARM, null);
                        Timber.e(throwable, "Error enabling alarm. AlarmID= %d", alarm.getId());
                    }));

        } else {
            addDisposable(mDataManager.disableAlarm(alarm.getId())
                    .compose(RxJavaUtil.applySchedulersAndroidMainThread())
                    .subscribe( () -> getMvpView().showAlarmMessage(enabled, alarm.getFromCurrency().getCode(), alarm.getToCoinCode())
                    ,throwable -> {
                        getAlarms();
                        getMvpView().showMessage(Message.COULD_NOT_UPDATE_ALARM, null);
                        Timber.e(throwable, "Error enabling alarm. AlarmID= %d", alarm.getId());
                    }));
        }
    }

    public void alarmClicked(Alarm alarm) {
        getMvpView().editAlarm(alarm);
    }
}
