package ve.com.abicelis.cryptomaster.application;

import android.app.Application;
import android.content.Context;
import android.support.annotation.UiThread;

import timber.log.Timber;
import ve.com.abicelis.cryptomaster.BuildConfig;
import ve.com.abicelis.cryptomaster.injection.application.ApplicationComponent;
import ve.com.abicelis.cryptomaster.injection.application.ApplicationModule;
import ve.com.abicelis.cryptomaster.injection.application.DaggerApplicationComponent;

/**
 * * Created by abicelis on 25/5/2018.
 */

public class CryptoMasterApplication extends Application {

    private ApplicationComponent mApplicationComponent;
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();

        getApplicationComponent().inject(this);
        mAppContext = this;


        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            //Fabric.with(this, new Crashlytics());
        }
    }

    //@UiThread
    public ApplicationComponent getApplicationComponent() {
        if(mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
