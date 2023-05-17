package com.example.app_doc_truyen.model;

import java.util.List;

public class Truyen {
    public String Name;
    public String image_T;
    public List<Tap> taps;
    public String author;

    public Truyen() {
    }

    public Truyen(String name, String image_T, List<Tap> taps) {
        Name = name;
        this.image_T = image_T;
        this.taps = taps;
    }

    public Truyen(String name, String image_T, List<Tap> taps, String author) {
        Name = name;
        this.image_T = image_T;
        this.taps = taps;
        this.author = author;
    }

    public Truyen(String name, String image_T) {
        Name = name;
        this.image_T = image_T;
    }
}
