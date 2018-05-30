package ve.com.abicelis.cryptomaster.ui.market;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.ui.base.BaseFragment;
import ve.com.abicelis.cryptomaster.ui.base.MvpView;
import ve.com.abicelis.cryptomaster.ui.home.HomePresenter;

/**
 * Created by abicelis on 30/5/2018.
 */
public class MarketFragment extends BaseFragment implements MarketMvpView {

    public static final String COLOR_OF_FRAGMENT = "COLOR_OF_FRAGMENT";
    private int color;

    @Inject
    MarketPresenter mMarketPresenter;

    @BindView(R.id.fragment_color_container)
    FrameLayout mContainer;


    public MarketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPresenterComponent().inject(this);
        mMarketPresenter.attachView(this);

        if (getArguments() != null) {
            color = getArguments().getInt(COLOR_OF_FRAGMENT);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_color, container, false);
        ButterKnife.bind(this, rootView);

        mContainer.setBackgroundColor(Color.DKGRAY);
        return rootView;
    }

    @Override
    public void showMessage(Message message, @Nullable BaseTransientBottomBar.BaseCallback<Snackbar> callback) {
        //Not implemented
    }
}
