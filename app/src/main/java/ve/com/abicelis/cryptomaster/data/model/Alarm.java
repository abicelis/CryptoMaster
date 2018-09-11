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

    public Alarm (long id, Currency fromCurrency, Currency toCurrency, ExchangeType exchangeType, AlarmType alarmType, AlarmColor alarmColor, String note) {
        this.id = id;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.exchangeType = exchangeType;
        this.alarmType = alarmType;
        this.alarmColor = alarmColor;
        this.note = note;
    }


    public long getId() {
        return id;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public ExchangeType getExchangeType() {
        return exchangeType;
    }

    public AlarmType getAlarmType() {
        return alarmType;
    }

    public AlarmColor getAlarmColor() {
        return alarmColor;
    }

    public String getNote() {
        return note;
    }
}
