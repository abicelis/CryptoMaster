package ve.com.abicelis.cryptomaster.ui.market;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.model.ChartTimeSpan;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.DominanceChartData;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapAndVolumeChartData;
import ve.com.abicelis.cryptomaster.ui.base.BaseFragment;
import ve.com.abicelis.cryptomaster.util.AttrUtil;
import ve.com.abicelis.cryptomaster.util.SnackbarUtil;

/**
 * Created by abicelis on 30/5/2018.
 */
public class MarketFragment extends BaseFragment implements MarketMvpView, View.OnClickListener {

    private Context mContext;

    @Inject
    MarketPresenter mMarketPresenter;

    @BindView(R.id.fragment_market_container)
    CoordinatorLayout mContainer;

    @BindView(R.id.fragment_market_total_chart_loading_container)
    FrameLayout mMarketCapChartLoadingContainer;
    @BindView(R.id.fragment_market_total_chart_loading)
    TextView mMarketCapChartLoading;
    @BindView(R.id.fragment_market_total_chart_loading_progressbar)
    ProgressBar mMarketCapChartLoadingProgressBar;

    @BindView(R.id.fragment_market_total_chart_error)
    TextView mMarketCapChartError;
    @BindView(R.id.fragment_market_total_chart)
    LineChart mMarketCapChart;
    @BindView(R.id.fragment_market_total_last)
    TextView mMarketCapLast;
    @BindView(R.id.fragment_market_total_24h)
    TextView mMarketButton24h;
    @BindView(R.id.fragment_market_total_7d)
    TextView mMarketButton7d;
    @BindView(R.id.fragment_market_total_1m)
    TextView mMarketButton1m;
    @BindView(R.id.fragment_market_total_3m)
    TextView mMarketButton3m;
    @BindView(R.id.fragment_market_total_1y)
    TextView mMarketButton1y;
    @BindView(R.id.fragment_market_total_all)
    TextView mMarketButtonAll;

    @BindView(R.id.fragment_market_dominance_chart_loading_container)
    FrameLayout mDominanceChartLoadingContainer;
    @BindView(R.id.fragment_market_dominance_chart_loading)
    TextView mDominanceChartLoading;
    @BindView(R.id.fragment_market_dominance_chart_loading_progressbar)
    ProgressBar mDominanceChartLoadingProgressBar;


    @BindView(R.id.fragment_market_dominance_chart_error)
    TextView mDominanceChartError;
    @BindView(R.id.fragment_market_dominance_chart)
    LineChart mDominanceChart;
    @BindView(R.id.fragment_market_dominance_24h)
    TextView mDominanceButton24h;
    @BindView(R.id.fragment_market_dominance_7d)
    TextView mDominanceButton7d;
    @BindView(R.id.fragment_market_dominance_1m)
    TextView mDominanceButton1m;
    @BindView(R.id.fragment_market_dominance_3m)
    TextView mDominanceButton3m;
    @BindView(R.id.fragment_market_dominance_1y)
    TextView mDominanceButton1y;
    @BindView(R.id.fragment_market_dominance_all)
    TextView mDominanceButtonAll;

