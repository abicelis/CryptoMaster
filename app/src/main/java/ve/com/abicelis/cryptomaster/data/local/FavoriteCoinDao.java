package ve.com.abicelis.cryptomaster.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.FavoriteCoin;

/**
 * Created by abicelis on 23/6/2018.
 */
@Dao
public interface FavoriteCoinDao {


    @Query("SELECT count(*) FROM favorite_coin")
    Single<Integer> count();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH) //favorite_coin_id not used...
    @Query("SELECT * FROM favorite_coin JOIN coin ON favorite_coin_id=coin_id")
    Single<List<Coin>> getAll();


    @Query("SELECT COUNT(1) FROM favorite_coin WHERE favorite_coin_id = :coinId")
    Single<Integer> coinIsFavorite(long coinId);


    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH) //favorite_coin_id not used...
    @Query("SELECT last_updated FROM favorite_coin JOIN coin ON favorite_coin_id=coin_id ORDER BY last_updated ASC LIMIT 1")
    Single<Long> getOldestLastUpdated();



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insert(List<FavoriteCoin> coins);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(FavoriteCoin coin);

    @Update
    int update(FavoriteCoin favoriteCoin);

    @Delete
    int delete(FavoriteCoin favoriteCoin);

    @Query("DELETE FROM favorite_coin")
    int deleteAll();

}
