package ve.com.abicelis.cryptomaster.util;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

import java.lang.reflect.Field;

import ve.com.abicelis.cryptomaster.R;

/**
 * Created by abicelis on 25/5/2018.
 */

public class SnackbarUtil {


    //Taken from https://stackoverflow.com/questions/37221914/snackbar-and-other-animations-stopped-working-on-some-android-devices
    private static void accessibilityBypass(Snackbar snackbar) {
        try {
            Field mAccessibilityManagerField = BaseTransientBottomBar.class.getDeclaredField("mAccessibilityManager");
            mAccessibilityManagerField.setAccessible(true);
            AccessibilityManager accessibilityManager = (AccessibilityManager) mAccessibilityManagerField.get(snackbar);
            Field mIsEnabledField = AccessibilityManager.class.getDeclaredField("mIsEnabled");
            mIsEnabledField.setAccessible(true);
            mIsEnabledField.setBoolean(accessibilityManager, false);
            mAccessibilityManagerField.set(snackbar, accessibilityManager);
        } catch (Exception e) {
            Log.d("Snackbar", "Reflection error: " + e.toString());
        }
    }

    public static void showSnackbar(View container, @NonNull SnackbarType snackbarType,
                                    @StringRes int textStringRes, @Nullable SnackbarDuration duration,
                                    @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {

        duration = (duration == null ? SnackbarDuration.LONG : duration);

        Snackbar snackbar = Snackbar.make(container, textStringRes, duration.getDuration());
        accessibilityBypass(snackbar);

        snackbar.getView().setBackgroundResource(snackbarType.getColorRes());
        TextView snackbarText = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackbarText.setCompoundDrawablesWithIntrinsicBounds(0, 0,snackbarType.getIconRes(), 0);
        snackbarText.setGravity(Gravity.CENTER);
        if(callback != null)
            snackbar.addCallback(callback);
        snackbar.show();
    }

    public static void showSnackbar(View container, @NonNull SnackbarType snackbarType,
                                    @NonNull String textString, @Nullable SnackbarDuration duration,
                                    @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {

        duration = (duration == null ? SnackbarDuration.LONG : duration);

        Snackbar snackbar = Snackbar.make(container, textString, duration.getDuration());
        snackbar.getView().setBackgroundResource(snackbarType.getColorRes());
        TextView snackbarText = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackbarText.setCompoundDrawablesWithIntrinsicBounds(0, 0,snackbarType.getIconRes(), 0);
        snackbarText.setGravity(Gravity.CENTER);
        if(callback != null)
            snackbar.addCallback(callback);
        snackbar.show();
    }


    public enum SnackbarDuration {
        LONG(Snackbar.LENGTH_LONG),
        SHORT(Snackbar.LENGTH_SHORT),
        INDEFINITE(Snackbar.LENGTH_INDEFINITE);

        private int duration;

        SnackbarDuration(int duration){
            this.duration = duration;
        }

        public int getDuration() {
            return duration;
        }
    }

    public enum SnackbarType {
        ERROR(R.color.snackbar_error_background, R.drawable.ic_snackbar_error),
        SUCCESS(R.color.snackbar_success_background, R.drawable.ic_snackbar_success),
        NOTICE(R.color.snackbar_notice_background, R.drawable.ic_snackbar_warning);

        private @ColorRes
        int colorRes;
        private @DrawableRes
        int iconRes;

        SnackbarType(@ColorRes int colorRes, @DrawableRes int iconRes){
            this.colorRes = colorRes;
            this.iconRes = iconRes;
        }

        public @ColorRes
        int getColorRes() {
            return colorRes;
        }

        public @DrawableRes
        int getIconRes() {
            return iconRes;
        }
    }

}


