package com.example.huynhmatngua.doctruyenvoz.Object;

public class Chap {

    private int begin;
    private int end;

    public Chap(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Chap{" +
                "begin=" + begin +
                ", end=" + end +
                '}';
    }
}
