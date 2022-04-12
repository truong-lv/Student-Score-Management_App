package com.example.studentscoremanagement.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.studentscoremanagement.DBHelper;

public class TaiKhoan {
    private String tenTaiKhoan;
    private String matKhau;
    private String sdt;
    private String anh;

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

    public TaiKhoan(String tenTaiKhoan, String matKhau, String sdt, String anh) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        this.sdt = sdt;
        this.anh = anh;
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

    public String getAnh() {
        return anh;
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

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public boolean checkLogin(DBHelper db){
        Cursor getInfor=db.GetData("SELECT "+DBHelper.COL_TAIKHOAN_SDT+", "+DBHelper.COL_TAIKHOAN_ANH+" FROM "+DBHelper.TB_TAIKHOAN
                +" WHERE "+DBHelper.COL_TAIKHOAN_TEN+"='"+this.tenTaiKhoan+"' AND "+DBHelper.COL_TAIKHOAN_MATKHAU+"='"+this.matKhau+"'");
        if(getInfor.moveToNext()){
            this.setSdt(getInfor.getString(0));
            this.setAnh(getInfor.getString(1));
            return true;
        }
        return false;
    }

    public void saveToDatabase (DBHelper db){
        String query = "UPDATE " + DBHelper.TB_TAIKHOAN
                + " SET " + DBHelper.COL_TAIKHOAN_MATKHAU + " = '" + matKhau + "', "
                    + DBHelper.COL_TAIKHOAN_SDT + " = '" + sdt + "', "
                    + DBHelper.COL_TAIKHOAN_ANH + " = '" + anh + "' "
                + " WHERE "+DBHelper.COL_TAIKHOAN_TEN+"='"+this.tenTaiKhoan+"'";

        db.QueryData(query);
    }
}
