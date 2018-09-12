package ve.com.abicelis.cryptomaster.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by abicelis on 10/9/2018.
 */
@Entity(tableName = "alarm")
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "alarm_id")
    private Long id;

    @ColumnInfo(name ="from_currency")
    private Currency fromCurrency;

    @ColumnInfo(name = "to_currency")
    private Currency toCurrency;

    @ColumnInfo(name = "trigger_value")
    private double triggerValue;

    @ColumnInfo(name = "exchange_type")
    private ExchangeType exchangeType;

    @ColumnInfo(name = "alarm_type")
    private AlarmType alarmType;

    @ColumnInfo(name = "alarm_color")
    private AlarmColor alarmColor;

    @ColumnInfo(name = "note")
    private String note;

    public Alarm (Long id, Currency fromCurrency, Currency toCurrency, double triggerValue, ExchangeType exchangeType, AlarmType alarmType, AlarmColor alarmColor, String note) {
        this(fromCurrency, toCurrency, triggerValue, exchangeType, alarmType, alarmColor, note);
        this.id = id;
    }

    @Ignore
    public Alarm (Currency fromCurrency, Currency toCurrency, double triggerValue, ExchangeType exchangeType, AlarmType alarmType, AlarmColor alarmColor, String note) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.triggerValue = triggerValue;
        this.exchangeType = exchangeType;
        this.alarmType = alarmType;
        this.alarmColor = alarmColor;
        this.note = note;
    }


    public Long getId() {
        return id;
    }
    public Currency getFromCurrency() {
        return fromCurrency;
    }
    public Currency getToCurrency() {
        return toCurrency;
    }
    public double getTriggerValue() {
        return triggerValue;
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

    public void setId(Long id) {
        this.id = id;
    }
    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }
    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }
    public void setTriggerValue(double triggerValue) {
        this.triggerValue = triggerValue;
    }
    public void setExchangeType(ExchangeType exchangeType) {
        this.exchangeType = exchangeType;
    }
    public void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }
    public void setAlarmColor(AlarmColor alarmColor) {
        this.alarmColor = alarmColor;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
