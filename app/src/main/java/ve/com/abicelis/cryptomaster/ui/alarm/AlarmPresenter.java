package ve.com.abicelis.cryptomaster.ui.alarm;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.Alarm;
import ve.com.abicelis.cryptomaster.data.model.viewmodel.AlarmViewModel;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;
import ve.com.abicelis.cryptomaster.util.RxJavaUtil;

/**
 * Created by abicelis on 2/9/2018.
 */
public class AlarmPresenter extends BasePresenter<AlarmMvpView> {

    DataManager mDataManager;

    private ActionMode mActionMode;
    private boolean multiSelect = false;
    private ArrayList<AlarmViewModel> selectedItems = new ArrayList<>();

    public AlarmPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    public void start() {
        getMvpView().showFab();
        addDisposable(mDataManager.getAlarmsSortedByEnabled()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( alarms -> {
                    ArrayList<AlarmViewModel> alarmViewModels = new ArrayList<>();
                    for (Alarm alarm: alarms)
                        alarmViewModels.add(new AlarmViewModel(alarm, false));

                    getMvpView().showAlarms(alarmViewModels);
                }, throwable -> {
                    Timber.e(throwable, "Error getting alarms");
                    getMvpView().showMessage(Message.COULD_NOT_FETCH_ALARM_DATA, null);
                }));
    }

    public void alarmEnabledOrDisabled(AlarmViewModel alarmViewModel, boolean enabled) {
        if(enabled) {
            addDisposable(mDataManager.enableAlarm(alarmViewModel.getAlarm().getId())
                    .compose(RxJavaUtil.applySchedulersAndroidMainThread())
                    .subscribe( () -> getMvpView().showAlarmMessage(enabled, alarmViewModel.getAlarm().getFromCurrency().getCode(), alarmViewModel.getAlarm().getToCoinCode())
                            ,throwable -> {
                                start();
                                getMvpView().showMessage(Message.COULD_NOT_UPDATE_ALARM, null);
                                Timber.e(throwable, "Error enabling alarm. AlarmID= %d", alarmViewModel.getAlarm().getId());
                            }));

        } else {
            addDisposable(mDataManager.disableAlarm(alarmViewModel.getAlarm().getId())
                    .compose(RxJavaUtil.applySchedulersAndroidMainThread())
                    .subscribe( () -> getMvpView().showAlarmMessage(enabled, alarmViewModel.getAlarm().getFromCurrency().getCode(), alarmViewModel.getAlarm().getToCoinCode())
                            ,throwable -> {
                                start();
                                getMvpView().showMessage(Message.COULD_NOT_UPDATE_ALARM, null);
                                Timber.e(throwable, "Error enabling alarm. AlarmID= %d", alarmViewModel.getAlarm().getId());
                            }));
        }
    }

    public void alarmItemClicked(AlarmViewModel alarmViewModel, int position) {
        if (multiSelect) {
            handleMultiSelect(alarmViewModel, position);
        } else {
            getMvpView().editAlarm(alarmViewModel);
        }
    }


    public void alarmItemLongClicked(AlarmViewModel alarmViewModel, int position) {
        if (multiSelect) {
            handleMultiSelect(alarmViewModel, position);
        } else {

            ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    multiSelect = true;
                    mode.getMenuInflater().inflate (R.menu.menu_alarm_contextual, menu);
                    //menu.add("DELETE");
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    ArrayList<Alarm> alarms = new ArrayList<>();
                    for (AlarmViewModel alarmViewModel : selectedItems)
                        alarms.add(alarmViewModel.getAlarm());

                    addDisposable(mDataManager.deleteAlarms(alarms)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe( () -> {
                                mode.finish();
                                getMvpView().removeAlarms(selectedItems);

                            }, throwable -> {
                                Timber.e(throwable, "Error deleting items");
                            }));
                    return true;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    multiSelect = false;
                    selectedItems.clear();
                    getMvpView().clearActionModeSelectedItems();
                    if (getMvpView().fragmentInFocus())
                        getMvpView().showFab();
                    start();
                }
            };

           mActionMode = getMvpView().enableSupportActionMode(actionModeCallbacks);
           getMvpView().hideFab();
        }

    }


    private void handleMultiSelect(AlarmViewModel alarmViewModel, int position) {
        if (selectedItems.contains(alarmViewModel)) {
            selectedItems.remove(alarmViewModel);
            getMvpView().updateAlarm(position, false);

            if (selectedItems.size() == 0)
                mActionMode.finish();
        } else {
            selectedItems.add(alarmViewModel);
            getMvpView().updateAlarm(position, true);

        }

        mActionMode.setTitle(String.valueOf(selectedItems.size()));
        mActionMode.invalidate();
    }

    public void onLostFocus() {
        if (mActionMode != null)
            mActionMode.finish();
    }
}
