package com.groomandbride.data.body;

import com.google.gson.annotations.SerializedName;

public class ReqFavoritesBody {
    private int limit;
    @SerializedName("offset") private int page_num;

    public ReqFavoritesBody(int limit, int page_num) {
        this.limit = limit;
        this.page_num = page_num;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage_num() {
        return page_num;
    }

    public void setPage_num(int page_num) {
        this.page_num = page_num;
    }
}
