package ve.com.abicelis.cryptomaster.ui.alarm;

import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
    @BindView(R.id.list_item_alarm_pair)
    TextView mPair;
    @BindView(R.id.list_item_alarm_description)
    TextView mDescription;
    @BindView(R.id.list_item_alarm_color)
    View mColor;

    public AlarmViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void setData(Alarm alarm) {
        mCurrent = alarm;

        mPair.setText(String.format("%1$s/%2$s", mCurrent.getFromCurrency().getCode(), mCurrent.getToCurrency().getCode()));

        mDescription.setText(
                String.format("%1$s %2$s %3$s",
                        mCurrent.getAlarmType().getDescription(mDescription.getContext()),
                        mCurrent.getToCurrency().getSymbol(),
                        mCurrent.getTriggerValue()));

        int color = mCurrent.getAlarmColor().getColor(mColor.getContext());
        mColor.setBackgroundTintList(ColorStateList.valueOf(color));
    }


}
