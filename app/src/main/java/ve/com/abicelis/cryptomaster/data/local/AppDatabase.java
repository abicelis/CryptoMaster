package ve.com.abicelis.cryptomaster.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.FavoriteCoin;

/**
 * Created by abicelis on 23/6/2018.
 */

@Database(entities = {Coin.class, FavoriteCoin.class}, version = 1)
//@TypeConverters({CalendarConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CoinDao coinDao();
    public abstract FavoriteCoinDao favoriteCoinDao();
}
