package ve.com.abicelis.cryptomaster.ui.coindetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.data.model.Exchange;
import ve.com.abicelis.cryptomaster.util.StringUtil;

/**
 * Created by abicelis on 26/7/2018.
 */
public class ExchangeViewHolder extends RecyclerView.ViewHolder {


    //DATA
    private Exchange mCurrent;

    //UI
    @BindView(R.id.list_item_exchange_number)
    TextView mNumber;
    @BindView(R.id.list_item_exchange_name)
    TextView mName;
    @BindView(R.id.list_item_exchange_pair)
    TextView mPair;
    @BindView(R.id.list_item_exchange_price)
    TextView mPrice;
    @BindView(R.id.list_item_exchange_vol)
    TextView mVolume;

    public ExchangeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void setData(Exchange exchange) {
        mCurrent = exchange;

        mNumber.setText(String.valueOf(mCurrent.getNumber()));
        mName.setText(mCurrent.getName());
        mPair.setText(String.format(Locale.getDefault(), "%1$s/%2$s", mCurrent.getCodeFrom(), mCurrent.getCodeTo()));

        String tinyPrice = "";
        double tinyPriceComparator = (1.0/Math.pow(10, Constants.COIN_DETAIL_ACTIVITY_EXCHANGE_PRICE_MAX_DECIMALS));
        double price =  mCurrent.getPrice();

        if (price < tinyPriceComparator) {
            tinyPrice = "<";
            price = tinyPriceComparator;
        }
        //String format = "%1$s %2$."+ Constants.COIN_DETAIL_ACTIVITY_EXCHANGE_PRICE_MAX_DECIMALS+"f";
        String format = "%1$s %2$f";
        mPrice.setText(String.format(Locale.getDefault(), format, tinyPrice, price));
        mVolume.setText(StringUtil.withSuffix(mCurrent.getVolume()));
    }


}
