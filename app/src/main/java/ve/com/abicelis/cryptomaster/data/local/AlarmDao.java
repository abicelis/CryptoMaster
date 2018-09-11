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
import ve.com.abicelis.cryptomaster.data.model.Alarm;
import ve.com.abicelis.cryptomaster.data.model.Coin;

/**
 * Created by abicelis on 23/6/2018.
 */
@Dao
public abstract class AlarmDao {


    @Query("SELECT count(*) FROM alarm")
    public abstract Single<Integer> count();

    @Query("SELECT * FROM alarm")
    public abstract Single<List<Alarm>> getAll();

    @Query("SELECT * FROM alarm where alarm_id = :alarmId")
    public abstract Maybe<Alarm> getById(long alarmId);

    @Query("SELECT * FROM alarm where alarm_id IN (:alarmIds)")
    public abstract Maybe<List<Alarm>> getByIds(long[] alarmIds);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] insert(List<Alarm> alarms);

    @Update
    public abstract int update(Alarm alarm);

    @Delete
    public abstract void delete(Alarm alarm);

    @Query("DELETE FROM alarm")
    public abstract int deleteAll();


    @Transaction
    public void deleteAlarmsAndInsertNewOnes(List<Alarm> alarms) {
        deleteAll();
        insert(alarms);
    }

}
