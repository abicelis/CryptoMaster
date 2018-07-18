package ve.com.abicelis.cryptomaster.ui.coindetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

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
import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.ui.base.BaseActivity;
import ve.com.abicelis.cryptomaster.util.AttrUtil;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;

/**
 * Created by abicelis on 17/7/2018.
 */
public class CoinDetailActivity extends BaseActivity implements CoinDetailMvpView, View.OnClickListener {

    @BindView(R.id.activity_coin_detail_container)
    CoordinatorLayout mContainer;
    @BindView(R.id.fragment_market_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_coin_detail_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.activity_coin_detail_toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.activity_coin_detail_main_container)
    LinearLayout mCoinDetailMainContainer;
    @BindView(R.id.activity_coin_detail_main_price_fiat_symbol)
    TextView mCoinDetailMainPriceFiatSymbol;
    @BindView(R.id.activity_coin_detail_main_price_fiat_integer_part)
    TextView mCoinDetailMainPriceFiatIntegerPart;
    @BindView(R.id.activity_coin_detail_main_price_fiat_fractional_part)
    TextView mCoinDetailMainPriceFiatFractionalPart;

    @BindView(R.id.activity_coin_detail_main_price_crypto)
    TextView mCoinDetailMainPriceCrypto;
    @BindView(R.id.activity_coin_detail_main_info_1)
    TextView mCoinDetailMainInfo1;
    @BindView(R.id.activity_coin_detail_main_info_2)
    TextView mCoinDetailMainInfo2;

    @BindView(R.id.activity_coin_detail_main_chart_loading_container)
    FrameLayout mCoinDetailChartLoadingContainer;
    @BindView(R.id.activity_coin_detail_main_chart_loading)
    TextView mCoinDetailChartLoading;
    @BindView(R.id.activity_coin_detail_main_chart_loading_progressbar)
    ProgressBar mCoinDetailChartLoadingProgressBar;

    @BindView(R.id.activity_coin_detail_main_chart_error)
    TextView mCoinDetailChartError;
    @BindView(R.id.activity_coin_detail_main_chart)
    LineChart mCoinDetailChart;
    @BindView(R.id.activity_coin_detail_main_24h)
    TextView mCoinDetailButton24h;
    @BindView(R.id.activity_coin_detail_main_7d)
    TextView mCoinDetailButton7d;
    @BindView(R.id.activity_coin_detail_main_1m)
    TextView mCoinDetailButton1m;
    @BindView(R.id.activity_coin_detail_main_3m)
    TextView mCoinDetailButton3m;
    @BindView(R.id.activity_coin_detail_main_1y)
    TextView mCoinDetailButton1y;
    @BindView(R.id.activity_coin_detail_main_all)
    TextView mCoinDetailButtonAll;

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

        mCoinDetailPresenter.getBasicCoinData();
       //mCoinDetailPresenter.getMainChartData(ChartTimeSpan._3M);
        mainChartShowLoading();

        mCoinDetailChart.setNoDataText("");

