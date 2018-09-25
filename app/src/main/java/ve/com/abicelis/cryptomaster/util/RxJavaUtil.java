package ve.com.abicelis.cryptomaster.util;

import io.reactivex.CompletableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abicelis on 24/9/2018.
 */
public class RxJavaUtil {


    public static CompletableTransformer applySchedulersIo() {
        return (upstream) -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static CompletableTransformer applySchedulersAndroidMainThread() {
        return (upstream) -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
