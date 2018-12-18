package ve.com.abicelis.cryptomaster.ui.alarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.model.Alarm;
import ve.com.abicelis.cryptomaster.data.model.viewmodel.AlarmViewModel;

/**
 * Created by abicelis on 26/7/2018.
 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmViewHolder> {

    //DATA
    List<AlarmViewModel> mItems;
    AlarmListener mListener;

    //UI
    private LayoutInflater mInflater;

    public AlarmAdapter(Context context, AlarmListener listener) {
        mInflater = LayoutInflater.from(context);
        mListener = listener;
        mItems = new ArrayList<>();
    }


    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlarmViewHolder(mInflater.inflate(R.layout.list_item_alarm , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.setData(mItems.get(position));
        holder.setListeners(mListener);
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public List<AlarmViewModel> getItems() {
        return mItems;
    }

    public void clearActionModeSelectedItems() {
        for (int i=0; i < getItemCount(); i ++)
            mItems.get(i).setSelected(false);
        notifyDataSetChanged();
    }


    interface AlarmListener {
        void onAlarmEnabledOrDisabled(AlarmViewModel alarm, boolean enabled);
        void onAlarmClicked(AlarmViewModel alarm, int position);
        void onAlarmLongClicked(AlarmViewModel alarm, int position);
    }


}
