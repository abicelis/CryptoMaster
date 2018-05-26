package ve.com.abicelis.cryptomaster.ui.base;

import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;

import ve.com.abicelis.cryptomaster.application.CryptoMasterApplication;
import ve.com.abicelis.cryptomaster.injection.presenter.PresenterComponent;
import ve.com.abicelis.cryptomaster.injection.presenter.PresenterModule;

/**
 * Created by abicelis on 25/5/2018.
 */

public abstract class BaseFragment extends Fragment {

    @UiThread
    protected PresenterComponent getPresenterComponent() {
        return ((CryptoMasterApplication)getActivity().getApplication())
                .getApplicationComponent()
                .newPresenterComponent(new PresenterModule(getActivity()));
    }

}
