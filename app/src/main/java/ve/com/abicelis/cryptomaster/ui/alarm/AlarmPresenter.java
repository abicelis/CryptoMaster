package ve.com.abicelis.cryptomaster.ui.alarm;

import java.util.concurrent.TimeUnit;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
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

        PeriodicWorkRequest.Builder alarmWorkerBuilder = new PeriodicWorkRequest.Builder(AlarmWorker.class, 1, TimeUnit.MINUTES);
        // ...if you want, you can apply constraints to the builder here...

        // Create the actual work object:
        PeriodicWorkRequest alarmWork = alarmWorkerBuilder.build();
        // Then enqueue the recurring task:
        WorkManager.getInstance().enqueue(alarmWork);

        getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
    }

}
