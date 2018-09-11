package ve.com.abicelis.cryptomaster.ui.alarm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.ui.base.BaseFragment;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;

/**
 * Created by abicelis on 2/9/2018.
 */
public class AlarmFragment extends BaseFragment implements AlarmMvpView, View.OnClickListener {


    @Inject
    AlarmPresenter mAlarmPresenter;

    @BindView(R.id.fragment_alarm_container)
    FrameLayout mContainer;
    @BindView(R.id.fragment_alarm_button)
    Button mButton;


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
        View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);
        ButterKnife.bind(this, rootView);

        mButton.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        SnackbarUtil.showSnackbar(mContainer, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fragment_alarm_button:
                Toast.makeText(this.getContext(), "Button pressed!", Toast.LENGTH_SHORT).show();
                mAlarmPresenter.buttonClicked();
                break;

        }
    }
}
