package ve.com.abicelis.cryptomaster.injection.application;

import dagger.Component;
import ve.com.abicelis.cryptomaster.application.CryptoMasterApplication;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.injection.presenter.PresenterComponent;
import ve.com.abicelis.cryptomaster.injection.presenter.PresenterModule;

/**
 * * Created by abicelis on 25/5/2018.
 */

@ApplicationScope
@Component(
        modules = {
                ApplicationModule.class,
                RemoteModule.class,
                LocalModule.class
        }
)
public interface ApplicationComponent {
    void inject(CryptoMasterApplication target);





    // Services injected by ApplicationComponent should be:
    // Global dagger services, which should be instantiated only once per app lifecycle
    // A service to be injected into Application object
    // Services required by more than one sub-component of ApplicationComponent
    DataManager dataManager();




    // An instance of a PresenterComponent can be instantiated from
    // this ApplicationComponent (Since PresenterComponent is an @SubComponent of ApplicationComponent)
    // while supplying the required PresenterModule.
    PresenterComponent newPresenterComponent(PresenterModule presenterModule);
}
