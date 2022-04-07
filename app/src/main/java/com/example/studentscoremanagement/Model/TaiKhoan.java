package com.example.studentscoremanagement.Model;

public class TaiKhoan {
    private String tenTaiKhoan;
    private String matKhau;
    private String sdt;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenTaiKhoan, String matKhau, String sdt) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        this.sdt=sdt;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
