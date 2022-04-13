package com.example.studentscoremanagement.Model;

public class HocSinh {
    private String tbHocSinh;
    private String maHocSinh;
    private String ho;
    private String ten;
    private String phai;
    private String ngaySinh;
    private String maLop;


    public HocSinh() {
    }

    public HocSinh(String tbHocSinh, String maHocSinh, String ho, String ten, String phai, String ngaySinh, String maLop) {
        this.tbHocSinh = tbHocSinh;
        this.maHocSinh = maHocSinh;
        this.ho = ho;
        this.ten = ten;
        this.phai = phai;
        this.ngaySinh = ngaySinh;
        this.maLop = maLop;
    }

    public HocSinh(String mahs, String ho, String ten, String phai, String ngaySinh) {
        this.maHocSinh = mahs;
        this.ho = ho;
        this.ten = ten;
        this.phai = phai;
        this.ngaySinh = ngaySinh;
    }

    public String getTbHocSinh() {
        return tbHocSinh;
    }

    public String getMaHocSinh() {
        return maHocSinh;
    }

    public String getHo() {
        return ho;
    }

    public String getTen() {
        return ten;
    }


    public String getPhai() {
        return phai;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setTbHocSinh(String tbHocSinh) {
        this.tbHocSinh = tbHocSinh;
    }

    public void setMaHocSinh(String maHocSinh) {
        this.maHocSinh = maHocSinh;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setPhai(String phai) {
        this.phai = phai;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }
}