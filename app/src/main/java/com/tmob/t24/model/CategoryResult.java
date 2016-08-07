package com.tmob.t24.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoryResult {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("data")
    @Expose
    private List<Category> data = new ArrayList<Category>();

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
    public List<Category> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<Category> data) {
        this.data = data;
    }

}