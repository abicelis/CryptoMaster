package ve.com.abicelis.cryptomaster.data.model;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.util.AttrUtil;

/**
 * Created by abicelis on 11/9/2018.
 */
public enum AlarmColor {
    COLOR_1(R.color.alarm_color_1),
    COLOR_2(R.color.alarm_color_2),
    COLOR_3(R.color.alarm_color_3),
    COLOR_4(R.color.alarm_color_4),
    COLOR_5(R.color.alarm_color_5),
    COLOR_6(R.color.alarm_color_6),
    COLOR_7(R.color.alarm_color_7),
    COLOR_8(R.color.alarm_color_8),
    COLOR_9(R.color.alarm_color_9);


    private @ColorRes int mColorAttrRes;

    AlarmColor(int colorAttrRes) {
        mColorAttrRes = colorAttrRes;
    }

    public int getColor(Context context) {
        return ContextCompat.getColor(context, mColorAttrRes);
    }

    public @ColorRes int getColorRes(Context context) {
        return mColorAttrRes;
    }


}
