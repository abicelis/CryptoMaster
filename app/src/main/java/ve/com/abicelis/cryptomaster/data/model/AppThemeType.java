package ve.com.abicelis.cryptomaster.data.model;

import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

import ve.com.abicelis.cryptomaster.application.CryptoMasterApplication;
import ve.com.abicelis.cryptomaster.R;

/**
 * Created by abicelis on 9/9/2017.
 */

public enum AppThemeType {
    LIGHT(R.style.AppTheme, R.string.app_theme_type_light),
    DARK(R.style.AppThemeDark, R.string.app_theme_type_dark);

    private @StyleRes
    int mStyleRes;
    private @StringRes
    int mFriendlyName;


    AppThemeType(@StyleRes int styleRes, @StringRes int friendlyName) {
        mStyleRes = styleRes;
        mFriendlyName = friendlyName;
    }

    public @StyleRes
    int getTheme() {
        return mStyleRes;
    }

    public String getFriendlyName() {
        return CryptoMasterApplication.getAppContext().getString(mFriendlyName);
    }
}
