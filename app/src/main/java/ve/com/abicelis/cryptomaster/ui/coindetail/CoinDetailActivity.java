package ve.com.abicelis.cryptomaster.ui.coindetail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapPriceAndVolumeChartData;
import ve.com.abicelis.cryptomaster.ui.base.BaseActivity;
import ve.com.abicelis.cryptomaster.util.AttrUtil;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;
import ve.com.abicelis.cryptomaster.util.TextViewUtil;
import ve.com.abicelis.cryptomaster.util.ViewUtil;

/**
 * Created by abicelis on 17/7/2018.
 */
public class CoinDetailActivity extends BaseActivity implements CoinDetailMvpView, View.OnClickListener {

    @BindView(R.id.activity_coin_detail_container)
    CoordinatorLayout mContainer;
    @BindView(R.id.activity_coin_detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_coin_detail_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.activity_coin_detail_toolbar_subtitle)
    TextView mToolbarSubtitle;
//    @BindView(R.id.activity_coin_detail_toolbar_btc)
//    ImageView mToolbarBtc;
//    @BindView(R.id.activity_coin_detail_toolbar_default_currency)
//    ImageView mToolbarDefaultCurrency;




    //activity_coin_detail_basic.xml
    @BindView(R.id.activity_coin_detail_basic_logo)
    ImageView mLogo;

    @BindView(R.id.activity_coin_detail_basic_price_fiat)
    TextView mBasicPriceFiat;
    @BindView(R.id.activity_coin_detail_basic_price_crypto)
    TextView mBasicPriceCrypto;
    @BindView(R.id.activity_coin_detail_basic_rank)
    TextView mBasicRank;
    @BindView(R.id.activity_coin_detail_basic_market_cap)
    TextView mBasicMarketCap;
    @BindView(R.id.activity_coin_detail_basic_volume)
    TextView mBasicVolume;
    @BindView(R.id.activity_coin_detail_basic_circulating_supply)
    TextView mBasicCirculatingSupply;
    @BindView(R.id.activity_coin_detail_basic_total_supply)
    TextView mBasicTotalSupply;



    //activity_coin_detail_chart.xml
    @BindView(R.id.activity_coin_detail_chart_button_container)
    LinearLayout mChartButtonContainer;
    @BindView(R.id.activity_coin_detail_chart_button_btc)
    Button mChartButtonBtc;
    @BindView(R.id.activity_coin_detail_chart_button_default_currency)
    Button mChartButtonDefaultCurrency;
    @BindView(R.id.activity_coin_detail_chart_info_1)
    TextView mCoinDetailMainInfo1;
    @BindView(R.id.activity_coin_detail_chart_info_2)
    TextView mCoinDetailMainInfo2;
    @BindView(R.id.activity_coin_detail_chart)
    LineChart mCoinDetailChart;
    @BindView(R.id.activity_coin_detail_chart_loading_container)
    FrameLayout mCoinDetailChartLoadingContainer;
    @BindView(R.id.activity_coin_detail_chart_error)
    TextView mCoinDetailChartError;
    @BindView(R.id.activity_coin_detail_chart_24h)
    TextView mCoinDetailChartButton24h;
    @BindView(R.id.activity_coin_detail_chart_7d)
    TextView mCoinDetailChartButton7d;
    @BindView(R.id.activity_coin_detail_chart_1m)
    TextView mCoinDetailChartButton1m;
    @BindView(R.id.activity_coin_detail_chart_3m)
    TextView mCoinDetailChartButton3m;
    @BindView(R.id.activity_coin_detail_chart_1y)
    TextView mCoinDetailChartButton1y;
    @BindView(R.id.activity_coin_detail_chart_all)
    TextView mCoinDetailChartButtonAll;

