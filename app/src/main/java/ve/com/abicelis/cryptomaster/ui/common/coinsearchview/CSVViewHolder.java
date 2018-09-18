package ve.com.abicelis.cryptomaster.ui.common.coinsearchview;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.model.CachedCoin;

/**
 * Created by abicelis on 11/9/2018.
 */
public class CSVViewHolder extends RecyclerView.ViewHolder {


    //DATA
    private CachedCoin mCurrent;

    //UI
    @BindView(R.id.list_item_coin_search_view_coin_container)
    ConstraintLayout mContainer;
    @BindView(R.id.list_item_coin_search_view_coin_code)
    TextView mCode;
    @BindView(R.id.list_item_coin_search_view_coin_name)
    TextView mName;

    public CSVViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void setData(CachedCoin cachedCoin) {
        mCurrent = cachedCoin;

        mCode.setText(cachedCoin.getCode());
        mName.setText(cachedCoin.getName());
    }

    public void setListeners(final CSVAdapter.ItemClickedListener itemClickedListener) {

        mContainer.setOnClickListener((v) -> {
            itemClickedListener.itemClicked(mCurrent);
        });
    }




}
