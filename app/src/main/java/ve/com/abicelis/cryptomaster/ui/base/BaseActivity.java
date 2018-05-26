package ve.com.abicelis.cryptomaster.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;

import ve.com.abicelis.cryptomaster.application.CryptoMasterApplication;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
import ve.com.abicelis.cryptomaster.injection.presenter.PresenterComponent;
import ve.com.abicelis.cryptomaster.injection.presenter.PresenterModule;

/**
 * Created by abicelis on 25/5/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSavedAppTheme();
    }

    @UiThread
    protected PresenterComponent getPresenterComponent() {
        return ((CryptoMasterApplication)getApplication())
                .getApplicationComponent()
                .newPresenterComponent(new PresenterModule(this));
    }

    protected void setSavedAppTheme(){
        setTheme(new SharedPreferenceHelper().getAppThemeType().getTheme());
    }
}
