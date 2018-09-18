package ve.com.abicelis.cryptomaster.ui.common.coinsearchview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.model.CachedCoin;

/**
 * Created by abicelis on 26/7/2018.
 */
public class CSVAdapter extends RecyclerView.Adapter<CSVViewHolder> {

    //DATA
    List<CachedCoin> mItems;
    ItemClickedListener mItemClickedListener;

    //UI
    private LayoutInflater mInflater;

    public CSVAdapter(Context context, ItemClickedListener itemClickedListener) {
        mInflater = LayoutInflater.from(context);
        mItems = new ArrayList<>();
        mItemClickedListener = itemClickedListener;
    }


    @NonNull
    @Override
    public CSVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CSVViewHolder(mInflater.inflate(R.layout.list_item_coin_search_view_coin , parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CSVViewHolder holder, int position) {
        holder.setData(mItems.get(position));
        holder.setListeners(mItemClickedListener);
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public List<CachedCoin> getItems() {
        return mItems;
    }


    public interface ItemClickedListener {
        void itemClicked(CachedCoin coin);
    }

}
