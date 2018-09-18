package ve.com.abicelis.cryptomaster.ui.common.coinsearchview;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.model.CachedCoin;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.util.AttrUtil;
import ve.com.abicelis.cryptomaster.util.ViewUtil;

/**
 * Created by abicelis on 13/9/2018.
 */
public class CoinSearchView extends FrameLayout {

    //DATA
    int mSearchViewRadius;
    int mExpandedHeight;
    int mNormalHeight;
    boolean cachedCoinsLoaded = false;
    Currency mBaseCoin;
    CachedCoin mQuoteCoin;
    private State mState = State.IDLE_NO_COIN;

    private CoinSearchViewListener mCoinSearchViewListener;


    @BindView(R.id.view_coin_search_hint_container)
    ConstraintLayout mContainerHint;

    @BindView(R.id.view_coin_search_coin_container)
    ConstraintLayout mCoinContainer;
    @BindView(R.id.view_coin_search_coin_pair)
    TextView mCoinPair;
    @BindView(R.id.view_coin_search_coin_price)
    TextView mCoinPrice;
    @BindView(R.id.view_coin_search_coin_loading)
    ProgressBar mCoinLoading;

    @BindView(R.id.view_coin_search_search_container)
    LinearLayout mContainerSearch;
    @BindView(R.id.view_coin_search_edittext)
    EditText mSearchEditText;

    @BindView(R.id.view_coin_search_progress)
    ProgressBar mProgress;
    @BindView(R.id.view_coin_search_recycler)
    RecyclerView mRecycler;
    private LinearLayoutManager mLayoutManager;
    private CSVAdapter mAdapter;

    public CoinSearchView(Context context) {
        super(context);
        init(context,null);
    }

    public CoinSearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CoinSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_coin_search, this);
        //LayoutInflater.from(context).inflate(R.layout.view_coin_search, this, true);


        ButterKnife.bind(this);

//        //Get/apply custom xml configs
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.fancy_edit_text);
//        int iconId = a.getResourceId(R.styleable.fancy_edit_text_icon, -1);
//        int textId = a.getResourceId(R.styleable.fancy_edit_text_text, -1);
//        int hintId = a.getResourceId(R.styleable.fancy_edit_text_hint, -1);
//        int maxLines = a.getInt(R.styleable.fancy_edit_text_maxLines, -1);
//
//
//        setIcon(iconId);
//        setText(textId);
//        setHint(hintId);
//        setMaxLines(maxLines);
//        a.recycle();
//
//
//        this.setBackgroundResource(R.drawable.white_round_edges_background);
//        this.setGravity(Gravity.CENTER_VERTICAL);

        mContainerHint.setOnClickListener(v -> {
            showSearch();
        });

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(cachedCoinsLoaded) {
                    mCoinSearchViewListener.requestCachedCoins(s.toString());
                }
            }
        });

        this.setBackgroundColor(AttrUtil.getAttributeColor(context, R.attr.default_foreground));

        //Recycler
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        CSVAdapter.ItemClickedListener listener = (coin) -> {
            if (!coin.getCode().equals(mBaseCoin.getCode())) {
                mState = State.IDLE_WITH_COIN;
                mQuoteCoin = coin;

                mAdapter.getItems().clear();
                cachedCoinsLoaded = false;
                mSearchEditText.setText("");
                hideSearch();
                showCoin();
            } else {
                mCoinSearchViewListener.showMessage(Message.DUPLICATE_QUOTE_BASE_COIN);
            }
        };

        mAdapter = new CSVAdapter(getContext(), listener);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }



    private void showSearch() {
        mState = State.SEARCH_OPEN;

        ViewGroup.LayoutParams layoutParams = getLayoutParams();

        if (mExpandedHeight == 0) {
            mExpandedHeight = mCoinSearchViewListener.getExpandedHeight();
            mSearchViewRadius = (int) Math.hypot(getWidth() ,mExpandedHeight);
            mNormalHeight = layoutParams.height;
        }
        layoutParams.height = mExpandedHeight;
        setLayoutParams(layoutParams);

        mContainerSearch.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.VISIBLE);

        Animator anim = ViewAnimationUtils.createCircularReveal(mContainerSearch, getMeasuredWidth()/2, 0, 0, mSearchViewRadius);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        //anim.setDuration(300);
        anim.start();

        mSearchEditText.requestFocus();
        //ViewUtil.showKeyboardOn(getContext(), mSearchEditText);

        //Notify listener
        mCoinSearchViewListener.requestCachedCoins("");
    }

    public void hideSearch() {
        mCoinSearchViewListener.hideKeyboard();


        Animator animator = ViewAnimationUtils.createCircularReveal(mContainerSearch, getMeasuredWidth()/2, 0, mSearchViewRadius, 0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                mContainerSearch.setVisibility(View.GONE);
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = mNormalHeight;
                setLayoutParams(layoutParams);
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });

    }

    private void showCoin() {
        mCoinContainer.setVisibility(View.VISIBLE);
        mCoinLoading.setVisibility(View.VISIBLE);
        mCoinPair.setText(String.format(Locale.getDefault(), "%1$s/%2$s", mBaseCoin.getCode(), mQuoteCoin.getCode()));
        mCoinPrice.setText("");
        mCoinSearchViewListener.getPairQuote(mQuoteCoin);
    }

    private void hideCoin() {
        mCoinContainer.setVisibility(View.GONE);
    }

    public void updateCachedCoins(List<CachedCoin> cachedCoins) {
        cachedCoinsLoaded = true;
        mProgress.setVisibility(View.GONE);
        mAdapter.getItems().clear();
        mAdapter.getItems().addAll(cachedCoins);
        mAdapter.notifyDataSetChanged();
    }

    public void setBaseCoin(Currency baseCoin) {
        mBaseCoin = baseCoin;

        if(mState == State.IDLE_WITH_COIN) {
            showCoin();
        }
    }

    public void setPairQuote(double quote) {
        mCoinLoading.setVisibility(View.GONE);
        mCoinPrice.setText(String.format(Locale.getDefault(), "%1$f", quote));
    }


    //CoinSearchViewListener
    public void setCoinSearchViewListener(CoinSearchViewListener listener) {
        mCoinSearchViewListener = listener;
    }
    public interface CoinSearchViewListener {
        int getExpandedHeight();
        void hideKeyboard();
        void requestCachedCoins(String query);
        void showMessage(Message message);
        void getPairQuote(CachedCoin quoteCoin);
    }



    public enum State { IDLE_NO_COIN, SEARCH_OPEN, IDLE_WITH_COIN }
}
