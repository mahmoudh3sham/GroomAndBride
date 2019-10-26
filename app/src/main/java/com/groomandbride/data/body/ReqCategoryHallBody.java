package com.groomandbride.data.body;

import com.google.gson.annotations.SerializedName;

public class ReqCategoryHallBody {
    private int limit;
    @SerializedName("offset") private int page_num;
    @SerializedName("hallCategory") private String cate_id;

    public ReqCategoryHallBody(int limit, int page_num, String cate_id) {
        this.limit = limit;
        this.page_num = page_num;
        this.cate_id = cate_id;
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

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }
}
