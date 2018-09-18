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
import ve.com.abicelis.cryptomaster.data.model.CachedCoin;
import ve.com.abicelis.cryptomaster.data.model.Coin;

/**
 * Created by abicelis on 23/6/2018.
 */
@Dao
public abstract class CachedCoinDao {


    @Query("SELECT count(*) FROM cached_coin")
    public abstract Single<Integer> count();

    @Query("SELECT * FROM cached_coin")
    public abstract Single<List<CachedCoin>> getAll();

    @Query("SELECT * FROM cached_coin where cached_coin_id = :cachedCoinId")
    public abstract Maybe<CachedCoin> getById(long cachedCoinId);

    @Query("SELECT * FROM cached_coin where cached_coin_id IN (:cachedCoinIds)")
    public abstract Maybe<List<CachedCoin>> getByIds(long[] cachedCoinIds);

    @Query("SELECT * FROM cached_coin where code = :code")
    public abstract Maybe<CachedCoin> getByCode(String code);

    @Query("SELECT * FROM cached_coin where code IN (:codes)")
    public abstract Maybe<List<CachedCoin>> getByCodes(String codes);

    @Query("SELECT * FROM cached_coin where name = :name")
    public abstract Maybe<CachedCoin> getByName(String name);

    @Query("SELECT * FROM cached_coin where name IN (:names)")
    public abstract Maybe<List<CachedCoin>> getByNames(String names);

    @Query("SELECT * FROM cached_coin ORDER BY rank ASC LIMIT :limit")
    public abstract Single<List<CachedCoin>> getByRank(int limit);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)     //Skip warning, mismatch with calculated column relevance
    @Query("SELECT *" +
            ",(name LIKE :query) +" +
            " (website_slug LIKE :query) +" +
            " (CASE WHEN code LIKE :query THEN 2 ELSE 0 END)" +
            " AS relevance" +
            " FROM cached_coin" +
            " WHERE name LIKE :query OR code LIKE :query OR website_slug LIKE :query" +
            " ORDER BY [relevance] desc")
    public abstract Single<List<CachedCoin>> find(String query);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)     //Skip warning, mismatch with calculated column relevance
    @Query("SELECT *" +
            ",(name LIKE :query) +" +
            " (website_slug LIKE :query) +" +
            " (CASE WHEN code LIKE :query THEN 2 ELSE 0 END)" +
            " AS relevance" +
            " FROM cached_coin" +
            " WHERE name LIKE :query OR code LIKE :query OR website_slug LIKE :query" +
            " ORDER BY [relevance] desc" +
            " LIMIT :limit")
    public abstract Single<List<CachedCoin>> find(String query, int limit);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] insert(List<CachedCoin> coins);

    @Query("DELETE FROM cached_coin")
    public abstract int deleteAll();

    @Transaction
    public void deleteCachedCoinsAndInsertNewOnes(List<CachedCoin> coins) {
        deleteAll();
        insert(coins);
    }


}
