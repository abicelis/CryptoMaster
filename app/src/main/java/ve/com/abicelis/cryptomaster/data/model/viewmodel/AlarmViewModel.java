package ve.com.abicelis.cryptomaster.data.model.viewmodel;

import ve.com.abicelis.cryptomaster.data.model.Alarm;

/**
 * Created by abicelis on 1/10/2018.
 */
public class AlarmViewModel {

    private Alarm alarm;
    private boolean selected;

    public AlarmViewModel(Alarm alarm, boolean selected) {
        this.alarm = alarm;
        this.selected = selected;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
