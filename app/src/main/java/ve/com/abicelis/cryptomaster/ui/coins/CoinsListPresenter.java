package ve.com.abicelis.cryptomaster.ui.coins;

import android.arch.persistence.room.EmptyResultSetException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.application.Constants;
import ve.com.abicelis.cryptomaster.application.Message;
import ve.com.abicelis.cryptomaster.data.DataManager;
import ve.com.abicelis.cryptomaster.data.model.Coin;
import ve.com.abicelis.cryptomaster.data.model.CoinsFragmentType;
import ve.com.abicelis.cryptomaster.data.model.CoinsListViewHolderState;
import ve.com.abicelis.cryptomaster.data.model.CoinsSortType;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorBy1hAsc;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorBy1hDesc;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorBy24hAsc;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorBy24hDesc;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorBy7dAsc;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorBy7dDesc;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorByMcapAsc;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorByMcapDesc;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorByNameAsc;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorByNameDesc;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorByPriceAsc;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorByPriceDesc;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorByVolAsc;
import ve.com.abicelis.cryptomaster.data.model.coinsort.CoinComparatorByVolDesc;
import ve.com.abicelis.cryptomaster.ui.base.BasePresenter;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Created by abicelis on 24/6/2018.
 */
public class CoinsListPresenter extends BasePresenter<CoinsListMvpView> {

    private DataManager mDataManager;

    //DATA
    private List<Coin> mItems = new ArrayList<>();
    private CoinsSortType mCoinsSortType;

    private boolean isLoading;
    private CoinsFragmentType mCoinsFragmentType = null;


    public CoinsListPresenter(DataManager dataManager) {
        isLoading = false;
        mDataManager = dataManager;
    }

    public void setCoinFragmentType(CoinsFragmentType fragmentType){ mCoinsFragmentType = fragmentType; }
    public CoinsFragmentType getCoinFragmentType() { return mCoinsFragmentType; }

    public void setCoinsSortType(CoinsSortType coinsSortType) { mCoinsSortType = coinsSortType; }
    public CoinsSortType getCoinsSortType() { return mCoinsSortType; }


    public void onBindRepositoryRowViewAtPosition(int position, CoinsListRowView rowView) {
        Coin current = mItems.get(position);
        rowView.setData(this, current);
        rowView.setListeners();
    }

    public int getItemCount(){
        return mItems.size();
    }



    public void fetchNewData() {
        if(!isLoading) {
            switch (mCoinsFragmentType) {
                case NORMAL:
                    getCoinsData();
                    break;
                case FAVORITES:
                    getFavoriteCoinsData();
                    break;
            }
        }
    }


