package ve.com.abicelis.cryptomaster.ui.alarm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.model.Alarm;
import ve.com.abicelis.cryptomaster.data.model.Exchange;
import ve.com.abicelis.cryptomaster.ui.base.BaseFragment;
import ve.com.abicelis.cryptomaster.ui.coindetail.ExchangeAdapter;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;

/**
 * Created by abicelis on 2/9/2018.
 */
public class AlarmFragment extends BaseFragment implements AlarmMvpView, View.OnClickListener {


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
        mAdapter = new AlarmAdapter(getContext());
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fragment_alarms_fab:
                Toast.makeText(this.getContext(), "FAB pressed!", Toast.LENGTH_SHORT).show();
                mAlarmPresenter.buttonClicked();
                break;

        }
    }


    @Override
    public void showAlarms(List<Alarm> alarmList) {
        mAdapter.getItems().clear();
        mAdapter.getItems().addAll(alarmList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        SnackbarUtil.showSnackbar(mContainer, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
    }


}
