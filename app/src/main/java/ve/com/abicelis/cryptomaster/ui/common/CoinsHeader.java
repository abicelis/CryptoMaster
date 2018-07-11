package ve.com.abicelis.cryptomaster.ui.common;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.model.CoinsSortType;

/**
 * Created by abicelis on 10/7/2018.
 */
public class CoinsHeader extends ConstraintLayout implements View.OnClickListener {

    private CoinsSortType mCurrentCoinsSortType = CoinsSortType.MCAP_DESCENDING;
    boolean isAnimating = false;

    @BindView(R.id.fragment_coin_header_name_container)
    ConstraintLayout mHeaderNameContainer;
    @BindView(R.id.fragment_coin_header_name_sort)
    TextView mHeaderNameSort;
    @BindView(R.id.fragment_coin_header_price_container)
    ConstraintLayout mHeaderPriceContainer;
    @BindView(R.id.fragment_coin_header_price_sort)
    TextView mHeaderPriceSort;
    @BindView(R.id.fragment_coin_header_mcap_container)
    ConstraintLayout mHeaderMcapContainer;
    @BindView(R.id.fragment_coin_header_mcap_sort)
    TextView mHeaderMcapSort;
    @BindView(R.id.fragment_coin_header_vol_container)
    ConstraintLayout mHeaderVolContainer;
    @BindView(R.id.fragment_coin_header_vol_sort)
    TextView mHeaderVolSort;
    @BindView(R.id.fragment_coin_header_1h_container)
    ConstraintLayout mHeader1hContainer;
    @BindView(R.id.fragment_coin_header_1h_sort)
    TextView mHeader1hSort;
    @BindView(R.id.fragment_coin_header_24h_container)
    ConstraintLayout mHeader24hContainer;
    @BindView(R.id.fragment_coin_header_24h_sort)
    TextView mHeader24hSort;
    @BindView(R.id.fragment_coin_header_7d_container)
    ConstraintLayout mHeader7dContainer;
    @BindView(R.id.fragment_coin_header_7d_sort)
    TextView mHeader7dSort;

    private CoinsHeaderSortListener mListener;

    public CoinsHeader(Context context) {
        super(context);
        init(context, null);

    }