    @Inject
    CoinDetailPresenter mCoinDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detail);
        ButterKnife.bind(this);
        getPresenterComponent().inject(this);
        mCoinDetailPresenter.attachView(this);

        long coinId = getIntent().getLongExtra(Constants.EXTRA_COIN_DETAIL_COIN_ID, -1);
        if (coinId != -1) {
            mCoinDetailPresenter.setCoinId(coinId);
        } else {
            Timber.i(Message.COIN_DETAIL_COIN_ID_MISSING.getFriendlyName(this));

            BaseTransientBottomBar.BaseCallback<Snackbar> callback = new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);
                    finish();
                }
            };
            showMessage(Message.COIN_FRAGMENT_TYPE_MISSING, callback);
        }

        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
        setSupportActionBar(mToolbar);

        toggleBtcButton();
        mCoinDetailPresenter.getBasicCoinData();


        mCoinDetailChart.setNoDataText("");

        mCoinDetailChartButton24h.setOnClickListener(this);
        mCoinDetailChartButton7d.setOnClickListener(this);
        mCoinDetailChartButton1m.setOnClickListener(this);
        mCoinDetailChartButton3m.setOnClickListener(this);
        mCoinDetailChartButton1y.setOnClickListener(this);
        mCoinDetailChartButtonAll.setOnClickListener(this);

        mChartButtonBtc.setOnClickListener(this);
        mChartButtonDefaultCurrency.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCoinDetailPresenter.detachView();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_activity_coin_detail, menu);
