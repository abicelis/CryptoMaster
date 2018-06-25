package ve.com.abicelis.cryptomaster.injection.application;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.data.local.AppDatabase;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;

/**
 * Created by abicelis on 25/5/2018.
 */

@Module
public class LocalModule {

    private static final String ROOM_DATABASE_NAME = "ROOM_DATABASE_NAME";

    /**
     * INJECTION GRAPH FOR ROOM DATABASE
     */

    @Provides
    @Named(ROOM_DATABASE_NAME)
    String provideRoomDatabaseName() {return Constants.ROOM_DATABASE_NAME;}

    @Provides
    @ApplicationScope
    AppDatabase provideRoomAppDatabase(Application applicationContext, @Named(ROOM_DATABASE_NAME) String databaseName) {
        return Room.databaseBuilder(applicationContext,
                AppDatabase.class, databaseName).fallbackToDestructiveMigration().build();
        //TODO remember to kill fallbackToDestructiveMigration() and provide a proper migration plan on prod!
    }

    @Provides
    @ApplicationScope
    SharedPreferenceHelper provideSharedPreferenceHelper() {return new SharedPreferenceHelper(); }

}
