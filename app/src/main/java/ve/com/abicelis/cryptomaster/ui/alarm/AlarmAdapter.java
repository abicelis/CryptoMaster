package ve.com.abicelis.cryptomaster.ui.alarm;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.model.Alarm;

/**
 * Created by abicelis on 26/7/2018.
 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmViewHolder> {

    //DATA
    List<Alarm> items;

    //UI
    private LayoutInflater mInflater;

    public AlarmAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        items = new ArrayList<>();
    }


    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlarmViewHolder(mInflater.inflate(R.layout.list_item_alarm , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.setData(items.get(position));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public List<Alarm> getItems() {
        return items;
    }


}
