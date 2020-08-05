package com.thoughtworks.rslist.domain;

public class RsEvent {
    private int id;

    private String eventName;

    private String keyWord;
    // use jackson must have empty constructor

    public RsEvent() {
    }

    public RsEvent(int id, String eventName, String keyWord) {
        this.id = id;
        this.eventName = eventName;
        this.keyWord = keyWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
