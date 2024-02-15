package com.example.myapplication;

public class Message {
    private String sender;
    private String receiver;
    private String context;

    public Message(){}

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    public Message(String sender,String receiver,String context)
    {
        this.sender=sender;
        this.receiver=receiver;
        this.context=context;


    }

}

