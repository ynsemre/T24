package com.tmob.t24.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Author {

    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("alias")
    @Expose
    private Object alias;
    @SerializedName("images")
    @Expose
    private Images images;

    /**
     * @return The name
     */
    public Object getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(Object name) {
        this.name = name;
    }

    /**
     * @return The alias
     */
    public Object getAlias() {
        return alias;
    }

    /**
     * @param alias The alias
     */
    public void setAlias(Object alias) {
        this.alias = alias;
    }

    /**
     * @return The images
     */
    public Images getImages() {
        return images;
    }

    /**
     * @param images The images
     */
    public void setImages(Images images) {
        this.images = images;
    }

}