package com.tmob.t24.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsObject implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("excerpt")
    @Expose
    private String excerpt;
    @SerializedName("alias")
    @Expose
    private String alias;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("urls")
    @Expose
    private Urls urls;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("stats")
    @Expose
    private Stats stats;
    @SerializedName("images")
    @Expose
    private Images images;
    @SerializedName("publishingDate")
    @Expose
    private String publishingDate;

    private Integer loadingVisibility = View.GONE;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The excerpt
     */
    public String getExcerpt() {
        return excerpt;
    }

    /**
     * @param excerpt The excerpt
     */
    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    /**
     * @return The alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias The alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return The urls
     */
    public Urls getUrls() {
        return urls;
    }

    /**
     * @param urls The urls
     */
    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    /**
     * @return The category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return The stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * @param stats The stats
     */
    public void setStats(Stats stats) {
        this.stats = stats;
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

    /**
     * @return The publishingDate
     */
    public String getPublishingDate() {
        return publishingDate;
    }

    /**
     * @param publishingDate The publishingDate
     */
    public void setPublishingDate(String publishingDate) {
        this.publishingDate = publishingDate;
    }

    public Integer getLoadingVisibility() {
        return loadingVisibility;
    }

    public void setLoadingVisibility(Integer loadingVisibility) {
        this.loadingVisibility = loadingVisibility;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
    }

    public NewsObject() {
    }

    protected NewsObject(Parcel in) {
        this.id = in.readString();
    }

    public static final Parcelable.Creator<NewsObject> CREATOR = new Parcelable.Creator<NewsObject>() {
        @Override
        public NewsObject createFromParcel(Parcel source) {
            return new NewsObject(source);
        }

        @Override
        public NewsObject[] newArray(int size) {
            return new NewsObject[size];
        }
    };
}