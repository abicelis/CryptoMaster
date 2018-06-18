package ve.com.abicelis.cryptomaster.ui.coins;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.util.AttrUtil;
import ve.com.abicelis.cryptomaster.util.StringUtil;

/**
 * Created by abicelis on 16/6/2018.
 */

public class CoinViewHolder extends RecyclerView.ViewHolder {

    //DATA
    private CoinsAdapter mAdapter;
    private Activity mActivity;
    private Coin mCurrent;
    private int mPosition;

    //UI
    @BindView(R.id.list_item_coin_container)
    ConstraintLayout mContainer;
    @BindView(R.id.list_item_coin_name)
    TextView mName;
    @BindView(R.id.list_item_coin_symbol)
    TextView mSymbol;
    @BindView(R.id.list_item_coin_icon)
    ImageView mIcon;

    @BindView(R.id.list_item_coin_price)
    TextView mPrice;
    @BindView(R.id.list_item_coin_mcap)
    TextView mMcap;
    @BindView(R.id.list_item_coin_vol)
    TextView mVolume;

    @BindView(R.id.list_item_coin_1h)
    TextView mPercent1h;
    @BindView(R.id.list_item_coin_24h)
    TextView mPercent24h;
    @BindView(R.id.list_item_coin_7d)
    TextView mPercent7d;



    public CoinViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(CoinsAdapter adapter, Activity activity, Coin current, int position) {
        mAdapter = adapter;
        mActivity = activity;
        mCurrent = current;
        mPosition = position;

        mName.setText(mCurrent.getName());
        mSymbol.setText(mCurrent.getSymbol());

        Glide.with(activity).load(String.format(Constants.COINMARKETCAP_ICONS_BASE_URL, mCurrent.getId())).into(mIcon);

        mPrice.setText(StringUtil.doubleMaxTwoDecimals(mCurrent.getPrice()));
        mMcap.setText(StringUtil.withSuffix(mCurrent.getMarketCap()));
        mVolume.setText(StringUtil.withSuffix(mCurrent.getVolume24h()));

        mPercent1h.setText(String.valueOf(mCurrent.getPercentChange1h()));
        mPercent24h.setText(String.valueOf(mCurrent.getPercentChange24h()));
        mPercent7d.setText(String.valueOf(mCurrent.getPercentChange7d()));


        if(mCurrent.getPercentChange1h() >= 0)
            mPercent1h.setTextColor(AttrUtil.getAttributeColor(mActivity, R.attr.text_positive));
        else
            mPercent1h.setTextColor(AttrUtil.getAttributeColor(mActivity, R.attr.text_negative));

        if(mCurrent.getPercentChange24h() >= 0)
            mPercent24h.setTextColor(AttrUtil.getAttributeColor(mActivity, R.attr.text_positive));
        else
            mPercent24h.setTextColor(AttrUtil.getAttributeColor(mActivity, R.attr.text_negative));

        if(mCurrent.getPercentChange7d() >= 0)
            mPercent7d.setTextColor(AttrUtil.getAttributeColor(mActivity, R.attr.text_positive));
        else
            mPercent7d.setTextColor(AttrUtil.getAttributeColor(mActivity, R.attr.text_negative));
    }

    public void setListeners() {

        //mContainer.setOnClickListener(this);
        //mContainer.setOnLongClickListener(this);
    }

//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        switch (id) {
//            case R.id.list_item_flight_container:
//                mAdapter.triggerFlightClicked(mCurrent);
//                break;
//        }
//    }
//
//    @Override
//    public boolean onLongClick(View v) {
//        int id = v.getId();
//        switch (id) {
//            case R.id.list_item_flight_container:
//                mAdapter.triggerFlightLongClicked(mCurrent);
//                return true;
//        }
//        return false;
//    }
}