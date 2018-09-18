package ve.com.abicelis.cryptomaster.ui.new_alarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.model.CachedCoin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.ui.base.BaseActivity;
import ve.com.abicelis.cryptomaster.ui.common.coinsearchview.CoinSearchView;
import ve.com.abicelis.cryptomaster.util.AttrUtil;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;
import ve.com.abicelis.cryptomaster.util.ViewUtil;

/**
 * Created by abicelis on 14/9/2018.
 */
public class NewAlarmActivity extends BaseActivity implements NewAlarmMvpView {

    @Inject
    NewAlarmPresenter mPresenter;

    @BindView(R.id.activity_new_alarm_nested_scroll_view)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.activity_new_alarm_base_title)
    TextView mBaseTitle;
    @BindView(R.id.activity_new_alarm_base_container)
    CardView mBaseContainer;
    @BindView(R.id.activity_new_alarm_base_btc)
    Button mBaseBtc;
    @BindView(R.id.activity_new_alarm_base_default_currency)
    Button mBaseDefCurr;

    @BindView(R.id.activity_new_alarm_pair_title)
    TextView mPairTitle;
    @BindView(R.id.activity_new_alarm_coin_search_view)
    CoinSearchView mCoinSearchView;

    @BindView(R.id.activity_new_alarm_price_container)
    CardView mPriceContainer;
    @BindView(R.id.activity_new_alarm_price_edittext)
    EditText mPriceEditText;
    @BindView(R.id.activity_new_alarm_price_below_button)
    Button mPriceBelowButton;
    @BindView(R.id.activity_new_alarm_price_below_diff)
    TextView mPriceBelowDiff;
    @BindView(R.id.activity_new_alarm_price_below_percent)
    TextView mPriceBelowPercent;
    @BindView(R.id.activity_new_alarm_price_above_button)
    Button mPriceAboveButton;
    @BindView(R.id.activity_new_alarm_price_above_diff)
    TextView mPriceAboveDiff;
    @BindView(R.id.activity_new_alarm_price_above_percent)
    TextView mPriceAbovePercent;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_alarm);
        ButterKnife.bind(this);

        getPresenterComponent().inject(this);
        mPresenter.attachView(this);

        mBaseBtc.setText(Currency.BTC.getCode());
        mBaseBtc.setOnClickListener(v -> {
            mPresenter.baseCurrencyBtcToggled();
        });

        mBaseDefCurr.setText(mPresenter.getDefaultCurrency().getCode());
        mBaseDefCurr.setOnClickListener(v -> {
            mPresenter.baseCurrencyDefCurrToggled();
        });
        mPresenter.baseCurrencyDefCurrToggled();    //Call it for starters

        mPairTitle.setOnClickListener(v -> {
            mCoinSearchView.hideSearch();
        });

        mCoinSearchView.setCoinSearchViewListener(new CoinSearchView.CoinSearchViewListener() {
            @Override
            public int getExpandedHeight() {
                int nsvHeight = mNestedScrollView.getMeasuredHeight();
                int baseTitleHeight = mBaseTitle.getMeasuredHeight();
                int baseContainerHeight = mBaseContainer.getMeasuredHeight();
                int pairTitleHeight = mPairTitle.getMeasuredHeight();
                return nsvHeight - baseTitleHeight - baseContainerHeight - pairTitleHeight;
            }

            @Override
            public void hideKeyboard() {
                ViewUtil.hideKeyboard(NewAlarmActivity.this);
            }

            @Override
            public void requestCachedCoins(String query) {
                mPresenter.requestCachedCoins(query);
            }


            @Override
            public void showMessage(Message message) {
                NewAlarmActivity.this.showMessage(message, null);
            }

            @Override
            public void getPairQuote(CachedCoin quoteCoin) {
                mPresenter.getPairQuote(quoteCoin);
            }
        });


        mPriceBelowButton.setOnClickListener(v-> {
            mPresenter.priceBelowButtonToggled();
        });
        mPriceAboveButton.setOnClickListener(v-> {
            mPresenter.priceAboveButtonToggled();
        });


        mPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.priceTextChanged(s.toString());
            }
        });


    }
    @Override
    public void displayCachedCoins(List<CachedCoin> cachedCoins) {
        mCoinSearchView.updateCachedCoins(cachedCoins);
    }

    @Override
    public void displayQuote(double quote) {
        mCoinSearchView.setPairQuote(quote);
    }


    @Override
    public void toggleBaseBtc() {
        mBaseBtc.setBackgroundColor(AttrUtil.getAttributeColor(this, R.attr.chart_line));
        mBaseBtc.setTextColor(AttrUtil.getAttributeColor(this, R.attr.text_primary));
        mBaseDefCurr.setBackgroundColor(AttrUtil.getAttributeColor(this, R.attr.button_not_checked));
        mBaseDefCurr.setTextColor(AttrUtil.getAttributeColor(this, R.attr.text_disabled));

        mCoinSearchView.setBaseCoin(Currency.BTC);
    }

    @Override
    public void toggleBaseDefaultCurrency(Currency defaultCurrency) {
        mBaseDefCurr.setBackgroundColor(AttrUtil.getAttributeColor(this, R.attr.chart_line_3));
        mBaseDefCurr.setTextColor(AttrUtil.getAttributeColor(this, R.attr.text_primary));
        mBaseBtc.setBackgroundColor(AttrUtil.getAttributeColor(this, R.attr.button_not_checked));
        mBaseBtc.setTextColor(AttrUtil.getAttributeColor(this, R.attr.text_disabled));

        mCoinSearchView.setBaseCoin(defaultCurrency);

    }

    @Override
    public void handlePriceBelowToggled() {
        mPriceBelowButton.setBackgroundColor(AttrUtil.getAttributeColor(this, R.attr.text_negative));
        mPriceBelowButton.setTextColor(AttrUtil.getAttributeColor(this, R.attr.text_primary));
        mPriceAboveButton.setBackgroundColor(AttrUtil.getAttributeColor(this, R.attr.button_not_checked));
        mPriceAboveButton.setTextColor(AttrUtil.getAttributeColor(this, R.attr.text_disabled));

        mPriceEditText.setText("");
    }

    @Override
    public void handlePriceAboveToggled() {
        mPriceAboveButton.setBackgroundColor(AttrUtil.getAttributeColor(this, R.attr.text_positive));
        mPriceAboveButton.setTextColor(AttrUtil.getAttributeColor(this, R.attr.text_primary));
        mPriceBelowButton.setBackgroundColor(AttrUtil.getAttributeColor(this, R.attr.button_not_checked));
        mPriceBelowButton.setTextColor(AttrUtil.getAttributeColor(this, R.attr.text_disabled));

        mPriceEditText.setText("");
    }


    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        SnackbarUtil.showSnackbar(mNestedScrollView, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
    }
}
