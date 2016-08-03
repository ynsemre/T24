package com.tmob.t24.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("list")
    @Expose
    private String list;
    @SerializedName("box")
    @Expose
    private String box;
    @SerializedName("page")
    @Expose
    private String page;
    @SerializedName("grid")
    @Expose
    private String grid;

    /**
     * @return The list
     */
    public String getList() {
        return list;
    }

    /**
     * @param list The list
     */
    public void setList(String list) {
        this.list = list;
    }

    /**
     * @return The box
     */
    public String getBox() {
        return box;
    }

    /**
     * @param box The box
     */
    public void setBox(String box) {
        this.box = box;
    }

    /**
     * @return The page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page The page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @return The grid
     */
    public String getGrid() {
        return grid;
    }

    /**
     * @param grid The grid
     */
    public void setGrid(String grid) {
        this.grid = grid;
    }

}