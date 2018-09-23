package ve.com.abicelis.cryptomaster.util;

import java.util.concurrent.TimeUnit;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.data.model.AlarmFrequency;
import ve.com.abicelis.cryptomaster.service.AlarmWorker;

/**
 * Created by abicelis on 21/9/2018.
 */
public class AlarmWorkerUtil {

    public static void resetAlarmWorker(AlarmFrequency alarmFrequency) {
        Timber.i("Resetting alarmWorker (Frequency = " + alarmFrequency.getFrequencyMinutes() + " minutes)");
        WorkManager.getInstance().cancelAllWorkByTag(Constants.ALARM_WORKER_TAG);
        PeriodicWorkRequest.Builder alarmWorkerBuilder = new PeriodicWorkRequest.Builder(AlarmWorker.class, alarmFrequency.getFrequencyMinutes(), TimeUnit.MINUTES)
                .addTag(Constants.ALARM_WORKER_TAG);
        PeriodicWorkRequest alarmWork = alarmWorkerBuilder.build();
        WorkManager.getInstance().enqueue(alarmWork);
    }
}
