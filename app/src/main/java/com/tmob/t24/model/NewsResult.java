package com.tmob.t24.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewsResult {

    @SerializedName("data")
    @Expose
    private List<NewsObject> data = new ArrayList<NewsObject>();
    @SerializedName("paging")
    @Expose
    private Paging paging;
    @SerializedName("result")
    @Expose
    private Boolean result;

    /**
     * @return The data
     */
    public List<NewsObject> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<NewsObject> data) {
        this.data = data;
    }

    /**
     * @return The paging
     */
    public Paging getPaging() {
        return paging;
    }

    /**
     * @param paging The paging
     */
    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    /**
     * @return The result
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(Boolean result) {
        this.result = result;
    }

}