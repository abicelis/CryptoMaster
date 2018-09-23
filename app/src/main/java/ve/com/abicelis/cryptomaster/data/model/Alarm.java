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

    private boolean enabled;

    @ColumnInfo(name ="from_currency")
    private Currency fromCurrency;

    @ColumnInfo(name = "to_coin_id")
    private int toCoinId;

    @ColumnInfo(name = "to_coin_code")
    private String toCoinCode;

    @ColumnInfo(name = "trigger_value")
    private double triggerValue;

    @ColumnInfo(name = "alarm_type")
    private AlarmType alarmType;

    @ColumnInfo(name = "alarm_color")
    private AlarmColor alarmColor;

    @ColumnInfo(name = "note")
    private String note;

    public Alarm (Long id, Currency fromCurrency, int toCoinId, String toCoinCode, double triggerValue, AlarmType alarmType, AlarmColor alarmColor, String note, boolean enabled) {
        this(fromCurrency, toCoinId, toCoinCode, triggerValue, alarmType, alarmColor, note, enabled);
        this.id = id;
    }

    @Ignore
    public Alarm (Currency fromCurrency, int toCoinId, String toCoinCode, double triggerValue, AlarmType alarmType, AlarmColor alarmColor, String note, boolean enabled) {
        this.fromCurrency = fromCurrency;
        this.toCoinId = toCoinId;
        this.toCoinCode = toCoinCode;
        this.triggerValue = triggerValue;
        this.alarmType = alarmType;
        this.alarmColor = alarmColor;
        this.note = note;
        this.enabled = enabled;
    }


    public Long getId() {
        return id;
    }
    public boolean isEnabled() { return enabled; }
    public Currency getFromCurrency() {
        return fromCurrency;
    }
    public int getToCoinId() {
        return toCoinId;
    }
    public String getToCoinCode() { return toCoinCode; }
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
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }
    public void setToCoinId(int toCurrency) {
        this.toCoinId = toCurrency;
    }
    public void setToCoinCode(String toCurrencyCode) { this.toCoinCode = toCurrencyCode; }
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


    //TODO uncomment this!!!!!
    public boolean checkIfShouldTrigger(double quote) {
        //if(AlarmType.ABOVE.equals(alarmType) && triggerValue > quote)
            return true;

        //if(AlarmType.BELOW.equals(alarmType) && triggerValue < quote)
        //    return true;

        //return false;
    }
}
