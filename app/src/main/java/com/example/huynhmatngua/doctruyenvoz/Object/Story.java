package com.example.huynhmatngua.doctruyenvoz.Object;

public class Story {

    private String name;
    private String chap;
    private String body;

    public Story(String name, String chap, String body) {
        this.name = name;
        this.chap = chap;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChap() {
        return chap;
    }

    public void setChap(String chap) {
        this.chap = chap;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Story{" +
                "name='" + name + '\'' +
                ", chap='" + chap + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
