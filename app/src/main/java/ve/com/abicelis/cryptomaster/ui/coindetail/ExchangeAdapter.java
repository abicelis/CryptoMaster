package ve.com.abicelis.cryptomaster.ui.coindetail;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.model.Exchange;

/**
 * Created by abicelis on 26/7/2018.
 */
public class ExchangeAdapter extends RecyclerView.Adapter<ExchangeViewHolder> {

    //DATA
    List<Exchange> items;

    //UI
    private LayoutInflater mInflater;

    public ExchangeAdapter(Activity activity) {
        mInflater = LayoutInflater.from(activity);
        items = new ArrayList<>();
    }


    @NonNull
    @Override
    public ExchangeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExchangeViewHolder(mInflater.inflate(R.layout.list_item_exchange , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeViewHolder holder, int position) {
        holder.setData(items.get(position));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public List<Exchange> getItems() {
        return items;
    }


}
