package ve.com.abicelis.cryptomaster.ui.alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.model.Alarm;
import ve.com.abicelis.cryptomaster.data.model.AlarmType;
import ve.com.abicelis.cryptomaster.data.model.Exchange;
import ve.com.abicelis.cryptomaster.ui.base.BaseFragment;
import ve.com.abicelis.cryptomaster.ui.coindetail.ExchangeAdapter;
import ve.com.abicelis.cryptomaster.ui.new_alarm.NewAlarmActivity;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;

/**
 * Created by abicelis on 2/9/2018.
 */
public class AlarmFragment extends BaseFragment implements AlarmMvpView, View.OnClickListener {

    private static final int REQUEST_CODE = 306;

    @Inject
    AlarmPresenter mAlarmPresenter;

    @BindView(R.id.fragment_alarms_container)
    CoordinatorLayout mContainer;
    @BindView(R.id.fragment_alarms_fab)
    FloatingActionButton mFab;

    @BindView(R.id.fragment_alarms_recycler)
    RecyclerView mRecycler;
    private LinearLayoutManager mLayoutManager;
    private AlarmAdapter mAdapter;


    public AlarmFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPresenterComponent().inject(this);
        mAlarmPresenter.attachView(this);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_alarms, container, false);
        ButterKnife.bind(this, rootView);

        mFab.setOnClickListener(this);
        setupRecycler();
        mAlarmPresenter.getAlarms();

        return rootView;
    }

    private void setupRecycler() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new AlarmAdapter(getContext(), new AlarmAdapter.AlarmListener() {
            @Override
            public void onAlarmEnabledOrDisabled(Alarm alarm, boolean enabled) {
                mAlarmPresenter.alarmEnabledOrDisabled(alarm, enabled);
            }

            @Override
            public void onAlarmClicked(Alarm alarm) {
                mAlarmPresenter.alarmClicked(alarm);
            }
        });

        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        //decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.item_decoration));
        mRecycler.addItemDecoration(decoration);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fragment_alarms_fab:
                Intent createNewAlarmIntent = new Intent(getContext(), NewAlarmActivity.class);
                startActivityForResult(createNewAlarmIntent, REQUEST_CODE);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE)
            if(resultCode == Activity.RESULT_OK)
                mAlarmPresenter.getAlarms();
    }



    @Override
    public void showAlarms(List<Alarm> alarmList) {
        mAdapter.getItems().clear();
        mAdapter.getItems().addAll(alarmList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void editAlarm(Alarm alarm) {
        Intent editAlarmIntent = new Intent(getContext(), NewAlarmActivity.class);
        editAlarmIntent.putExtra(Constants.NEW_ALARM_ACTIVITY_EXTRA_ALARM_ID, alarm.getId());
        startActivityForResult(editAlarmIntent, REQUEST_CODE);
    }


    @Override
    public void showAlarmMessage(boolean enabled, String fromCurrencyCode, String toCurrencyCode) {
        String aux = (enabled ? getString(R.string.activity_alarm_success_enabling_alarm) : getString(R.string.activity_alarm_success_disabling_alarm));
        String message = String.format(Locale.getDefault(), "%1$s/%2$s %3$s", fromCurrencyCode, toCurrencyCode, aux);

        SnackbarUtil.showSnackbar(mContainer, SnackbarUtil.SnackbarType.SUCCESS, message, SnackbarUtil.SnackbarDuration.SHORT, null);
    }

    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        SnackbarUtil.showSnackbar(mContainer, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
    }



}