    private void getCoinsData() {
        isLoading = true;
        getMvpView().showLoading();
        Timber.d("Fetching coins data");


        addDisposable(mDataManager.getOldestCoinLastUpdated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lastUpdated -> {
                    long timeSinceLastUpdate = (new Date().getTime()/1000) - lastUpdated;

                    if(timeSinceLastUpdate > Constants.COINS_FRAGMENT_MAX_SECONDS_SINCE_LAST_UPDATE) {
                        Timber.d("FAV: Local data is old, fetching remote coins data. TimeSinceLastUpdate=%1$d secs", timeSinceLastUpdate);

                        //Local data is old, try to grab remote data
                        addDisposable(mDataManager.getRemoteCoins("USD")
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(coins -> {
                                    Timber.d("Fetched remote coins data");

                                    isLoading = false;
                                    getMvpView().hideLoading();
                                    mItems = coins;
                                    applySorting();
                                    getMvpView().refreshCoinsList();
                                }, throwable -> {

                                    //Could not fetch remote, grab local copy
                                    getMvpView().showMessage(Message.COULD_NOT_FETCH_FRESH_COIN_DATA, null);
                                    Timber.d(throwable, "Error fetching remote coin data, getting local copy");

                                    addDisposable(mDataManager.getLocalCoins()
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(coins -> {
                                                isLoading = false;
                                                getMvpView().hideLoading();
                                                mItems = coins;
                                                applySorting();
                                                getMvpView().refreshCoinsList();
                                                Timber.d("Got local coins data");

                                            }, throwable1 -> {
                                                isLoading = false;
                                                getMvpView().hideLoading();
                                                getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                                                Timber.e(throwable1, "Error fetching remote and local coin data");
                                            }));

                                }));

                    } else {
                        //Local data is recent
                        Timber.d("Local data is recent, getting local coins data. TimeSinceLastUpdate=%1$d secs", timeSinceLastUpdate);
                        addDisposable(mDataManager.getLocalCoins()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(coins -> {
                                    isLoading = false;
                                    getMvpView().hideLoading();
                                    mItems = coins;
                                    applySorting();
                                    getMvpView().refreshCoinsList();
                                    Timber.d("Got local coins data");

                                }, throwable -> {
                                    isLoading = false;
                                    getMvpView().hideLoading();
                                    Timber.e(throwable);
                                    getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                                    Timber.d("Error getting local coins data");
                                }));
                    }

                }, throwable1 -> {
                    if(throwable1 instanceof EmptyResultSetException) {

                        //No coins in db, try to grab remote data
                        Timber.d("No coins saved locally, fetching remote data");

                        addDisposable(mDataManager.getRemoteCoins("USD")
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(coins -> {
                                    Timber.d("Fetched remote coins data");

                                    isLoading = false;
                                    getMvpView().hideLoading();
                                    mItems = coins;
                                    applySorting();
                                    getMvpView().refreshCoinsList();
                                }, throwable3 -> {
                                    isLoading = false;
                                    getMvpView().hideLoading();
                                    Timber.e(throwable3, "Error fetching remote coin data");
                                    getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                                }));

                    } else {
                        isLoading = false;
                        getMvpView().hideLoading();
                        Timber.d(throwable1);
                        getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                    }

                }));
    }


    private void getFavoriteCoinsData() {

        isLoading = true;
        getMvpView().showLoading();
        Timber.d("FAV: Fetching favorite coins data");

        addDisposable(mDataManager.getOldestFavoriteCoinLastUpdated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lastUpdated -> {
                    long timeSinceLastUpdate = (new Date().getTime()/1000) - lastUpdated;

                    if(timeSinceLastUpdate > Constants.COINS_FRAGMENT_MAX_SECONDS_SINCE_LAST_UPDATE) {
                        Timber.d("FAV: Local data is old, fetching remote favorite coins data. TimeSinceLastUpdate=%1$d secs", timeSinceLastUpdate);

                        //Local data is old, try to grab remote data
                        addDisposable(mDataManager.getRemoteFavoriteCoins("USD")
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(coins -> {
                                    Timber.d("FAV: Fetched remote favorite coins data");

                                    isLoading = false;
                                    getMvpView().hideLoading();
                                    mItems = coins;
                                    applySorting();
                                    getMvpView().refreshCoinsList();
                                }, throwable -> {

                                    //Could not fetch remote, grab local copy
                                    getMvpView().showMessage(Message.COULD_NOT_FETCH_FRESH_COIN_DATA, null);
                                    Timber.d(throwable, "FAV: Error fetching remote favorite coin data, grabbing local copy");

                                    addDisposable(mDataManager.getLocalFavoriteCoins()
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(coins -> {
                                                isLoading = false;
                                                getMvpView().hideLoading();
                                                mItems = coins;
                                                applySorting();
                                                getMvpView().refreshCoinsList();
                                                Timber.d("FAV: Got local favorite coins data");

                                            }, throwable2 -> {
                                                isLoading = false;
                                                getMvpView().hideLoading();
                                                getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                                                Timber.d(throwable2, "FAV: Error getting remote and local favorite coin data");
                                            }));

                                }));

                    } else {
                        //Local data is recent
                        Timber.d("FAV: Local data is recent, getting local favorite coins data. TimeSinceLastUpdate=%1$d secs", timeSinceLastUpdate);

                        addDisposable(mDataManager.getLocalFavoriteCoins()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(coins -> {
                                    isLoading = false;
                                    getMvpView().hideLoading();
                                    mItems = coins;
                                    applySorting();
                                    getMvpView().refreshCoinsList();
                                    Timber.d("FAV: Got local favorite coins data");

                                }, throwable -> {
                                    isLoading = false;
                                    getMvpView().hideLoading();
                                    Timber.e(throwable);
                                    getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                                    Timber.d("FAV: Error getting local favorite coins data");
                                }));
                    }

                }, throwable1 -> {
                    if(throwable1 instanceof EmptyResultSetException) {
                        //TODO: show empty view, no favorites here
                        Timber.d(throwable1,"FAV: No favorite coins");

                    } else {
                        Timber.d(throwable1);
                        getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                    }

                    isLoading = false;
                    getMvpView().hideLoading();
                }));
    }

    public void changeSortingType(CoinsSortType coinsSortType) {
        mCoinsSortType = coinsSortType;
        applySorting();
        getMvpView().refreshCoinsList();
    }


    private void applySorting() {

        switch (mCoinsSortType) {
            case NAME_ASCENDING:
                Collections.sort(mItems, new CoinComparatorByNameAsc());
                break;
            case NAME_DESCENDING:
                Collections.sort(mItems, new CoinComparatorByNameDesc());
                break;
            case PRICE_ASCENDING:
                Collections.sort(mItems, new CoinComparatorByPriceAsc());
                break;
            case PRICE_DESCENDING:
                Collections.sort(mItems, new CoinComparatorByPriceDesc());
                break;
            case MCAP_ASCENDING:
                Collections.sort(mItems, new CoinComparatorByMcapAsc());
                break;
            case MCAP_DESCENDING:
                Collections.sort(mItems, new CoinComparatorByMcapDesc());
                break;
            case VOL_ASCENDING:
                Collections.sort(mItems, new CoinComparatorByVolAsc());
                break;
            case VOL_DESCENDING:
                Collections.sort(mItems, new CoinComparatorByVolDesc());
                break;
            case _1H_ASCENDING:
                Collections.sort(mItems, new CoinComparatorBy1hAsc());
                break;
            case _1H_DESCENDING:
                Collections.sort(mItems, new CoinComparatorBy1hDesc());
                break;
            case _24H_ASCENDING:
                Collections.sort(mItems, new CoinComparatorBy24hAsc());
                break;
            case _24H_DESCENDING:
                Collections.sort(mItems, new CoinComparatorBy24hDesc());
                break;
            case _7D_ASCENDING:
                Collections.sort(mItems, new CoinComparatorBy7dAsc());
                break;
            case _7D_DESCENDING:
                Collections.sort(mItems, new CoinComparatorBy7dDesc());
                break;
        }

    }

    public void onItemLongClickedAtPosition(int adapterPosition, CoinsListRowView rowView) {
        Coin coin = mItems.get(adapterPosition);
        if(coin.coinsListViewHolderState == CoinsListViewHolderState.NORMAL) {
            coin.coinsListViewHolderState = CoinsListViewHolderState.SHOWING_FAVORITE_OVERLAY;

            addDisposable(mDataManager.isFavoriteCoin(coin.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        if(result == 1)    //Is favorite
                            rowView.revealFavoriteView(R.string.fragment_coins_removed_from_favorites);
                        else     //Not favorite
                            rowView.revealFavoriteView(R.string.fragment_coins_added_to_favorites);

                    }, throwable -> {
                        Timber.e(throwable);
                        getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                    }));
        }
    }

    public void favoriteViewTimeout(int adapterPosition, CoinsListRowView rowView) {
        if(adapterPosition != NO_POSITION) {
            Coin coin = mItems.get(adapterPosition);
            addDisposable(mDataManager.isFavoriteCoin(coin.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        if(result == 1) {
                            addDisposable(mDataManager.removeCoinFromFavorites(coin.getId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {

                                    }, throwable -> {
                                        Timber.e(throwable);
                                        getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                                    }));
                        } else {
                            addDisposable(mDataManager.setCoinAsFavorite(coin.getId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {

                                    }, throwable -> {
                                        Timber.e(throwable);
                                        getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                                    }));
                        }

                        if(mCoinsFragmentType == CoinsFragmentType.NORMAL) {
                            rowView.hideFavoriteView();
                            mItems.get(adapterPosition).coinsListViewHolderState = CoinsListViewHolderState.NORMAL;
                        } else {    //CoinsFragmentType.FAVORITES
                            if(adapterPosition < mItems.size()) {
                                mItems.remove(adapterPosition);
                                getMvpView().refreshAfterItemRemoved(adapterPosition, mItems.size());
                            } else {
                                getMvpView().refreshCoinsList();
                            }
                        }


                    }, throwable -> {
                        Timber.e(throwable);
                        getMvpView().showMessage(Message.ERROR_UNEXPECTED, null);
                    }));
        }
    }

    public void onFavoriteCanceledAtPosition(int adapterPosition, CoinsListRowView rowView) {
        if(adapterPosition != NO_POSITION) {
            Coin coin = mItems.get(adapterPosition);
            coin.coinsListViewHolderState = CoinsListViewHolderState.NORMAL;
            rowView.cancelFavoriteView();
        }
    }


}