        mCoinDetailButton24h.setOnClickListener(this);
        mCoinDetailButton7d.setOnClickListener(this);
        mCoinDetailButton1m.setOnClickListener(this);
        mCoinDetailButton3m.setOnClickListener(this);
        mCoinDetailButton1y.setOnClickListener(this);
        mCoinDetailButtonAll.setOnClickListener(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCoinDetailPresenter.detachView();
    }

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
            case R.id.activity_coin_detail_main_24h:
                mCoinDetailPresenter.getMainChartData(ChartTimeSpan._24H);
                break;
            case R.id.activity_coin_detail_main_7d:
                mCoinDetailPresenter.getMainChartData(ChartTimeSpan._7D);
                break;
            case R.id.activity_coin_detail_main_1m:
                mCoinDetailPresenter.getMainChartData(ChartTimeSpan._1M);
                break;
            case R.id.activity_coin_detail_main_3m:
                mCoinDetailPresenter.getMainChartData(ChartTimeSpan._3M);
                break;
            case R.id.activity_coin_detail_main_1y:
                mCoinDetailPresenter.getMainChartData(ChartTimeSpan._1Y);
                break;
            case R.id.activity_coin_detail_main_all:
                mCoinDetailPresenter.getMainChartData(ChartTimeSpan.ALL);
                break;
        }
    }


    /* CoinDetailMvpView implementation */

    @Override
    public void showBasicCoinData(Coin coin) {
        mToolbarTitle.setText(coin.getName());
        mToolbarSubtitle.setText(coin.getSymbol());
        mCoinDetailMainPriceFiatSymbol.setText(Currency.valueOf(coin.getQuoteCurrencySymbol()).getSymbol());
        mCoinDetailMainPriceFiatIntegerPart.setText( String.valueOf((int) Math.floor(coin.getPrice())) + ".");
        mCoinDetailMainPriceFiatFractionalPart.setText( String.valueOf((int) Math.floor(coin.getPrice() * 100)) );
    }

    @Override
    public void showMainChartGraph() {

//        //Axis customization
//        int color = AttrUtil.getAttributeColor(this, R.attr.chart_grid_line);
//
//
//        YAxis yAxisL = mCoinDetailChart.getAxisLeft();
//        yAxisL.setEnabled(false);
//        yAxisL.setSpaceTop(200);
//
//        YAxis yAxisR = mCoinDetailChart.getAxisRight();
//        //yAxisR.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
//        //yAxisR.setYOffset(-3f);
//        //yAxisR.setSpaceBottom(20);
//        yAxisR.setTextColor(color);
//        yAxisR.setGridColor(color);
//        yAxisR.setLabelCount(4);
//        yAxisR.setDrawAxisLine(false);
//        yAxisR.setValueFormatter(new CoinDetailActivity.YAxisFormatter());
//        //yAxisR.setAxisMinimum(0f);
//
//        XAxis xAxis = mCoinDetailChart.getXAxis();
//        //xAxis.setTextSize(10f);
//        xAxis.setTextColor(color);
//        xAxis.setGridColor(color);
//        xAxis.setLabelCount(5, true);
//        xAxis.setAvoidFirstLastClipping(true);
//        xAxis.setDrawAxisLine(false);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1f);
//        xAxis.setValueFormatter(new CoinDetailActivity.XAxisFormatter(data.getTimestamps(), data.getChartTimeSpan()));
//
//
//        //Data set and customization
//        LineDataSet dataSetMcap = new LineDataSet(data.getMarketCapEntries(), "Mcap"); // add entries to dataset
//        dataSetMcap.setColor(AttrUtil.getAttributeColor(this, R.attr.chart_line));
//        dataSetMcap.setLineWidth(2f);
//        dataSetMcap.setAxisDependency(YAxis.AxisDependency.RIGHT);
//        dataSetMcap.setDrawCircles(false);
//        dataSetMcap.setDrawValues(false);
//        dataSetMcap.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
//        //dataSetMcap.setHighlightEnabled(false);
//        //dataSetMcap.setHighLightColor(AttrUtil.getAttributeColor(mContext, R.attr.chart_highlight));
//
//        LineDataSet dataSetVolume = new LineDataSet(data.getVolumeEntries(), "Volume"); // add entries to dataset
//        dataSetVolume.setColor(AttrUtil.getAttributeColor(this, R.attr.chart_line_2));
//        //dataSetVolume.setLineWidth(1f);
//        dataSetVolume.setAxisDependency(YAxis.AxisDependency.LEFT);
//        dataSetVolume.setDrawCircles(false);
//        dataSetVolume.setDrawValues(false);
//        dataSetMcap.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
//        dataSetVolume.setDrawFilled(true);
//        dataSetVolume.setFillColor(AttrUtil.getAttributeColor(this, R.attr.chart_line_2));
//        //dataSetVolume.setHighlightEnabled(false);
//
//
//        List<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(dataSetMcap);
//        dataSets.add(dataSetVolume);
//
//        //Chart customization
//        LineData lineData = new LineData(dataSets);
//        mCoinDetailChart.setData(lineData);
//        mCoinDetailChart.setTouchEnabled(false);
//        //mCoinDetailChart.setDragEnabled(false);
//        //mCoinDetailChart.setPinchZoom(false);
//        //mCoinDetailChart.setDoubleTapToZoomEnabled(false);
//        //mCoinDetailChart.setScaleEnabled(false);
//        mCoinDetailChart.getDescription().setEnabled(false);
//        mCoinDetailChart.getLegend().setEnabled(false);
//        mCoinDetailChart.invalidate(); // refresh
    }

    @Override
    public void mainChartSetInfo() {
        //TODO!
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
    public void mainChartActivateButton(ChartTimeSpan chartTimeSpan) {
        int activeColor = AttrUtil.getAttributeColor(this, R.attr.chart_button_active);
        int inactiveColor = AttrUtil.getAttributeColor(this, R.attr.chart_button_inactive);

        mCoinDetailButton24h.setTextColor(inactiveColor);
        mCoinDetailButton7d.setTextColor(inactiveColor);
        mCoinDetailButton1m.setTextColor(inactiveColor);
        mCoinDetailButton3m.setTextColor(inactiveColor);
        mCoinDetailButton1y.setTextColor(inactiveColor);
        mCoinDetailButtonAll.setTextColor(inactiveColor);

        switch (chartTimeSpan) {
            case _24H:
                mCoinDetailButton24h.setTextColor(activeColor);
                break;
            case _7D:
                mCoinDetailButton7d.setTextColor(activeColor);
                break;
            case _1M:
                mCoinDetailButton1m.setTextColor(activeColor);
                break;
            case _3M:
                mCoinDetailButton3m.setTextColor(activeColor);
                break;
            case _1Y:
                mCoinDetailButton1y.setTextColor(activeColor);
                break;
            case ALL:
                mCoinDetailButtonAll.setTextColor(activeColor);
                break;
        }
    }

    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        SnackbarUtil.showSnackbar(mContainer, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
    }



    class YAxisFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return "$" + (int)value + "B";
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
