package ve.com.abicelis.cryptomaster.injection.presenter;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.ui.home.HomePresenter;

/**
 * Created by abicelis on 25/5/2018.
 */

@Module
public class PresenterModule {

    private final Activity mActivity;

    public PresenterModule(Activity activity) { mActivity = activity; }

//    @Provides
//    Context context() { return mActivity; }

    @Provides
    Activity activity() { return mActivity; }



    /* Presenters */
    @Provides
    HomePresenter homePresenter(DataManager dataManager) {
        return new HomePresenter(dataManager);
    }
//
//    @Provides
//    HomePresenter homePresenter(DataManager dataManager) {
//        return new HomePresenter(dataManager);
//    }
//
//    @Provides
//    ChangeImagePresenter changeImagePresenter(DataManager dataManager) {return new ChangeImagePresenter(dataManager); }
//
//    @Provides
//    TripDetailPresenter tripDetailPresenter(DataManager dataManager) {return new TripDetailPresenter(dataManager); }
//
//    @Provides
//    FlightPresenter flightPresenter(DataManager dataManager) {return new FlightPresenter(dataManager); }
//
//    @Provides
//    AirportAirlineSearchPresenter airportAirlineSearchPresenter(DataManager dataManager) {return new AirportAirlineSearchPresenter(dataManager); }
//
//    @Provides
//    TrackerPresenter trackerPresenter(DataManager dataManager) {return new TrackerPresenter(dataManager); }
}
