package ve.com.abicelis.cryptomaster.ui.new_alarm;

import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.model.AlarmColor;

/**
 * Created by abicelis on 20/9/2018.
 */
public class AlarmColorViewHolder extends RecyclerView.ViewHolder {

    //DATA
    AlarmColor mCurrent;

    @BindView(R.id.list_item_alarm_color_circle)
    View circleView;


    public AlarmColorViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(AlarmColor alarmColor) {
        mCurrent = alarmColor;
        int color = mCurrent.getColor(circleView.getContext());
        circleView.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    public void setListeners(AlarmColorPickerDialogFragment.ColorPickedListener listener) {
        circleView.setOnClickListener(v -> {
            listener.onColorPicked(mCurrent);
        } );
    }
}
