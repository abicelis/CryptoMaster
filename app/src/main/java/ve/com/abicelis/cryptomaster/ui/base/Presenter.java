package ve.com.abicelis.cryptomaster.ui.base;

/**
 * Created by abicelis on 25/5/2018.
 *
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
public interface Presenter<V extends MvpView> {
    void attachView(V mvpView);
    void detachView();
}
