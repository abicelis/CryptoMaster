package ve.com.abicelis.cryptomaster.ui.alarm;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.Alarm;
import ve.com.abicelis.cryptomaster.data.model.AlarmColor;
import ve.com.abicelis.cryptomaster.data.model.AlarmType;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.data.model.ExchangeType;
import ve.com.abicelis.cryptomaster.service.AlarmWorker;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

/**
 * Created by abicelis on 2/9/2018.
 */
public class AlarmPresenter extends BasePresenter<AlarmMvpView> {

    DataManager mDataManager;

    public AlarmPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void buttonClicked() {

//        PeriodicWorkRequest.Builder alarmWorkerBuilder = new PeriodicWorkRequest.Builder(AlarmWorker.class, 1, TimeUnit.MINUTES);
//        // ...if you want, you can apply constraints to the builder here...
//
//        // Create the actual work object:
//        PeriodicWorkRequest alarmWork = alarmWorkerBuilder.build();
//        // Then enqueue the recurring task:
//        WorkManager.getInstance().enqueue(alarmWork);

        Alarm alarm = new Alarm(Currency.BCH, Currency.USD, 3.2398, ExchangeType.COINMARKETCAP, AlarmType.ABOVE, AlarmColor.COLOR_1, "Some note");
        insertAlarm(alarm);

        //etMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
    }

    public void getAlarms() {
        addDisposable(mDataManager.getAlarms()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( alarms -> {
                    getMvpView().showAlarms(alarms);
                }, throwable -> {
                    Timber.e(throwable, "Error getting alarms");
                    getMvpView().showMessage(Message.COULD_NOT_FETCH_ALARM_DATA, null);
                }));
    }


    public void insertAlarm(Alarm alarm) {

        new AsyncTask<Alarm, Void, Integer>() {
            @Override
            protected Integer doInBackground(Alarm... alarms) {
                mDataManager.insertAlarm(alarms[0]);
                return 1;
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                getAlarms();
            }
        }.execute(alarm);
    }
}
