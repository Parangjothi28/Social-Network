package com.app.entities;

import java.util.Random;

public class Advertisements {

    private int id = new Random().nextInt();
    private int userId;
    private byte[] content;
    private int views;
    private int viewSpot;
    private String subscriptionEndDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getViewSpot() {
        return viewSpot;
    }

    public void setViewSpot(int viewSpot) {
        this.viewSpot = viewSpot;
    }

    public String getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(String subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }
}