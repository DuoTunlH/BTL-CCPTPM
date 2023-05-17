package com.example.app_doc_truyen.model;

import java.util.List;

public class PhanLoai {
    public String Name;
    public String image;
    public List<Truyen> truyens;

    public PhanLoai(String name, String image) {
        Name = name;
        this.image = image;
    }

    public PhanLoai(String name, String image, List<Truyen> truyens) {
        Name = name;
        this.image = image;
        this.truyens = truyens;
    }

    public PhanLoai() {

    }
}
