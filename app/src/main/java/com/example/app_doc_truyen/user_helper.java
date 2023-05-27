package com.example.app_doc_truyen;

import java.util.List;

public class user_helper {
    private String TenDK, email, phone, matkhau;
    private List<String> yeuthich;

    public user_helper(String tenDK, String email, String phone, String matkhau) {
        TenDK = tenDK;
        this.email = email;
        this.phone = phone;
        this.matkhau = matkhau;
    }

    public user_helper() {

    }

    public List<String> getYeuthich() {
        return yeuthich;
    }

    public void setYeuthich(List<String> yeuthich) {
        this.yeuthich = yeuthich;
    }

    public String getTenDK() {
        return TenDK;
    }

    public void setTenDK(String tenDK) {
        TenDK = tenDK;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
