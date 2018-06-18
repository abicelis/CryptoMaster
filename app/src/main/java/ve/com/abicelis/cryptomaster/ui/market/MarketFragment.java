package ve.com.abicelis.cryptomaster.ui.market;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.ui.base.BaseFragment;

/**
 * Created by abicelis on 30/5/2018.
 */
public class MarketFragment extends BaseFragment implements MarketMvpView {

    public static final String COLOR_OF_FRAGMENT = "COLOR_OF_FRAGMENT";
    private int color;

    @Inject
    MarketPresenter mMarketPresenter;

    @BindView(R.id.fragment_market_container)
    ConstraintLayout mContainer;
    @BindView(R.id.fragment_market_chart_market_cap)
    LineChartView mMarketCapChart;


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

        mMarketPresenter.getMarketCapGraphData();

        mContainer.setBackgroundColor(Color.DKGRAY);
        return rootView;
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
    public void showMarketCapGraph(List<PointValue> values) {

        Line line = new Line(values).setColor(Color.WHITE).setCubic(true);
        List<Line> lines = new ArrayList<>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);
        mMarketCapChart.setInteractive(false);
        mMarketCapChart.setLineChartData(data);
    }
}
