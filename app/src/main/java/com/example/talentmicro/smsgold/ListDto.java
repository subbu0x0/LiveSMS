package com.example.talentmicro.smsgold;

import java.io.Serializable;
import java.util.ArrayList;

class ListDto implements Serializable {

    private String usrName;
    private String message;
    long date;
    //private ArrayList<String> messageList;


    public ListDto() {
    }

    public ListDto(String usrName, String message,long date) {
        this.usrName = usrName;
        this.message = message;
        this.date = date;
    }


    public String getUsrName() {
        return usrName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

  /*  public ArrayList<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<String> messageList) {
        this.messageList = messageList;
    }*/
}
