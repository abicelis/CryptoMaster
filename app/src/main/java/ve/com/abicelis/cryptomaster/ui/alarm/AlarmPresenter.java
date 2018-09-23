package ve.com.abicelis.cryptomaster.ui.alarm;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 2/9/2018.
 */
public class AlarmPresenter extends BasePresenter<AlarmMvpView> {

    DataManager mDataManager;

    public AlarmPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void getAlarms() {

        //TODO get enabled and disabled and show them accordingly in recycler
        addDisposable(mDataManager.getEnabledAlarms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( alarms -> {
                    getMvpView().showAlarms(alarms);
                }, throwable -> {
                    Timber.e(throwable, "Error getting alarms");
                    getMvpView().showMessage(Message.COULD_NOT_FETCH_ALARM_DATA, null);
                }));
    }

}
