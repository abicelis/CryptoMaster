package ve.com.abicelis.cryptomaster.util;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

/**
 * Created by abicelis on 23/7/2018.
 */
public class TextViewUtil {

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }
}
