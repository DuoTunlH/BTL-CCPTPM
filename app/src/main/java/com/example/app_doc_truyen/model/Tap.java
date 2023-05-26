package com.example.app_doc_truyen.model;

import com.example.app_doc_truyen.ListBL.ListView_BL;

import java.util.List;

public class Tap {
    public String Name;
    public List<String> links;
    public List<ListView_BL> binhluan;
    public int yeuthich ;

    public Tap(String name, List<String> links) {
        Name = name;
        this.links = links;
    }

    public Tap() {

    }
}