    public MarketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPresenterComponent().inject(this);
        mMarketPresenter.attachView(this);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_market, container, false);
        ButterKnife.bind(this, rootView);

        mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimeSpan._24H);
        mMarketPresenter.getDominanceGraphData(ChartTimeSpan._3M);

        mMarketCapChart.setNoDataText("");
        mDominanceChart.setNoDataText("");

        mMarketButton24h.setOnClickListener(this);
        mMarketButton7d.setOnClickListener(this);
        mMarketButton1m.setOnClickListener(this);
        mMarketButton3m.setOnClickListener(this);
        mMarketButton1y.setOnClickListener(this);
        mMarketButtonAll.setOnClickListener(this);

        mDominanceButton24h.setOnClickListener(this);
        mDominanceButton7d.setOnClickListener(this);
        mDominanceButton1m.setOnClickListener(this);
        mDominanceButton3m.setOnClickListener(this);
        mDominanceButton1y.setOnClickListener(this);
        mDominanceButtonAll.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMarketPresenter.detachView();
    }

    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        SnackbarUtil.showSnackbar(mContainer, message.getMessageType(), message.getFriendlyNameRes(), SnackbarUtil.SnackbarDuration.SHORT, callback);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fragment_market_total_24h:
                mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimeSpan._24H);
                break;
            case R.id.fragment_market_total_7d:
                mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimeSpan._7D);
                break;
            case R.id.fragment_market_total_1m:
                mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimeSpan._1M);
                break;
            case R.id.fragment_market_total_3m:
                mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimeSpan._3M);
                break;
            case R.id.fragment_market_total_1y:
                mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimeSpan._1Y);
                break;
            case R.id.fragment_market_total_all:
                mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimeSpan.ALL);
                break;

            case R.id.fragment_market_dominance_24h:
                mMarketPresenter.getDominanceGraphData(ChartTimeSpan._24H);
                break;
            case R.id.fragment_market_dominance_7d:
                mMarketPresenter.getDominanceGraphData(ChartTimeSpan._7D);
                break;
            case R.id.fragment_market_dominance_1m:
                mMarketPresenter.getDominanceGraphData(ChartTimeSpan._1M);
                break;
            case R.id.fragment_market_dominance_3m:
                mMarketPresenter.getDominanceGraphData(ChartTimeSpan._3M);
                break;
            case R.id.fragment_market_dominance_1y:
                mMarketPresenter.getDominanceGraphData(ChartTimeSpan._1Y);
                break;
            case R.id.fragment_market_dominance_all:
                mMarketPresenter.getDominanceGraphData(ChartTimeSpan.ALL);
                break;
        }
    }


    /* MARKET CAP GRAPH */

    @Override
    public void marketCapAndVolumeShowGraph(MarketCapAndVolumeChartData data) {

        //Axis customization
        int color = AttrUtil.getAttributeColor(mContext, R.attr.chart_grid_line);


        YAxis yAxisL = mMarketCapChart.getAxisLeft();
        yAxisL.setEnabled(false);
        yAxisL.setSpaceTop(200);

        YAxis yAxisR = mMarketCapChart.getAxisRight();
        //yAxisR.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        //yAxisR.setYOffset(-3f);
        yAxisR.setSpaceBottom(20);
        yAxisR.setTextColor(color);
        yAxisR.setGridColor(color);
        yAxisR.setLabelCount(4, true);
        yAxisR.setDrawAxisLine(false);
        yAxisR.setValueFormatter(new YAxisMcapFormatter());

        XAxis xAxis = mMarketCapChart.getXAxis();
        //xAxis.setTextSize(10f);
        xAxis.setTextColor(color);
        xAxis.setGridColor(color);
        xAxis.setLabelCount(5, true);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new XAxisFormatter(data.getTimestamps(), data.getChartTimeSpan()));


        //Data set and customization
        LineDataSet dataSetMcap = new LineDataSet(data.getMarketCapEntries(), "Mcap"); // add entries to dataset
        dataSetMcap.setColor(AttrUtil.getAttributeColor(mContext, R.attr.chart_line));
        dataSetMcap.setLineWidth(2f);
        dataSetMcap.setAxisDependency(YAxis.AxisDependency.RIGHT);
        dataSetMcap.setDrawCircles(false);
        //dataSetMcap.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        //dataSetMcap.setHighlightEnabled(false);
        //dataSetMcap.setHighLightColor(AttrUtil.getAttributeColor(mContext, R.attr.chart_highlight));

        LineDataSet dataSetVolume = new LineDataSet(data.getVolumeEntries(), "Volume"); // add entries to dataset
        dataSetVolume.setColor(AttrUtil.getAttributeColor(mContext, R.attr.chart_line_2));
        //dataSetVolume.setHighlightEnabled(false);
        dataSetVolume.setDrawFilled(true);
        dataSetVolume.setFillColor(AttrUtil.getAttributeColor(mContext, R.attr.chart_line_2));
        //dataSetVolume.setLineWidth(1f);
        dataSetVolume.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSetVolume.setDrawCircles(false);


        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetMcap);
        dataSets.add(dataSetVolume);

        //Chart customization
        LineData lineData = new LineData(dataSets);
        mMarketCapChart.setData(lineData);
        mMarketCapChart.setTouchEnabled(false);
        //mMarketCapChart.setDragEnabled(false);
        //mMarketCapChart.setPinchZoom(false);
        //mMarketCapChart.setDoubleTapToZoomEnabled(false);
        //mMarketCapChart.setScaleEnabled(false);
        mMarketCapChart.getDescription().setEnabled(false);
        mMarketCapChart.getLegend().setEnabled(false);
        mMarketCapChart.invalidate(); // refresh
    }
    @Override
    public void marketCapSetLast(String text) {
        mMarketCapLast.setText(text);
    }
    @Override
    public void marketCapActivateButton(ChartTimeSpan chartTimeSpan) {
        int activeColor = AttrUtil.getAttributeColor(mContext, R.attr.chart_button_active);
        int inactiveColor = AttrUtil.getAttributeColor(mContext, R.attr.chart_button_inactive);

        mMarketButton24h.setTextColor(inactiveColor);
        mMarketButton7d.setTextColor(inactiveColor);
        mMarketButton1m.setTextColor(inactiveColor);
        mMarketButton3m.setTextColor(inactiveColor);
        mMarketButton1y.setTextColor(inactiveColor);
        mMarketButtonAll.setTextColor(inactiveColor);

        switch (chartTimeSpan) {
            case _24H:
                mMarketButton24h.setTextColor(activeColor);
                break;
            case _7D:
                mMarketButton7d.setTextColor(activeColor);
                break;
            case _1M:
                mMarketButton1m.setTextColor(activeColor);
                break;
            case _3M:
                mMarketButton3m.setTextColor(activeColor);
                break;
            case _1Y:
                mMarketButton1y.setTextColor(activeColor);
                break;
            case ALL:
                mMarketButtonAll.setTextColor(activeColor);
                break;
        }
    }
    @Override
    public void marketCapShowLoading() {
        mMarketCapChartLoadingContainer.animate().setInterpolator(new LinearInterpolator()).alpha(1.0f).start();
        //mMarketCapChartLoading.animate().setInterpolator(new LinearInterpolator()).alpha(1.0f).start();
        //mMarketCapChartLoadingProgressBar.animate().setInterpolator(new LinearInterpolator()).alpha(1.0f).start();

        mMarketCapChartError.animate().setInterpolator(new LinearInterpolator()).alpha(0.0f).start();
    }
    @Override
    public void marketCapHideLoading() {
        mMarketCapChartLoadingContainer.animate().setInterpolator(new LinearInterpolator()).alpha(0.0f).start();
        //mMarketCapChartLoading.animate().setInterpolator(new LinearInterpolator()).alpha(0.0f).start();
        //mMarketCapChartLoadingProgressBar.animate().setInterpolator(new LinearInterpolator()).alpha(0.0f).start();

    }

    @Override
    public void marketCapShowError() {
        mMarketCapChartError.animate().setInterpolator(new LinearInterpolator()).alpha(1.0f).start();
    }


    /* DOMINANCE GRAPH */

    @Override
    public void dominanceShowGraph(DominanceChartData data) {

        //Axis customization
        int color = AttrUtil.getAttributeColor(mContext, R.attr.chart_grid_line);


        YAxis yAxisL = mDominanceChart.getAxisLeft();
        yAxisL.setEnabled(false);
        //yAxisL.setSpaceTop(200);

        YAxis yAxisR = mDominanceChart.getAxisRight();
        //yAxisR.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        //yAxisR.setYOffset(-3f);
        //yAxisR.setSpaceBottom(20);
        yAxisR.setTextColor(color);
        yAxisR.setGridColor(color);
        yAxisR.setLabelCount(4, true);
        yAxisR.setDrawAxisLine(false);
        yAxisR.setValueFormatter(new YAxisDominanceFormatter());

        XAxis xAxis = mDominanceChart.getXAxis();
        //xAxis.setTextSize(10f);
        xAxis.setTextColor(color);
        xAxis.setGridColor(color);
        xAxis.setLabelCount(5, true);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new XAxisFormatter(data.getTimestamps(), data.getChartTimeSpan()));


        //Data set and customization
        LineDataSet dataSetMostDominant = new LineDataSet(data.getMostDominantCoinEntries(), "MostDominant");
        dataSetMostDominant.setColor(AttrUtil.getAttributeColor(mContext, R.attr.chart_line));
        dataSetMostDominant.setLineWidth(2f);
        dataSetMostDominant.setDrawCircles(false);
        dataSetMostDominant.setFillFormatter(new MyFillFormatter(dataSetMostDominant));
        dataSetMostDominant.setDrawFilled(true);

        LineDataSet dataSetLessDominant = new LineDataSet(data.getLessDominantCoinEntries(), "LessDominant");
        dataSetLessDominant.setColor(AttrUtil.getAttributeColor(mContext, R.attr.chart_grid_line));
        dataSetLessDominant.setLineWidth(2f);
        dataSetLessDominant.setDrawCircles(false);
        dataSetLessDominant.setFillFormatter(new MyFillFormatter(dataSetMostDominant));
        dataSetLessDominant.setDrawFilled(true);

        LineDataSet dataSetLeastDominant = new LineDataSet(data.getLessDominantCoinEntries(), "LeastDominant");
        dataSetLeastDominant.setColor(AttrUtil.getAttributeColor(mContext, R.attr.chart_button_active));
        dataSetLeastDominant.setLineWidth(2f);
        dataSetLeastDominant.setDrawCircles(false);
        dataSetLeastDominant.setFillFormatter(new MyFillFormatter(dataSetLessDominant));
        dataSetLeastDominant.setDrawFilled(true);

        LineDataSet dataSetOthers = new LineDataSet(data.getOtherCoinsEntries(), "Others");
        dataSetOthers.setColor(AttrUtil.getAttributeColor(mContext, R.attr.chart_button_inactive));
        dataSetOthers.setLineWidth(2f);
        dataSetOthers.setDrawCircles(false);
        dataSetOthers.setFillFormatter(new MyFillFormatter(dataSetLeastDominant));
        dataSetOthers.setDrawFilled(true);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSetMostDominant);
        dataSets.add(dataSetLessDominant);
        dataSets.add(dataSetOthers);

        //Chart customization
        LineData lineData = new LineData(dataSets);
        mDominanceChart.setData(lineData);
        mDominanceChart.setTouchEnabled(false);
        mDominanceChart.getDescription().setEnabled(false);
        //mDominanceChart.getLegend().setEnabled(false);
        mDominanceChart.setRenderer(new StackedLineChartRenderer(mDominanceChart, mDominanceChart.getAnimator(), mDominanceChart.getViewPortHandler()));
        mDominanceChart.invalidate(); // refresh
    }

    @Override
    public void dominanceActivateButton(ChartTimeSpan chartTimeSpan) {
        int activeColor = AttrUtil.getAttributeColor(mContext, R.attr.chart_button_active);
        int inactiveColor = AttrUtil.getAttributeColor(mContext, R.attr.chart_button_inactive);

        mDominanceButton24h.setTextColor(inactiveColor);
        mDominanceButton7d.setTextColor(inactiveColor);
        mDominanceButton1m.setTextColor(inactiveColor);
        mDominanceButton3m.setTextColor(inactiveColor);
        mDominanceButton1y.setTextColor(inactiveColor);
        mDominanceButtonAll.setTextColor(inactiveColor);

        switch (chartTimeSpan) {
            case _24H:
                mDominanceButton24h.setTextColor(activeColor);
                break;
            case _7D:
                mDominanceButton7d.setTextColor(activeColor);
                break;
            case _1M:
                mDominanceButton1m.setTextColor(activeColor);
                break;
            case _3M:
                mDominanceButton3m.setTextColor(activeColor);
                break;
            case _1Y:
                mDominanceButton1y.setTextColor(activeColor);
                break;
            case ALL:
                mDominanceButtonAll.setTextColor(activeColor);
                break;
        }
    }

    @Override
    public void dominanceShowLoading() {
        mDominanceChartLoadingContainer.animate().setInterpolator(new LinearInterpolator()).alpha(1.0f).start();
        //mDominanceChartLoading.animate().setInterpolator(new LinearInterpolator()).alpha(1.0f).start();

        mDominanceChartError.animate().setInterpolator(new LinearInterpolator()).alpha(0.0f).start();
    }

    @Override
    public void dominanceHideLoading() {
        mDominanceChartLoadingContainer.animate().setInterpolator(new LinearInterpolator()).alpha(0.0f).start();
        //mDominanceChartLoading.animate().setInterpolator(new LinearInterpolator()).alpha(0.0f).start();
    }

    @Override
    public void dominanceShowError() {
        mDominanceChartError.animate().setInterpolator(new LinearInterpolator()).alpha(1.0f).start();
    }



    class YAxisDominanceFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return (int)value + "%";
        }
    }

    class YAxisMcapFormatter implements IAxisValueFormatter {

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
