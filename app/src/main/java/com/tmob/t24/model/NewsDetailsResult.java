package com.tmob.t24.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsDetailsResult {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private NewsObject data;

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

    /**
     * @return The data
     */
    public NewsObject getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(NewsObject data) {
        this.data = data;
    }

}