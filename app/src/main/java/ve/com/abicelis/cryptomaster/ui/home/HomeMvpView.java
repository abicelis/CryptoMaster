package ve.com.abicelis.cryptomaster.ui.home;

import ve.com.abicelis.cryptomaster.data.model.StartFragment;
import ve.com.abicelis.cryptomaster.ui.base.MvpView;


/**
 * Created by abicelis on 4/9/2017.
 */

public interface HomeMvpView extends MvpView {
   void setupViewPager(StartFragment startFragment);
   void setupBottomNavigation(StartFragment startFragment);
}
