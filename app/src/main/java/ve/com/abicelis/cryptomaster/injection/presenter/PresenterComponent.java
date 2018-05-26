package ve.com.abicelis.cryptomaster.injection.presenter;

import dagger.Subcomponent;
import ve.com.abicelis.cryptomaster.ui.home.HomeActivity;

/**
 * Created by abicelis on 25/5/2018.
 */

@Subcomponent(
        modules = {
                PresenterModule.class,
        }
)
public interface PresenterComponent {
    void inject(HomeActivity target);
}
