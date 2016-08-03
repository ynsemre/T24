package com.tmob.t24.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Urls {

    @SerializedName("web")
    @Expose
    private String web;

    /**
     * @return The web
     */
    public String getWeb() {
        return web;
    }

    /**
     * @param web The web
     */
    public void setWeb(String web) {
        this.web = web;
    }

}