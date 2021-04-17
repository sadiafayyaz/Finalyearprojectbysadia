package com.example.androidcarmanager.Database;

public class Add_Notes_DB {
    String notetitle, notemessage;


    public Add_Notes_DB() {
    }

    public Add_Notes_DB(String title, String message) {
        this.notetitle = title;
        this.notemessage = message;

    }

    public String getTitle() {
        return notetitle;
    }

    public void setTitle(String title) {
        this.notetitle = title;
    }

    public String getMessage() {
        return notemessage;
    }

    public void setMessage(String message) {
        this.notemessage = message;
    }


}
