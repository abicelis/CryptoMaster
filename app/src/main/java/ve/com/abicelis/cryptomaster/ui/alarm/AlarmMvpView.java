package ve.com.abicelis.cryptomaster.ui.alarm;



import android.support.v7.view.ActionMode;

import java.util.ArrayList;
import java.util.List;

import ve.com.abicelis.cryptomaster.data.model.viewmodel.AlarmViewModel;
import ve.com.abicelis.cryptomaster.ui.base.MvpView;

/**
 * Created by abicelis on 2/9/2018.
 */
interface AlarmMvpView extends MvpView {
    void showAlarmMessage(boolean enabled, String fromCurrencyCode, String toCurrencyCode);
    void showAlarms(List<AlarmViewModel> alarmList);

    void editAlarm(AlarmViewModel alarm);

    boolean fragmentInFocus();
    void showFab();
    void hideFab();

    ActionMode enableSupportActionMode(ActionMode.Callback actionModeCallbacks);
    void updateAlarm(int position, boolean selected);
    void removeAlarms(ArrayList<AlarmViewModel> selectedItems);
    void clearActionModeSelectedItems();


}
