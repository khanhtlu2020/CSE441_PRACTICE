package com.example.btth03;

import java.io.Serializable;

public class Full_name implements Serializable {
    String first,last,midd;

    public Full_name(String first, String last, String midd) {
        this.first = first;
        this.last = last;
        this.midd = midd;
    }

    public Full_name(String name) {
        String[] array = name.split(" ");
        this.first = array[array.length-1];
        this.last = array[0];
        String midd = "";
        for (int i = 1; i < array.length-1; i++) {
            midd+=array[i];
        }
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