package ve.com.abicelis.cryptomaster.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.CryptoMasterApplication;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.util.RxJavaUtil;

/**
 * Created by abicelis on 24/9/2018.
 */
public class AlarmSnoozeReceiver extends BroadcastReceiver {

    @Inject
    DataManager dataManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        CryptoMasterApplication cma = ((CryptoMasterApplication) context.getApplicationContext());
        cma.getApplicationComponent().inject(this);

        long alarmId = intent.getLongExtra(Constants.NOTIFICATION_EXTRA_ALARM_ID, -1);
        long notificationId = intent.getLongExtra(Constants.NOTIFICATION_EXTRA_NOTIFICATION_ID, -1);


        if (alarmId != -1 && notificationId != -1) {
            final CompositeDisposable compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(dataManager.enableAlarm(alarmId)
                    .compose(RxJavaUtil.applySchedulersIo())
                    .doAfterTerminate(() -> {
                        compositeDisposable.dispose();
                        NotificationManagerCompat.from(context).cancel((int)notificationId);
                    })
                    .subscribe(
                            () -> Timber.i("Alarm enabled. Alarm ID = %d", alarmId)
                            ,throwable -> Timber.e(throwable,"Could not enable alarm. Alarm ID = %d", alarmId)
                    ));

        } else
            Timber.e("Did not receive a valid Alarm ID or Notification Id on NOTIFICATION_EXTRA_ALARM_ID or NOTIFICATION_EXTRA_NOTIFICATION_ID");

    }
}
