package ve.com.abicelis.cryptomaster.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by abicelis on 16/9/2018.
 */

@Entity(tableName = "cached_coin")
public class CachedCoin implements Comparable<CachedCoin> {

    @PrimaryKey
    @ColumnInfo(name = "cached_coin_id")
    private long id;                                //CoinMarketCap ID
    private String name;
    private String code;
    @ColumnInfo(name = "website_slug")
    private String websiteSlug;
    private int rank;


    public CachedCoin(long id, String name, String code, String websiteSlug, int rank) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.websiteSlug = websiteSlug;
        this.rank = rank;
    }


    public long getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }
    public String getWebsiteSlug() {
        return websiteSlug;
    }
    public int getRank() {
        return rank;
    }



    @Override
    public int compareTo(@NonNull CachedCoin o) {
        return Double.compare(o.getId(), this.getId());
    }
}
