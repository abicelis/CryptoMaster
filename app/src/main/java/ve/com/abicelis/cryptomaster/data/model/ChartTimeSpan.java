package ve.com.abicelis.cryptomaster.data.model;

import android.content.Context;
import android.support.annotation.StringRes;

import ve.com.abicelis.cryptomaster.R;

/**
 * Created by abicelis on 3/7/2018.
 */
public enum ChartTimeSpan {
    _24H(R.string.chart_time_span_24h),
    _7D(R.string.chart_time_span_7d),
    _1M(R.string.chart_time_span_1m),
    _3M(R.string.chart_time_span_3m),
    _1Y(R.string.chart_time_span_1y),
    ALL(R.string.chart_time_span_all);


    private @StringRes int mFriendlyNameRes;

    ChartTimeSpan(int friendlyNameRes) {
        mFriendlyNameRes = friendlyNameRes;
    }

    public String getFriendlyName(Context context) {
        return context.getString(mFriendlyNameRes);
    }

    public @StringRes int getFriendlyNameRes() { return mFriendlyNameRes; }
}
