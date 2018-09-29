package ve.com.abicelis.cryptomaster.ui.alarm;

import android.content.res.ColorStateList;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.model.Alarm;

/**
 * Created by abicelis on 11/9/2018.
 */
public class AlarmViewHolder extends RecyclerView.ViewHolder {


    //DATA
    private Alarm mCurrent;

    //UI
    @BindView(R.id.list_item_alarm_container)
    ConstraintLayout mContainer;
    @BindView(R.id.list_item_alarm_pair)
    TextView mPair;
    @BindView(R.id.list_item_alarm_description)
    TextView mDescription;
    @BindView(R.id.list_item_alarm_color)
    View mColor;
    @BindView(R.id.list_item_alarm_switch)
    SwitchCompat mSwitch;

    public AlarmViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void setData(Alarm alarm) {
        mCurrent = alarm;

        mPair.setText(String.format("%1$s/%2$s", mCurrent.getFromCurrency().getCode(), mCurrent.getToCoinCode()));
        mSwitch.setChecked(mCurrent.isEnabled());

        mDescription.setText(
                String.format(Locale.getDefault(), "%1$s %2$.6f %3$s",
                        mCurrent.getAlarmType().getDescription(mDescription.getContext()),
                        mCurrent.getTriggerValue(),
                        mCurrent.getFromCurrency().getCode()));

        int color = mCurrent.getAlarmColor().getColor(mColor.getContext());
        mColor.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    public void setListeners(AlarmAdapter.AlarmListener listener) {

        mContainer.setOnClickListener((v) -> listener.onAlarmClicked(mCurrent));

        mSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listener.onAlarmEnabledOrDisabled(mCurrent, mSwitch.isChecked());
        });
    }


}
