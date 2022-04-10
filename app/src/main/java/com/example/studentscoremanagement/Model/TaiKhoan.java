package com.example.studentscoremanagement.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.studentscoremanagement.DBHelper;

public class TaiKhoan {
    private String tenTaiKhoan;
    private String matKhau;
    private String sdt;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenTaiKhoan, String matKhau) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
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

    public boolean checkLogin(DBHelper db){
        Cursor getInfor=db.GetData("SELECT * FROM "+db.TB_TAIKHOAN
                +" WHERE "+db.COL_TAIKHOAN_TEN+"='"+this.tenTaiKhoan+"' AND "+db.COL_TAIKHOAN_MATKHAU+"='"+this.matKhau+"'");
        if(getInfor.getCount()>0){
            return true;
        }
        return false;
    }
}
