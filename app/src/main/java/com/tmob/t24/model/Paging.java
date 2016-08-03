package com.tmob.t24.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paging {

    @SerializedName("current")
    @Expose
    private Integer current;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("pages")
    @Expose
    private Integer pages;
    @SerializedName("items")
    @Expose
    private Integer items;

    /**
     * @return The current
     */
    public Integer getCurrent() {
        return current;
    }

    /**
     * @param current The current
     */
    public void setCurrent(Integer current) {
        this.current = current;
    }

    /**
     * @return The limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @param limit The limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * @return The pages
     */
    public Integer getPages() {
        return pages;
    }

    /**
     * @param pages The pages
     */
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    /**
     * @return The items
     */
    public Integer getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(Integer items) {
        this.items = items;
    }

}