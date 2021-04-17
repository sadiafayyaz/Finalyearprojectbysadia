package com.example.androidcarmanager.Database;

public class Expence_DB {
    String expenseTitle;
    Long date, time;
    Double odometer, price;

    public Expence_DB() {
    }

    public Expence_DB(String expenseTitle, Long date, Long time, Double odometer, Double price) {
        this.expenseTitle = expenseTitle;
        this.date = date;
        this.time = time;
        this.odometer = odometer;
        this.price = price;
    }

    public String getExpenseTitle() {
        return expenseTitle;
    }

    public void setExpenseTitle(String expenseTitle) {
        this.expenseTitle = expenseTitle;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Double getOdometer() {
        return odometer;
    }

    public void setOdometer(Double odometer) {
        this.odometer = odometer;
    }

    public Double getCost() {
        return price;
    }

    public void setCost(Double cost) {
        this.price = cost;
    }
}

