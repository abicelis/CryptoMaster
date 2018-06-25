package ve.com.abicelis.cryptomaster.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import ve.com.abicelis.cryptomaster.data.model.Coin;

/**
 * Created by abicelis on 23/6/2018.
 */
@Dao
public interface CoinDao {


    @Query("SELECT count(*) FROM coin")
    Single<Integer> count();

    @Query("SELECT * FROM coin")
    Single<List<Coin>> getAll();

    @Query("SELECT * FROM coin where coin_id = :coinId")
    Maybe<Coin> getById(long coinId);

    @Query("SELECT * FROM coin where coin_id IN (:coinIds)")
    Maybe<List<Coin>> getByIds(long[] coinIds);

    @Query("SELECT * FROM coin where symbol = :symbol")
    Maybe<Coin> getBySymbol(String symbol);

    @Query("SELECT * FROM coin where symbol IN (:symbols)")
    Maybe<List<Coin>> getBySymbols(String symbols);

    @Query("SELECT * FROM coin where name = :name")
    Maybe<Coin> getByName(String name);

    @Query("SELECT * FROM coin where name IN (:names)")
    Maybe<List<Coin>> getByNames(String names);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)     //Skip warning, mismatch with calculated column relevance
    @Query("SELECT *" +
            ",(name LIKE :query) +" +
            " (CASE WHEN symbol LIKE :query THEN 2 ELSE 0 END)" +
            " AS relevance" +
            " FROM coin" +
            " WHERE name LIKE :query OR symbol LIKE :query" +
            " ORDER BY [relevance] desc")
    Maybe<List<Coin>> find(String query);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)     //Skip warning, mismatch with calculated column relevance
    @Query("SELECT *" +
            ",(name LIKE :query) +" +
            " (CASE WHEN symbol LIKE :query THEN 2 ELSE 0 END)" +
            " AS relevance" +
            " FROM coin" +
            " WHERE name LIKE :query OR symbol LIKE :query" +
            " ORDER BY [relevance] desc" +
            " LIMIT :limit")
    Maybe<List<Coin>> find(String query, int limit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<Coin> coins);

    @Update
    int update(Coin coin);

    @Delete
    void delete(Coin coin);

    @Query("DELETE FROM coin")
    int deleteAll();

}
