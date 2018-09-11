package ve.com.abicelis.cryptomaster.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by abicelis on 10/9/2018.
 */
@Entity(tableName = "alarm")
public class Alarm {

    @PrimaryKey
    @ColumnInfo(name = "alarm_id")
    private long id;

    @ColumnInfo(name = "coin_id")
    private long coinId;

    @ColumnInfo(name ="from_currency")
    private Currency fromCurrency;

    @ColumnInfo(name = "to_currency")
    private Currency toCurrency;

    @ColumnInfo(name = "exchange_type")
    private ExchangeType exchangeType;

    @ColumnInfo(name = "alarm_type")
    private AlarmType alarmType;

    @ColumnInfo(name = "alarm_color")
    private AlarmColor alarmColor;

    @ColumnInfo(name = "note")
    private String note;





}
