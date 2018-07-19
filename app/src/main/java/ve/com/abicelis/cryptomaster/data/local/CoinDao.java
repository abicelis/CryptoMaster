package ve.com.abicelis.cryptomaster.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import ve.com.abicelis.cryptomaster.data.model.Coin;

/**
 * Created by abicelis on 23/6/2018.
 */
@Dao
public abstract class CoinDao {


    @Query("SELECT count(*) FROM coin")
    public abstract Single<Integer> count();

    @Query("SELECT * FROM coin")
    public abstract Single<List<Coin>> getAll();

    @Query("SELECT * FROM coin where coin_id = :coinId")
    public abstract Maybe<Coin> getById(long coinId);

    @Query("SELECT * FROM coin where coin_id IN (:coinIds)")
    public abstract Maybe<List<Coin>> getByIds(long[] coinIds);

    @Query("SELECT * FROM coin where symbol = :symbol")
    public abstract Maybe<Coin> getBySymbol(String symbol);

    @Query("SELECT * FROM coin where symbol IN (:symbols)")
    public abstract Maybe<List<Coin>> getBySymbols(String symbols);

    @Query("SELECT * FROM coin where name = :name")
    public abstract Maybe<Coin> getByName(String name);

    @Query("SELECT * FROM coin where name IN (:names)")
    public abstract Maybe<List<Coin>> getByNames(String names);


    @Query("SELECT * FROM coin ORDER BY quote_usd_market_cap DESC LIMIT :limit")
    public abstract Single<List<Coin>> getTopByMarketCap(int limit);

    @Query("SELECT * FROM coin ORDER BY quote_usd_market_cap DESC")
    public abstract Single<List<Coin>> getAllByMarketCap();


    //@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH) //favorite_coin_id not used...
    @Query("SELECT last_updated FROM coin ORDER BY last_updated ASC LIMIT 1")
    public abstract Single<Long> getOldestLastUpdated();


    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)     //Skip warning, mismatch with calculated column relevance
    @Query("SELECT *" +
            ",(name LIKE :query) +" +
            " (CASE WHEN symbol LIKE :query THEN 2 ELSE 0 END)" +
            " AS relevance" +
            " FROM coin" +
            " WHERE name LIKE :query OR symbol LIKE :query" +
            " ORDER BY [relevance] desc")
    public abstract Maybe<List<Coin>> find(String query);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)     //Skip warning, mismatch with calculated column relevance
    @Query("SELECT *" +
            ",(name LIKE :query) +" +
            " (CASE WHEN symbol LIKE :query THEN 2 ELSE 0 END)" +
            " AS relevance" +
            " FROM coin" +
            " WHERE name LIKE :query OR symbol LIKE :query" +
            " ORDER BY [relevance] desc" +
            " LIMIT :limit")
    public abstract Maybe<List<Coin>> find(String query, int limit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] insert(List<Coin> coins);

    @Update
    public abstract int update(Coin coin);

    @Delete
    public abstract void delete(Coin coin);

    @Query("DELETE FROM coin")
    public abstract int deleteAll();


    @Transaction
    public void deleteCoinsAndInsertNewOnes(List<Coin> coins) {
        deleteAll();
        insert(coins);
    }

}
