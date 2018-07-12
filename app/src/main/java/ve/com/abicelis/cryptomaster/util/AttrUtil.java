package ve.com.abicelis.cryptomaster.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import timber.log.Timber;

/**
 * Created by abicelis on 16/6/2018.
 */
public class AttrUtil {

    public static int getAttributeColorResource(Context context, int attributeId) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attributeId, typedValue, true);
        return typedValue.resourceId;
    }
    public static int getAttributeColor(Context context, int attributeId) {
        int colorRes = getAttributeColorResource(context, attributeId);
        int color = -1;
        try {
            color = context.getResources().getColor(colorRes);
        } catch (Resources.NotFoundException e) {
            Timber.e("Not found color resource by id: %1$d", colorRes);
        }
        return color;
    }


}
