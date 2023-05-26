package com.example.app_doc_truyen.ListBL;

public class ListView_BL {
    String user ;
    String bl ;

    public ListView_BL(String user, String bl) {
        this.user = user;
        this.bl = bl;
    }

    public ListView_BL() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }
}
