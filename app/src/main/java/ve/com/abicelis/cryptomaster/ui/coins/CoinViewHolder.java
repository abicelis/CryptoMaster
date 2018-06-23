package ve.com.abicelis.cryptomaster.ui.coins;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.CoinsFragmentType;
import ve.com.abicelis.cryptomaster.util.AttrUtil;
import ve.com.abicelis.cryptomaster.util.StringUtil;

/**
 * Created by abicelis on 16/6/2018.
 */

public class CoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

    //DATA
    private enum State {NORMAL, SHOWING_FAVORITE_OVERLAY}
    private CoinsAdapter mAdapter;
    private Activity mActivity;
    private CoinsFragmentType mCoinsFragmentType;
    private Coin mCurrent;
    private State mState;


    // class member variable to save the X,Y coordinates
    private float[] lastTouchDownXY = new float[2];
    float mEndRadius;

    //UI
    Handler mFavoriteHandler;

    @BindView(R.id.list_item_coin_favorite)
    RelativeLayout mFavorite;
    @BindView(R.id.list_item_coin_favorite_text)
    TextView mFavoriteText;
    @BindView(R.id.list_item_coin_favorite_cancel)
    TextView mFavoriteCancel;

    @BindView(R.id.list_item_coin_container)
    FrameLayout mContainer;
    @BindView(R.id.list_item_coin_name)
    TextView mName;
    @BindView(R.id.list_item_coin_symbol)
    TextView mSymbol;
    @BindView(R.id.list_item_coin_icon)
    ImageView mIcon;

    @BindView(R.id.list_item_coin_price)
    TextView mPrice;
    @BindView(R.id.list_item_coin_mcap)
    TextView mMcap;
    @BindView(R.id.list_item_coin_vol)
    TextView mVolume;

    @BindView(R.id.list_item_coin_1h)
    TextView mPercent1h;
    @BindView(R.id.list_item_coin_24h)
    TextView mPercent24h;
    @BindView(R.id.list_item_coin_7d)
    TextView mPercent7d;



    public CoinViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(CoinsAdapter adapter, Activity activity, Coin current, CoinsFragmentType coinsFragmentType) {
        mAdapter = adapter;
        mActivity = activity;
        mCurrent = current;
        mCoinsFragmentType = coinsFragmentType;

        mState = State.NORMAL;
        if (mFavoriteHandler != null)
            mFavoriteHandler.removeCallbacksAndMessages(null);
        mFavorite.setVisibility(View.GONE);

        mName.setText(mCurrent.getName());
        mSymbol.setText(mCurrent.getSymbol());

        Glide.with(activity).load(String.format(Constants.COINMARKETCAP_ICONS_BASE_URL, mCurrent.getId())).into(mIcon);

        mPrice.setText(StringUtil.doubleMaxTwoDecimals(mCurrent.getPrice()));
        mMcap.setText(StringUtil.withSuffix(mCurrent.getMarketCap()));
        mVolume.setText(StringUtil.withSuffix(mCurrent.getVolume24h()));

        mPercent1h.setText(String.valueOf(mCurrent.getPercentChange1h()));
        mPercent24h.setText(String.valueOf(mCurrent.getPercentChange24h()));
        mPercent7d.setText(String.valueOf(mCurrent.getPercentChange7d()));


        if(mCurrent.getPercentChange1h() >= 0)
            mPercent1h.setTextColor(AttrUtil.getAttributeColor(mActivity, R.attr.text_positive));
        else
            mPercent1h.setTextColor(AttrUtil.getAttributeColor(mActivity, R.attr.text_negative));

        if(mCurrent.getPercentChange24h() >= 0)
            mPercent24h.setTextColor(AttrUtil.getAttributeColor(mActivity, R.attr.text_positive));
        else
            mPercent24h.setTextColor(AttrUtil.getAttributeColor(mActivity, R.attr.text_negative));

        if(mCurrent.getPercentChange7d() >= 0)
            mPercent7d.setTextColor(AttrUtil.getAttributeColor(mActivity, R.attr.text_positive));
        else
            mPercent7d.setTextColor(AttrUtil.getAttributeColor(mActivity, R.attr.text_negative));
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setListeners() {

        //mContainer.setOnClickListener(this);
        mContainer.setOnLongClickListener(this);
        mContainer.setOnTouchListener(this);
        mFavoriteCancel.setOnClickListener(this);
    }


    //    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        switch (id) {
//            case R.id.list_item_flight_container:
//                mAdapter.triggerFlightClicked(mCurrent);
//                break;
//        }
//    }
//
    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.list_item_coin_container:

                if(mState == State.NORMAL) {
                    mState = State.SHOWING_FAVORITE_OVERLAY;

                    if(new SharedPreferenceHelper().isFavoriteCoin(mCurrent.getId()))
                        mFavoriteText.setText(String.format(Locale.getDefault(), mActivity.getResources().getString(R.string.fragment_coins_removed_from_favorites), mCurrent.getName()));
                    else
                        mFavoriteText.setText(String.format(Locale.getDefault(), mActivity.getResources().getString(R.string.fragment_coins_added_to_favorites), mCurrent.getName()));


                    mEndRadius = (int) Math.hypot(v.getWidth(), v.getHeight());
                    Animator anim = ViewAnimationUtils.createCircularReveal(mFavorite, (int)lastTouchDownXY[0], (int)lastTouchDownXY[1], 0, mEndRadius);
                    anim.setInterpolator(new AccelerateDecelerateInterpolator());
                    //anim.setDuration(300);
                    mFavorite.setVisibility(View.VISIBLE);
                    anim.start();

                    mFavoriteHandler = new Handler();
                    mFavoriteHandler.postDelayed(() -> hideFavorite(false), Constants.COINS_FRAGMENT_FAVORITE_DELAY);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        // save the X,Y coordinates
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            lastTouchDownXY[0] = event.getX();
            lastTouchDownXY[1] = event.getY();
        }

        // let the touch event pass on to whoever needs it
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.list_item_coin_favorite_cancel:
                if(mState == State.SHOWING_FAVORITE_OVERLAY) {
                    mFavoriteHandler.removeCallbacksAndMessages(null);
                    hideFavorite(true);
                }
                break;
        }
    }


    private void hideFavorite(boolean isCancelled) {
        if(mFavorite.isAttachedToWindow()) {
            Animator animator = (isCancelled) ?
                    ViewAnimationUtils.createCircularReveal(mFavorite,  mFavorite.getMeasuredWidth(), mFavorite.getMeasuredHeight()/2, mEndRadius, 0) :
                    ViewAnimationUtils.createCircularReveal(mFavorite,  0, mFavorite.getMeasuredHeight()/2, mEndRadius, 0);

            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    doHandleHideFavorite(isCancelled);

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            //animator.setDuration(300);
            animator.start();
        } else {
            doHandleHideFavorite(isCancelled);
        }

    }

    public void doHandleHideFavorite(boolean isCancelled) {
        if(!isCancelled) {
            if (!new SharedPreferenceHelper().isFavoriteCoin(mCurrent.getId()))
                new SharedPreferenceHelper().setCoinAsFavorite(mCurrent.getId());
            else {
                new SharedPreferenceHelper().removeCoinFromFavorites(mCurrent.getId());

                if(mCoinsFragmentType == CoinsFragmentType.FAVORITES) {
                    int pos = getAdapterPosition();
                    mAdapter.getItems().remove(pos);
                    mAdapter.notifyItemRemoved(pos);
                    mAdapter.notifyItemRangeChanged(pos, mAdapter.getItemCount());
                }
            }
        }


        mFavorite.setVisibility(View.INVISIBLE);
        mState = State.NORMAL;
    }

}