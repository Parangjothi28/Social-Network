package com.app.entities;

import java.util.Random;

public class Transaction {
    private int id = new Random().nextInt();
    private int userId;
    private int Card = 123456;
    private double amount = 0;
    private String address = "";
    private String date = "";
    private String receipt = "";

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

    public int getCard() {
        return Card;
    }

    public void setCard(int Card) {
        this.Card = Card;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }
}
