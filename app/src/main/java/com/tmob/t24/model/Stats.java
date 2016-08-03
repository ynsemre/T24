package com.tmob.t24.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {

    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("shares")
    @Expose
    private Integer shares;
    @SerializedName("interactions")
    @Expose
    private Integer interactions;
    @SerializedName("reads")
    @Expose
    private Integer reads;
    @SerializedName("pageviews")
    @Expose
    private Integer pageviews;

    /**
     * @return The likes
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     * @param likes The likes
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * @return The comments
     */
    public Integer getComments() {
        return comments;
    }

    /**
     * @param comments The comments
     */
    public void setComments(Integer comments) {
        this.comments = comments;
    }

    /**
     * @return The shares
     */
    public Integer getShares() {
        return shares;
    }

    /**
     * @param shares The shares
     */
    public void setShares(Integer shares) {
        this.shares = shares;
    }

    /**
     * @return The interactions
     */
    public Integer getInteractions() {
        return interactions;
    }

    /**
     * @param interactions The interactions
     */
    public void setInteractions(Integer interactions) {
        this.interactions = interactions;
    }

    /**
     * @return The reads
     */
    public Integer getReads() {
        return reads;
    }

    /**
     * @param reads The reads
     */
    public void setReads(Integer reads) {
        this.reads = reads;
    }

    /**
     * @return The pageviews
     */
    public Integer getPageviews() {
        return pageviews;
    }

    /**
     * @param pageviews The pageviews
     */
    public void setPageviews(Integer pageviews) {
        this.pageviews = pageviews;
    }

}