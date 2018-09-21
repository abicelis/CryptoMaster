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

    @ColumnInfo(name ="from_currency_code")
    private String fromCurrencyCode;

    @ColumnInfo(name = "to_currency_code")
    private String toCurrencyCode;

    @ColumnInfo(name = "trigger_value")
    private double triggerValue;

    @ColumnInfo(name = "alarm_type")
    private AlarmType alarmType;

    @ColumnInfo(name = "alarm_color")
    private AlarmColor alarmColor;

    @ColumnInfo(name = "note")
    private String note;

    public Alarm (Long id, String fromCurrencyCode, String toCurrencyCode, double triggerValue, AlarmType alarmType, AlarmColor alarmColor, String note) {
        this(fromCurrencyCode, toCurrencyCode, triggerValue, alarmType, alarmColor, note);
        this.id = id;
    }

    @Ignore
    public Alarm (String fromCurrencyCode, String toCurrencyCode, double triggerValue, AlarmType alarmType, AlarmColor alarmColor, String note) {
        this.fromCurrencyCode = fromCurrencyCode;
        this.toCurrencyCode = toCurrencyCode;
        this.triggerValue = triggerValue;
        this.alarmType = alarmType;
        this.alarmColor = alarmColor;
        this.note = note;
    }


    public Long getId() {
        return id;
    }
    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }
    public String getToCurrencyCode() {
        return toCurrencyCode;
    }
    public double getTriggerValue() {
        return triggerValue;
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
    public void setFromCurrencyCode(String fromCurrency) {
        this.fromCurrencyCode = fromCurrency;
    }
    public void setToCurrencyCode(String toCurrency) {
        this.toCurrencyCode = toCurrency;
    }
    public void setTriggerValue(double triggerValue) {
        this.triggerValue = triggerValue;
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