    public CoinsHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs);

    }

    public CoinsHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, @Nullable AttributeSet attrs) {

        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.fragment_coins_header, this, true);
        ButterKnife.bind(this, rootView);

        mHeaderNameContainer.setOnClickListener(this);
        mHeaderPriceContainer.setOnClickListener(this);
        mHeaderMcapContainer.setOnClickListener(this);
        mHeaderVolContainer.setOnClickListener(this);
        mHeader1hContainer.setOnClickListener(this);
        mHeader24hContainer.setOnClickListener(this);
        mHeader7dContainer.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(isAnimating)
            return;

        switch (id) {
            case R.id.fragment_coin_header_name_container:
                switch (mCurrentCoinsSortType) {
                    case NAME_ASCENDING:
                        rotateSortIcon(mHeaderNameSort, 0f);
                        mCurrentCoinsSortType = CoinsSortType.NAME_DESCENDING;
                        break;
                    case NAME_DESCENDING:
                        rotateSortIcon(mHeaderNameSort, 180f);
                        mCurrentCoinsSortType = CoinsSortType.NAME_ASCENDING;
                        break;
                    default:
                        hideCurrentSortIcon();
                        showSortIcon(mHeaderNameSort, 180f);
                        mCurrentCoinsSortType = CoinsSortType.NAME_ASCENDING;
                        break;
                }
                break;

            case R.id.fragment_coin_header_price_container:
                switch (mCurrentCoinsSortType) {
                    case PRICE_ASCENDING:
                        rotateSortIcon(mHeaderPriceSort, 0f);
                        mCurrentCoinsSortType = CoinsSortType.PRICE_DESCENDING;
                        break;
                    case PRICE_DESCENDING:
                        rotateSortIcon(mHeaderPriceSort, 180f);
                        mCurrentCoinsSortType = CoinsSortType.PRICE_ASCENDING;
                        break;
                    default:
                        hideCurrentSortIcon();
                        showSortIcon(mHeaderPriceSort, 0f);
                        mCurrentCoinsSortType = CoinsSortType.PRICE_DESCENDING;
                        break;
                }
                break;


            case R.id.fragment_coin_header_mcap_container:
                switch (mCurrentCoinsSortType) {
                    case MCAP_ASCENDING:
                        rotateSortIcon(mHeaderMcapSort, 0f);
                        mCurrentCoinsSortType = CoinsSortType.MCAP_DESCENDING;
                        break;
                    case MCAP_DESCENDING:
                        rotateSortIcon(mHeaderMcapSort, 180f);
                        mCurrentCoinsSortType = CoinsSortType.MCAP_ASCENDING;
                        break;
                    default:
                        hideCurrentSortIcon();
                        showSortIcon(mHeaderMcapSort, 0f);;
                        mCurrentCoinsSortType = CoinsSortType.MCAP_DESCENDING;
                        break;
                }
                break;



            case R.id.fragment_coin_header_vol_container:
                switch (mCurrentCoinsSortType) {
                    case VOL_ASCENDING:
                        rotateSortIcon(mHeaderVolSort, 0f);
                        mCurrentCoinsSortType = CoinsSortType.VOL_DESCENDING;
                        break;
                    case VOL_DESCENDING:
                        rotateSortIcon(mHeaderVolSort, 180f);
                        mCurrentCoinsSortType = CoinsSortType.VOL_ASCENDING;
                        break;
                    default:
                        hideCurrentSortIcon();
                        showSortIcon(mHeaderVolSort, 0f);
                        mCurrentCoinsSortType = CoinsSortType.VOL_DESCENDING;
                        break;
                }
                break;



            case R.id.fragment_coin_header_1h_container:
                switch (mCurrentCoinsSortType) {
                    case _1H_ASCENDING:
                        rotateSortIcon(mHeader1hSort, 0f);
                        mCurrentCoinsSortType = CoinsSortType._1H_DESCENDING;
                        break;
                    case _1H_DESCENDING:
                        rotateSortIcon(mHeader1hSort, 180f);
                        mCurrentCoinsSortType = CoinsSortType._1H_ASCENDING;
                        break;
                    default:
                        hideCurrentSortIcon();
                        showSortIcon(mHeader1hSort, 0f);
                        mCurrentCoinsSortType = CoinsSortType._1H_DESCENDING;
                        break;
                }
                break;



            case R.id.fragment_coin_header_24h_container:
                switch (mCurrentCoinsSortType) {
                    case _24H_ASCENDING:
                        rotateSortIcon(mHeader24hSort, 0f);
                        mCurrentCoinsSortType = CoinsSortType._24H_DESCENDING;
                        break;
                    case _24H_DESCENDING:
                        rotateSortIcon(mHeader24hSort, 180f);
                        mCurrentCoinsSortType = CoinsSortType._24H_ASCENDING;
                        break;
                    default:
                        hideCurrentSortIcon();
                        showSortIcon(mHeader24hSort, 0f);
                        mCurrentCoinsSortType = CoinsSortType._24H_DESCENDING;
                        break;
                }
                break;



            case R.id.fragment_coin_header_7d_container:
                switch (mCurrentCoinsSortType) {
                    case _7D_ASCENDING:
                        rotateSortIcon(mHeader7dSort, 0f);
                        mCurrentCoinsSortType = CoinsSortType._7D_DESCENDING;
                        break;
                    case _7D_DESCENDING:
                        rotateSortIcon(mHeader7dSort, 180f);
                        mCurrentCoinsSortType = CoinsSortType._7D_ASCENDING;
                        break;
                    default:
                        hideCurrentSortIcon();
                        showSortIcon(mHeader7dSort, 0f);
                        mCurrentCoinsSortType = CoinsSortType._7D_DESCENDING;
                        break;
                }
                break;

        }

        mListener.sortChangedTo(mCurrentCoinsSortType);
    }

    private void rotateSortIcon(View view, float angle) {
        isAnimating = true;
        view.animate().setInterpolator(new DecelerateInterpolator()).rotation(angle).setListener(new CustomAnimatorListener(view, AnimationType.ROTATE)).start();
    }

    private void showSortIcon(View view, float defaultAngle) {
        isAnimating = true;
        view.setRotation(defaultAngle);
        view.animate().setInterpolator(new DecelerateInterpolator()).alpha(1f).scaleX(1f).setListener(new CustomAnimatorListener(view, AnimationType.SHOW)).start();
    }

    private void hideSortIcon(View view) {
        isAnimating = true;
        view.animate().setInterpolator(new DecelerateInterpolator()).alpha(0f).scaleX(0f).setListener(new CustomAnimatorListener(view, AnimationType.HIDE)).start();
    }

    private void hideCurrentSortIcon() {
        switch (mCurrentCoinsSortType) {
            case NAME_ASCENDING:
            case NAME_DESCENDING:
                hideSortIcon(mHeaderNameSort);
                break;
            case PRICE_ASCENDING:
            case PRICE_DESCENDING:
                hideSortIcon(mHeaderPriceSort);
                break;
            case MCAP_ASCENDING:
            case MCAP_DESCENDING:
                hideSortIcon(mHeaderMcapSort);
                break;
            case VOL_ASCENDING:
            case VOL_DESCENDING:
                hideSortIcon(mHeaderVolSort);
                break;
            case _1H_ASCENDING:
            case _1H_DESCENDING:
                hideSortIcon(mHeader1hSort);
                break;
            case _24H_ASCENDING:
            case _24H_DESCENDING:
                hideSortIcon(mHeader24hSort);
                break;
            case _7D_ASCENDING:
            case _7D_DESCENDING:
                hideSortIcon(mHeader7dSort);
                break;
        }
    }


    public enum AnimationType {SHOW, HIDE, ROTATE}

    public class CustomAnimatorListener implements Animator.AnimatorListener {

        private View mView;
        private AnimationType mAnimationType;

        public CustomAnimatorListener(View view, AnimationType animationType) {
            mView = view;
            mAnimationType = animationType;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            if(mAnimationType == AnimationType.SHOW)
                mView.setVisibility(View.VISIBLE);

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if(mAnimationType == AnimationType.HIDE)
                mView.setVisibility(View.GONE);

            isAnimating = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) { }

        @Override
        public void onAnimationRepeat(Animator animation) { }
    }







    public void setCoinsHeaderSortListener(CoinsHeaderSortListener coinsHeaderSortListener) {
        mListener = coinsHeaderSortListener;
    }
    public interface CoinsHeaderSortListener {
        void sortChangedTo(CoinsSortType coinsSortType);
    }

}
