package ve.com.abicelis.cryptomaster.injection.application;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * * Created by abicelis on 25/5/2018.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) { mApplication = application; }

    @Provides
    @ApplicationScope
    Application application() { return mApplication; }

    @Provides
    @ApplicationScope
    Context context() { return mApplication.getApplicationContext(); }

}
