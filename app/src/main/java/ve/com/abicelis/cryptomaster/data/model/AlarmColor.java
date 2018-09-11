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
    color_1(R.attr.chart_line),
    color_2(R.attr.chart_line_2),
    color_3(R.attr.chart_line_3),
    color_4(R.attr.chart_line_4),
    color_5(R.attr.chart_line_5),
    color_6(R.attr.chart_line_6);


    private @AttrRes int mColorAttrRes;

    AlarmColor(int colorAttrRes) {
        mColorAttrRes = colorAttrRes;
    }

    public @ColorRes int getColor(Context context) {
        return AttrUtil.getAttributeColor(context, mColorAttrRes);
    }

    public @ColorRes int getColorRes(Context context) {
        return AttrUtil.getAttributeColorResource(context, mColorAttrRes);
    }

    public String getColorHexString(Context context) {
        return AttrUtil.getAttributeColorHexString(context, mColorAttrRes);
    }

}
