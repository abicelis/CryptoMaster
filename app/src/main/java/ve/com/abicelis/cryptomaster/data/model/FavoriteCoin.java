package ve.com.abicelis.cryptomaster.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by abicelis on 23/6/2018.
 */

@Entity(tableName = "favorite_coin")
public class FavoriteCoin {

    @PrimaryKey
    @ColumnInfo(name = "favorite_coin_id")
    private long id;                                //CoinMarketCap ID

    public FavoriteCoin(long id) {
        this.id = id;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
}
