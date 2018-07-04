package ve.com.abicelis.cryptomaster.ui.market;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

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
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.model.ChartTimespan;
import ve.com.abicelis.cryptomaster.data.model.coinmarketcapgraph.MarketCapAndVolumeChartData;
import ve.com.abicelis.cryptomaster.ui.base.BaseFragment;
import ve.com.abicelis.cryptomaster.util.AttrUtil;
import ve.com.abicelis.cryptomaster.util.ViewUtil;

/**
 * Created by abicelis on 30/5/2018.
 */
public class MarketFragment extends BaseFragment implements MarketMvpView, View.OnClickListener {

    public static final String COLOR_OF_FRAGMENT = "COLOR_OF_FRAGMENT";
    private int color;
    private Context mContext;

    @Inject
    MarketPresenter mMarketPresenter;

    @BindView(R.id.fragment_market_container)
    CoordinatorLayout mContainer;
    @BindView(R.id.fragment_market_total_chart_loading)
    TextView mMarketCapChartLoading;
    @BindView(R.id.fragment_market_total_chart)
    LineChart mMarketCapChart;
    @BindView(R.id.fragment_market_total_last)
    TextView mMarketCapLast;
    @BindView(R.id.fragment_market_24h)
    TextView mMarketButton24h;
    @BindView(R.id.fragment_market_7d)
    TextView mMarketButton7d;
    @BindView(R.id.fragment_market_1m)
    TextView mMarketButton1m;
    @BindView(R.id.fragment_market_3m)
    TextView mMarketButton3m;
    @BindView(R.id.fragment_market_1y)
    TextView mMarketButton1y;
    @BindView(R.id.fragment_market_all)
    TextView mMarketButtonAll;


    public MarketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPresenterComponent().inject(this);
        mMarketPresenter.attachView(this);

        if (getArguments() != null) {
            color = getArguments().getInt(COLOR_OF_FRAGMENT);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_market, container, false);
        ButterKnife.bind(this, rootView);

        mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimespan._24H);

        mContainer.setBackgroundColor(Color.DKGRAY);
        mMarketButton24h.setOnClickListener(this);
        mMarketButton7d.setOnClickListener(this);
        mMarketButton1m.setOnClickListener(this);
        mMarketButton3m.setOnClickListener(this);
        mMarketButton1y.setOnClickListener(this);
        mMarketButtonAll.setOnClickListener(this);
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
        //Not implemented
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fragment_market_24h:
                mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimespan._24H);
                break;
            case R.id.fragment_market_7d:
                mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimespan._7D);
                break;
            case R.id.fragment_market_1m:
                mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimespan._1M);
                break;
            case R.id.fragment_market_3m:
                mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimespan._3M);
                break;
            case R.id.fragment_market_1y:
                mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimespan._1Y);
                break;
            case R.id.fragment_market_all:
                mMarketPresenter.getMarketCapAndVolumeGraphData(ChartTimespan.ALL);
                break;
        }
    }




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
        yAxisR.setTextColor(color);
        yAxisR.setGridColor(color);
        yAxisR.setDrawAxisLine(false);
        yAxisR.setValueFormatter(new YAxisFormatter());

        XAxis xAxis = mMarketCapChart.getXAxis();
        //xAxis.setTextSize(10f);
        xAxis.setTextColor(color);
        xAxis.setGridColor(color);
        xAxis.setLabelCount(5, true);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new XAxisFormatter(data.getTimestamps(), data.getChartTimespan()));


        //Data set and customization
        LineDataSet dataSetMcap = new LineDataSet(data.getMarketCapEntries(), "Mcap"); // add entries to dataset
        dataSetMcap.setColor(AttrUtil.getAttributeColor(mContext, R.attr.chart_line));
        dataSetMcap.setLineWidth(2f);
        dataSetMcap.setAxisDependency(YAxis.AxisDependency.RIGHT);
        dataSetMcap.setDrawCircles(false);

        LineDataSet dataSetVolume = new LineDataSet(data.getVolumeEntries(), "Volume"); // add entries to dataset
        dataSetVolume.setColor(AttrUtil.getAttributeColor(mContext, R.attr.chart_line_2));
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
        mMarketCapChart.setNoDataText("");
        mMarketCapChart.getDescription().setEnabled(false);
        mMarketCapChart.getLegend().setEnabled(false);
        mMarketCapChart.invalidate(); // refresh
    }
    @Override
    public void marketCapSetLast(String text) {
        mMarketCapLast.setText(text);
    }
    @Override
    public void marketCapActivateButton(ChartTimespan chartTimespan) {

        int activeColor = AttrUtil.getAttributeColor(mContext, R.attr.chart_button_active);
        int inactiveColor = AttrUtil.getAttributeColor(mContext, R.attr.chart_button_inactive);

        mMarketButton24h.setTextColor(inactiveColor);
        mMarketButton7d.setTextColor(inactiveColor);
        mMarketButton1m.setTextColor(inactiveColor);
        mMarketButton3m.setTextColor(inactiveColor);
        mMarketButton1y.setTextColor(inactiveColor);
        mMarketButtonAll.setTextColor(inactiveColor);

        switch (chartTimespan) {
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
        mMarketCapChartLoading.animate().setInterpolator(new LinearInterpolator()).alpha(1.0f).start();
    }

    @Override
    public void marketCapHideLoading() {
        mMarketCapChartLoading.animate().setInterpolator(new LinearInterpolator()).alpha(0.0f).start();
    }

    class YAxisFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return "$" + (int)value + "B";
        }
    }

    class XAxisFormatter implements IAxisValueFormatter {

        private List<Long> timestamps;
        private ChartTimespan chartTimespan;

        public XAxisFormatter(List<Long> timestamps, ChartTimespan chartTimespan) {
            this.timestamps = timestamps;
            this.chartTimespan = chartTimespan;
        }


        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            long timestamp = timestamps.get((int)value);
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis(timestamp);
            SimpleDateFormat dateFormat;

            switch (chartTimespan) {
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
