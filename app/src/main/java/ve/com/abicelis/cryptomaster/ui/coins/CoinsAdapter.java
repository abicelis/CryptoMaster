package ve.com.abicelis.cryptomaster.ui.coins;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.CoinsFragmentType;

/**
 * Created by abicelis on 17/9/2017.
 */

public class CoinsAdapter extends RecyclerView.Adapter<CoinViewHolder> {

    //DATA
    private List<Coin> mItems = new ArrayList<>();
    CoinsFragmentType mCoinsFragmentType;
    //private FlightClickedListener mListener;
    private LayoutInflater mInflater;
    private Activity mActivity;


    public CoinsAdapter(Activity activity, CoinsFragmentType coinsFragmentType) {
        mCoinsFragmentType = coinsFragmentType;
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
    }


    @Override
    public CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CoinViewHolder(mInflater.inflate(R.layout.list_item_coin , parent, false));
    }

    @Override
    public void onBindViewHolder(CoinViewHolder holder, int position) {
        Coin current = mItems.get(position);
        holder.setData(this, mActivity, current, mCoinsFragmentType);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public List<Coin> getItems() {
        return mItems;
    }

//
//    public void triggerFlightClicked(Flight flight) {
//        if (mListener != null)
//            mListener.onFlightClicked(flight);
//    }
//    public void triggerFlightLongClicked(Flight flight) {
//        if (mListener != null)
//            mListener.onFlightLongClicked(flight);
//    }
//
//    public void setFlightClickedListener(FlightClickedListener listener) {
//        mListener = listener;
//    }
//
//    public interface FlightClickedListener {
//        void onFlightClicked(Flight flight);
//        void onFlightLongClicked(Flight flight);
//    }
}
