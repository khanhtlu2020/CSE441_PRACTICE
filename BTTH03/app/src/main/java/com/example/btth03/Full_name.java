package com.example.btth03;

public class Full_name {
    String first,last,midd;

    public Full_name(String first, String last, String midd) {
        this.first = first;
        this.last = last;
        this.midd = midd;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getMidd() {
        return midd;
    }

    public void setMidd(String midd) {
        this.midd = midd;
    }

    @Override
    public String toString() {
        return last+" "+midd+" "+first;
    }
}
