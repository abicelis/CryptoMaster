package ve.com.abicelis.cryptomaster.injection.presenter;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.ui.coins.CoinsPresenter;
import ve.com.abicelis.cryptomaster.ui.home.HomePresenter;
import ve.com.abicelis.cryptomaster.ui.market.MarketPresenter;

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

    @Provides
    MarketPresenter marketPresenter(DataManager dataManager) {
        return new MarketPresenter(dataManager);
    }

    @Provides
    CoinsPresenter coinsPresenter(DataManager dataManager) {
        return new CoinsPresenter(dataManager);
    }

}