//        menu.findItem(R.id.menu_activity_coin_detail_btc).setIcon()
//
//        Glide.with(mIcon).load(String.format(Constants.COINMARKETCAP_ICONS_16_PX_BASE_URL, mCoinDetailPresenter.getCoin().getId())).into(mIcon);
//
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.activity_coin_detail_chart_24h:
                mCoinDetailPresenter.getMainChartData(ChartTimeSpan._24H);
                break;
            case R.id.activity_coin_detail_chart_7d:
                mCoinDetailPresenter.getMainChartData(ChartTimeSpan._7D);
                break;
            case R.id.activity_coin_detail_chart_1m:
                mCoinDetailPresenter.getMainChartData(ChartTimeSpan._1M);
                break;
            case R.id.activity_coin_detail_chart_3m:
                mCoinDetailPresenter.getMainChartData(ChartTimeSpan._3M);
                break;
            case R.id.activity_coin_detail_chart_1y:
                mCoinDetailPresenter.getMainChartData(ChartTimeSpan._1Y);
                break;
            case R.id.activity_coin_detail_chart_all:
                mCoinDetailPresenter.getMainChartData(ChartTimeSpan.ALL);
                break;

            case R.id.activity_coin_detail_chart_button_btc:
                mCoinDetailPresenter.onBtcClickedForChart();
                break;

            case R.id.activity_coin_detail_chart_button_default_currency:
                mCoinDetailPresenter.onDefaultCurrencyClickedForChart();
                break;
        }
    }

    @Override
    public void toggleBtcButton() {
        mChartButtonBtc.setBackgroundColor(AttrUtil.getAttributeColor(this, R.attr.chart_line));
        mChartButtonBtc.setTextColor(AttrUtil.getAttributeColor(this, R.attr.text_primary));
        mChartButtonDefaultCurrency.setBackgroundColor(AttrUtil.getAttributeColor(this, R.attr.button_not_checked));
        mChartButtonDefaultCurrency.setTextColor(AttrUtil.getAttributeColor(this, R.attr.text_disabled));
    }

    @Override
    public void toggleDefaultCurrencyButton() {
        mChartButtonDefaultCurrency.setBackgroundColor(AttrUtil.getAttributeColor(this, R.attr.chart_line_3));
        mChartButtonDefaultCurrency.setTextColor(AttrUtil.getAttributeColor(this, R.attr.text_primary));
        mChartButtonBtc.setBackgroundColor(AttrUtil.getAttributeColor(this, R.attr.button_not_checked));
        mChartButtonBtc.setTextColor(AttrUtil.getAttributeColor(this, R.attr.text_disabled));
    }

    /* CoinDetailMvpView implementation */

    @Override
    public void showBasicCoinData(Coin coin, Currency defaultCurrency) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());

        //Toolbar
        mToolbarTitle.setText(coin.getName());
        mToolbarSubtitle.setText(coin.getSymbol());

        //Request chart data
        mCoinDetailPresenter.getMainChartData(ChartTimeSpan._3M);


        //activity_coin_detail_basic.xml
        Glide.with(mLogo).load(String.format(Constants.COINMARKETCAP_ICONS_64_PX_BASE_URL, coin.getId())).into(mLogo);
        int integerPartStartAt = (defaultCurrency.hasSymbol() ? defaultCurrency.getSymbol().length() : 0);
        int integerPartLength = String.valueOf((int)coin.getQuoteDefaultPrice()).length() + integerPartStartAt + 1;
        String currencySymbol = (defaultCurrency.hasSymbol() ? defaultCurrency.getSymbol() : "");
        String currencyCode = (defaultCurrency.hasSymbol() ? "" : defaultCurrency.getCode());
        String format = (defaultCurrency.isCrypto() ? "%1$s%2$.8f %3$s" : "%1$s%2$f %3$s");
        String s = String.format(Locale.getDefault(), format, currencySymbol, coin.getQuoteDefaultPrice(), currencyCode);
        SpannableString ss1 = new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1.5f), integerPartStartAt, integerPartLength, 0);
        mBasicPriceFiat.setText(ss1);


        if(defaultCurrency.isBitcoin())
            mBasicPriceCrypto.setVisibility(View.GONE);
        else
            mBasicPriceCrypto.setText(String.format(Locale.getDefault(), "%1$s%2$.8f", Currency.BTC.getSymbol(), coin.getQuoteBtcPrice()));


        mBasicRank.setText(String.format(Locale.getDefault(), "#%s", formatter.format(coin.getRank())));
        if(defaultCurrency.hasSymbol()) {
            mBasicMarketCap.setText(String.format(Locale.getDefault(), "%1$s%2$s", defaultCurrency.getSymbol(), formatter.format(coin.getQuoteDefaultMarketCap())));
            mBasicVolume.setText(String.format(Locale.getDefault(), "%1$s%2$s", defaultCurrency.getSymbol(), formatter.format(coin.getQuoteDefaultVolume())));
        } else {
            mBasicMarketCap.setText(String.format(Locale.getDefault(), "%1$s %2$s", formatter.format(coin.getQuoteDefaultMarketCap()), defaultCurrency.getCode()));
            mBasicVolume.setText(String.format(Locale.getDefault(), "%1$s %2$s", formatter.format(coin.getQuoteDefaultVolume()), defaultCurrency.getCode()));
        }
        mBasicCirculatingSupply.setText(String.format(Locale.getDefault(), "%1$s %2$s", formatter.format(coin.getCirculatingSupply()), coin.getSymbol()));
        mBasicTotalSupply.setText(String.format(Locale.getDefault(), "%1$s %2$s", formatter.format(coin.getTotalSupply()), coin.getSymbol()));


        //activity_coin_detail_chart.xml
        if(defaultCurrency == Currency.BTC) {
            mChartButtonContainer.setVisibility(View.GONE);
        } else {
            mChartButtonBtc.setText(Currency.BTC.getCode());
            mChartButtonDefaultCurrency.setText(defaultCurrency.getCode());
        }
    }

    @Override
    public void showChart(MarketCapPriceAndVolumeChartData data, boolean showBtcChart, Currency defaultCurrency) {

        showChartInfo(data, showBtcChart, defaultCurrency);

        int btcLineColor = AttrUtil.getAttributeColor(this, R.attr.chart_line);
        int defaultCurrencyLineColor = AttrUtil.getAttributeColor(this, R.attr.chart_line_3);
        int gridColor = AttrUtil.getAttributeColor(this, R.attr.chart_grid_line);

        //LEFT Y AXIS
        YAxis yAxisL = mCoinDetailChart.getAxisLeft();
        yAxisL.setEnabled(false);

        //RIGHT Y AXIS
        YAxis yAxisR = mCoinDetailChart.getAxisRight();
        yAxisR.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yAxisR.setYOffset(-6f);
        yAxisR.setTextSize(12f);
        yAxisR.setGridColor(gridColor);
        yAxisR.setDrawGridLines(true);
        yAxisR.setLabelCount(4, true);
        yAxisR.setDrawAxisLine(true);

        if(showBtcChart) {
            yAxisR.setTextColor(btcLineColor);
            yAxisR.setValueFormatter(new YAxisFormatter(Currency.BTC));
        } else {
            yAxisR.setTextColor(defaultCurrencyLineColor);
            yAxisR.setValueFormatter(new YAxisFormatter(defaultCurrency));
        }


        //X AXIS
        XAxis xAxis = mCoinDetailChart.getXAxis();
        //xAxis.setTextSize(10f);
        xAxis.setTextColor(gridColor);
        xAxis.setGridColor(gridColor);
        xAxis.setLabelCount(4, true);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new CoinDetailActivity.XAxisFormatter(data.getTimestamps(), data.getChartTimeSpan()));


        //Data set and customization
        LineDataSet dataSet;
        if(showBtcChart) {
            dataSet = new LineDataSet(data.getPriceDefaultCurrencyEntries(), Currency.BTC.getCode());
            dataSet.setColor(btcLineColor);
        } else {
            dataSet = new LineDataSet(data.getPriceUsdEntries(), defaultCurrency.getCode());
            dataSet.setColor(defaultCurrencyLineColor);
        }
        dataSet.setLineWidth(2f);
        dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);


        //Chart customization
        LineData lineData = new LineData(dataSets);
        mCoinDetailChart.setData(lineData);
        mCoinDetailChart.setTouchEnabled(false);
        mCoinDetailChart.getDescription().setEnabled(false);
        mCoinDetailChart.getLegend().setEnabled(false);
        mCoinDetailChart.animateX(Constants.MISC_CHART_ANIMATION_DURATION, Constants.MISC_CHART_ANIMATION_EASING);
    }

    private void showChartInfo(MarketCapPriceAndVolumeChartData data, boolean showBtcChart, Currency defaultCurrency) {
        char plusOrMinus;
        String currencySymbol = "";
        String currencyCode = "";
        double priceVariation;
        double percentageVariation;
        String timeSpan = data.getChartTimeSpan().getFriendlyName(this);
        String color;
        String colorNegative = AttrUtil.getAttributeColorHexString(this, R.attr.text_negative);
        String colorPositive = AttrUtil.getAttributeColorHexString(this, R.attr.text_positive);
        double priceMin;
        double priceMax;


        if(showBtcChart) {
            //INFO1
            plusOrMinus = (data.getPriceBtcVariation() >= 0 ? '+' : '-');
            currencySymbol = Currency.BTC.getSymbol();
            priceVariation = Math.abs(data.getPriceBtcVariation());
            percentageVariation = Math.abs(data.getPercentageBtcVariation());
            color = (data.getPriceBtcVariation() >= 0 ? colorPositive : colorNegative);
            //INFO2
            priceMin = data.getPriceBtcMin();
            priceMax = data.getPriceBtcMax();

        } else {
            //INFO1
            plusOrMinus = (data.getPriceDefaultCurrencyVariation() >= 0 ? '+' : '-');
            if(defaultCurrency.hasSymbol()) currencySymbol = defaultCurrency.getSymbol();
            else currencyCode = defaultCurrency.getCode();
            priceVariation = Math.abs(data.getPriceDefaultCurrencyVariation());
            percentageVariation = Math.abs(data.getPercentageDefaultCurrencyVariation());
            color = (data.getPriceDefaultCurrencyVariation() >= 0 ? colorPositive : colorNegative);
            //INFO2
            priceMin = data.getPriceDefaultCurrencyMin();
            priceMax = data.getPriceDefaultCurrencyMax();
        }

        mCoinDetailMainInfo1.setText(TextViewUtil.fromHtml(String.format(Locale.getDefault(), getString(R.string.activity_coin_detail_line1_format),
                plusOrMinus, currencySymbol, priceVariation, currencyCode, percentageVariation, timeSpan, color)));

        mCoinDetailMainInfo2.setText(TextViewUtil.fromHtml(String.format(Locale.getDefault(), getString(R.string.activity_coin_detail_line2_format),
                colorNegative, currencySymbol, priceMin, currencyCode, colorPositive, currencySymbol, priceMax, currencyCode )));
    }


    @Override
    public void mainChartShowLoading() {
        mCoinDetailChartLoadingContainer.animate().setInterpolator(new LinearInterpolator()).alpha(1.0f).start();
        mCoinDetailChartError.animate().setInterpolator(new LinearInterpolator()).alpha(0.0f).start();
    }

    @Override
    public void mainChartShowError() {
        mCoinDetailChartError.animate().setInterpolator(new LinearInterpolator()).alpha(1.0f).start();
    }

    @Override
    public void mainChartHideLoading() {
        mCoinDetailChartLoadingContainer.animate().setInterpolator(new LinearInterpolator()).alpha(0.0f).start();

    }

    @Override
    public void chartActivateButton(ChartTimeSpan chartTimeSpan) {
        int activeColor = AttrUtil.getAttributeColor(this, R.attr.chart_button_active);
        int inactiveColor = AttrUtil.getAttributeColor(this, R.attr.chart_button_inactive);

        mCoinDetailChartButton24h.setTextColor(inactiveColor);
        mCoinDetailChartButton7d.setTextColor(inactiveColor);
        mCoinDetailChartButton1m.setTextColor(inactiveColor);
        mCoinDetailChartButton3m.setTextColor(inactiveColor);
        mCoinDetailChartButton1y.setTextColor(inactiveColor);
        mCoinDetailChartButtonAll.setTextColor(inactiveColor);

        switch (chartTimeSpan) {
            case _24H:
                mCoinDetailChartButton24h.setTextColor(activeColor);
                break;
            case _7D:
                mCoinDetailChartButton7d.setTextColor(activeColor);
                break;
            case _1M:
                mCoinDetailChartButton1m.setTextColor(activeColor);
                break;
            case _3M:
                mCoinDetailChartButton3m.setTextColor(activeColor);
                break;
            case _1Y:
                mCoinDetailChartButton1y.setTextColor(activeColor);
                break;
            case ALL:
                mCoinDetailChartButtonAll.setTextColor(activeColor);
                break;
        }
    }

    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        SnackbarUtil.showSnackbar(mContainer, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
    }



    class YAxisFormatter implements IAxisValueFormatter {

        private Currency currency;

        public YAxisFormatter(Currency currency) {
            this.currency = currency;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            String currencySymbol = (currency.hasSymbol() ? currency.getSymbol() : "");
            String currencyCode = (currency.hasSymbol() ? "" : currency.getCode());

            String format = (currency.isCrypto() ? "%1$s%2$.8f %3$s" : "%1$s%2$f %3$s");
            return String.format(Locale.getDefault(), format, currencySymbol, value, currencyCode);
        }
    }

    class XAxisFormatter implements IAxisValueFormatter {

        private List<Long> timestamps;
        private ChartTimeSpan chartTimeSpan;

        public XAxisFormatter(List<Long> timestamps, ChartTimeSpan chartTimeSpan) {
            this.timestamps = timestamps;
            this.chartTimeSpan = chartTimeSpan;
        }


        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            long timestamp = timestamps.get((int)value);
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis(timestamp);
            SimpleDateFormat dateFormat;

            switch (chartTimeSpan) {
                case _24H:
                    dateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
                    break;

                default:
                case _7D:
                case _1M:
                case _3M:
                case _1Y:
                    dateFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
                    break;

                case ALL:
                    dateFormat = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
                    break;

            }

            dateFormat.setCalendar(cal);
            return dateFormat.format(cal.getTime());
        }
    }


}
