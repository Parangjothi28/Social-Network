package com.app.entities;

import java.util.Random;

public class CreditCard {
    private int id = new Random().nextInt();
    private int userId;
    private long Card = 123456;
    private int cvv = 777;
    private int month = 0;
    private int year = 0;

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

    public long getCard() {
        return Card;
    }

    public void setCard(long Card) {
        this.Card = Card;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
