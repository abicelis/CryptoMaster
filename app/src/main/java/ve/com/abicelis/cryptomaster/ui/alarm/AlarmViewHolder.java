package ve.com.abicelis.cryptomaster.ui.alarm;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.model.viewmodel.AlarmViewModel;
import ve.com.abicelis.cryptomaster.util.AttrUtil;
import ve.com.abicelis.cryptomaster.util.DecimalFormatUtil;

/**
 * Created by abicelis on 11/9/2018.
 */
public class AlarmViewHolder extends RecyclerView.ViewHolder {


    //DATA
    private AlarmViewModel mCurrent;

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


    public void setData(AlarmViewModel alarm) {
        mCurrent = alarm;

        mPair.setText(String.format("%1$s/%2$s", mCurrent.getAlarm().getFromCurrency().getCode(), mCurrent.getAlarm().getToCoinCode()));
        mSwitch.setChecked(mCurrent.getAlarm().isEnabled());

        mDescription.setText(
                String.format(Locale.getDefault(), "%1$s %2$s %3$s",
                        mCurrent.getAlarm().getAlarmType().getDescription(mDescription.getContext()),
                        DecimalFormatUtil.formatDecimals(mCurrent.getAlarm().getTriggerValue()),
                        mCurrent.getAlarm().getFromCurrency().getCode()));

        int color = mCurrent.getAlarm().getAlarmColor().getColor(mColor.getContext());
        mColor.setBackgroundTintList(ColorStateList.valueOf(color));

        if (mCurrent.isSelected())
            mContainer.setBackgroundColor(AttrUtil.getAttributeColor(mContainer.getContext(), R.attr.default_item_selected));
        else
            mContainer.setBackgroundColor(Color.TRANSPARENT);

    }

    public void setListeners(AlarmAdapter.AlarmListener listener) {

        mContainer.setOnClickListener((v) -> listener.onAlarmClicked(mCurrent, getAdapterPosition()));
        mContainer.setOnLongClickListener((v) -> {
            listener.onAlarmLongClicked(mCurrent, getAdapterPosition());
            return false;
        });

        mSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listener.onAlarmEnabledOrDisabled(mCurrent, mSwitch.isChecked());
        });
    }


}
