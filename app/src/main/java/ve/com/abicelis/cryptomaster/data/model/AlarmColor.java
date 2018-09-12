package ve.com.abicelis.cryptomaster.data.model;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.util.AttrUtil;

/**
 * Created by abicelis on 11/9/2018.
 */
public enum AlarmColor {
    COLOR_1(R.attr.chart_line),
    COLOR_2(R.attr.chart_line_2),
    COLOR_3(R.attr.chart_line_3),
    COLOR_4(R.attr.chart_line_4),
    COLOR_5(R.attr.chart_line_5),
    COLOR_6(R.attr.chart_line_6);


    private @AttrRes int mColorAttrRes;

    AlarmColor(int colorAttrRes) {
        mColorAttrRes = colorAttrRes;
    }

    public int getColor(Context context) {
        return AttrUtil.getAttributeColor(context, mColorAttrRes);
    }

    public @ColorRes int getColorRes(Context context) {
        return AttrUtil.getAttributeColorResource(context, mColorAttrRes);
    }

    public String getColorHexString(Context context) {
        return AttrUtil.getAttributeColorHexString(context, mColorAttrRes);
    }

}
