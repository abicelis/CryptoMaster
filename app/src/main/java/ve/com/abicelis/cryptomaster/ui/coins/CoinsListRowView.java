package ve.com.abicelis.cryptomaster.ui.coins;

import android.support.annotation.StringRes;

import java.util.Locale;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.model.Coin;

/**
 * Created by abicelis on 24/6/2018.
 */
public interface CoinsListRowView {
    void setData(CoinsListPresenter presenter, Coin current);
    void setListeners();

    void revealFavoriteView(@StringRes int stringRes);
    void hideFavoriteView();
    void cancelFavoriteView();
}
