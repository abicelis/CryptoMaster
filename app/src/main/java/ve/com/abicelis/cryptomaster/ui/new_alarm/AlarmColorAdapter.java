package ve.com.abicelis.cryptomaster.ui.new_alarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.model.Alarm;
import ve.com.abicelis.cryptomaster.data.model.AlarmColor;
import ve.com.abicelis.cryptomaster.ui.alarm.AlarmViewHolder;

/**
 * Created by abicelis on 26/7/2018.
 */
public class AlarmColorAdapter extends RecyclerView.Adapter<AlarmColorViewHolder> {

    //DATA
    List<AlarmColor> items;
    AlarmColorPickerDialogFragment.ColorPickedListener mListener;

    //UI
    private LayoutInflater mInflater;

    public AlarmColorAdapter(Context context, AlarmColorPickerDialogFragment.ColorPickedListener listener) {
        mInflater = LayoutInflater.from(context);
        items = new ArrayList<>();
        mListener = listener;
    }


    @NonNull
    @Override
    public AlarmColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlarmColorViewHolder(mInflater.inflate(R.layout.list_item_alarm_color , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmColorViewHolder holder, int position) {
        holder.setData(items.get(position));
        holder.setListeners(mListener);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public List<AlarmColor> getItems() {
        return items;
    }


}
