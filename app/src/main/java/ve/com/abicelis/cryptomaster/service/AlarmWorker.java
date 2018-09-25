package ve.com.abicelis.cryptomaster.service;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import androidx.work.Worker;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.CryptoMasterApplication;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.Alarm;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.util.NotificationUtil;

/**
 * Created by abicelis on 5/8/2018.
 */
public class AlarmWorker extends Worker {

    @Inject
    DataManager dataManager;


    @NonNull
    @Override
    public Result doWork() {
        Timber.i("ALARM WORKER RUN");

        Context context = getApplicationContext();
        CryptoMasterApplication cma = ((CryptoMasterApplication) context);
        cma.getApplicationComponent().inject(this);

        //Create notification channel if running Android O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            NotificationUtil.createNotificationChannel(notificationManager, Constants.NOTIFICATION_CHANNEL_ID, Constants.NOTIFICATION_CHANNEL_NAME, Constants.NOTIFICATION_CHANNEL_DESCRIPTION);
        }

        //Get enabled alarms
        List<Alarm> alarmList = dataManager.getEnabledAlarms().blockingGet();
        Timber.i("Found " + alarmList.size() + " enabled alarms");

        for (Alarm alarm : alarmList) {
            try {
                Coin coin = dataManager.getCoinMarketCapTicker(alarm.getToCoinId(), alarm.getFromCurrency()).blockingGet();

                Timber.i(String.format(Locale.getDefault(), "Checking %1$s: Ticker = %2$.2f, Trigger = %3$.2f, Type =  %4$s",
                        alarm.getToCoinCode(), coin.getQuoteDefaultPrice(), alarm.getTriggerValue(), alarm.getAlarmType().name()));

                if (alarm.checkIfShouldTrigger(coin.getQuoteDefaultPrice())) {
                    Timber.i("ALARM TRIGGER!");

                    showNotification(context, alarm, coin);
                    disableAlarm(alarm);
                }
            } catch (Exception e) {
                Timber.e(e, "UNEXPECTED ERROR");
            }
        }


        return Result.SUCCESS;
    }

    private void disableAlarm(Alarm alarm) {
        Throwable throwable = dataManager.disableAlarm(alarm.getId()).blockingGet();
        if (throwable != null)
            Timber.e(throwable,"Could not disable alarm. Alarm ID = %d", alarm.getId());
    }

    private void showNotification(Context context, Alarm alarm, Coin coin) {
        Bitmap bitmap;
        try {

            FutureTarget<Bitmap> futureBitmap = Glide.with(context).asBitmap()
                    .load(String.format(Constants.COINMARKETCAP_ICONS_64_PX_BASE_URL, coin.getId()))
                    .submit();
            bitmap = futureBitmap.get();

        } catch (InterruptedException | ExecutionException e) {
            bitmap = null;
        }

        String aux = String.format(Locale.getDefault(),
                "%1$s %2$s %3$.8f",
                coin.getCode(), alarm.getAlarmType().getDescription2(context), alarm.getTriggerValue());

        String textTitle, textContent;

        if(alarm.getNote() == null || alarm.getNote().isEmpty()) {
            textTitle = coin.getName();
            textContent = aux;
        } else {
            textTitle = aux;
            textContent = alarm.getNote();
        }

        NotificationUtil.showNotification(context, coin.getId(), alarm.getId(), Constants.NOTIFICATION_CHANNEL_ID, R.drawable.ic_nav_bottom_coin, textTitle,
                textContent, ContextCompat.getColor(context, R.color.notification_color), alarm.getAlarmColor().getColor(context), bitmap, coin.getId());
    }


}