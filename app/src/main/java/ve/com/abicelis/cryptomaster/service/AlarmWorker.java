package ve.com.abicelis.cryptomaster.service;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import androidx.work.WorkFinishedCallback;
import androidx.work.Worker;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.application.CryptoMasterApplication;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.injection.presenter.PresenterModule;

/**
 * Created by abicelis on 5/8/2018.
 */
public class AlarmWorker extends Worker {

    @Inject
    DataManager dataManager;



    @NonNull
    @Override
    public Result doWork() {
        Context c = getApplicationContext();
        CryptoMasterApplication cma = ((CryptoMasterApplication) c);
        cma.getApplicationComponent().inject(this);

        Currency defCurr;
        try {
            defCurr = dataManager.getSharedPreferenceHelper().getDefaultCurrency();
            Timber.d("Def Curr is %s", defCurr.getFriendlyName());

        } catch (Exception e) {
            Timber.e(e, "Error getting defcurr");
            return Result.FAILURE;
        }


        //Access the API, get the ticker values of all the coins listed as alarms
        //Check whether any alarm should trigger
        //Send notifications if any alarm triggers
        //Maybe disable the alarm?

        return Result.SUCCESS;
    }




}