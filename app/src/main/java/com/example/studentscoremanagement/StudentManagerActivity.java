package com.example.studentscoremanagement;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class StudentManagerActivity extends AppCompatActivity {

    DBHelper database;
    TextView tvHoTen, tvNgaySinh, tvLop, tvPhai, tvTongMH, tvDiemTB;
    Button btnTruoc, btnSau;
    String maLop;
    TableLayout tbL;
    List<String> dsHS = new ArrayList<>();
    int maHS = 1;
    int tongMH = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manager);

        addControl();
        setEvent();
    }

    private void addControl() {
        tvHoTen = findViewById(R.id.tvHoTen);
        tvLop = findViewById(R.id.tvLop);
        tvNgaySinh = findViewById(R.id.tvNgaySinh);
        tvPhai = findViewById(R.id.tvPhai);
        btnTruoc = findViewById(R.id.btnTruoc);
        btnSau = findViewById(R.id.btnSau);
        tbL = (TableLayout) findViewById(R.id.tbDiem);
        tvTongMH = findViewById(R.id.tvTongMH);
        tvDiemTB = findViewById(R.id.tvDiemTB);
    }


    private void setEvent() {
        database = new DBHelper(this);

        layThongTin(database);
        layMonHoc(database);
        layDSLop(database);
        loadTrangThaiButton();


        btnTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maHS -= 1;
                layThongTin(database);
                tbL.removeViewsInLayout(1, tongMH);
                layMonHoc(database);
                loadTrangThaiButton();
            }
        });

        btnSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maHS += 1;
                layThongTin(database);
                tbL.removeViewsInLayout(1, tongMH);
                layMonHoc(database);
                loadTrangThaiButton();
            }
        });
    }

    private void loadTrangThaiButton() {
        tvTongMH.setText(""+tongMH);

        if(maHS == Integer.parseInt(dsHS.get(0)))
        {
            btnTruoc.setEnabled(false);
        }
        else if(maHS == Integer.parseInt(dsHS.get(dsHS.size()-1)))
        {
            btnSau.setEnabled(false);
        }
        else
        {
            btnTruoc.setEnabled(true);
            btnSau.setEnabled(true);
        }
    }


    public void layThongTin(DBHelper db){
        String ho, ten, phai, ngaysinh, malop = "";
        Cursor data =db.GetData("SELECT "+ DBHelper.COL_HOCSINH_HO +", "+DBHelper.COL_TAIKHOAN_TEN +
                ", " + DBHelper.COL_HOCSINH_PHAI + ", " + DBHelper.COL_HOCSINH_NGAYSINH + ", " + DBHelper.COL_HOCSINH_MALOP +
                " FROM "+ DBHelper.TB_HOCSINH
                +" WHERE "+ DBHelper.COL_HOCSINH_MAHOCSINH + "= " + maHS);
        if(data.moveToNext()){
            ho = data.getString(0);
            ten = data.getString(1);
            phai = data.getString(2);
            ngaysinh = data.getString(3);
            malop = data.getString(4);
            maLop = malop;
            tvHoTen.setText(ho + " " + ten);
            tvLop.setText(malop);
            tvNgaySinh.setText(ngaysinh);
            tvPhai.setText(phai);
        }

    }

    private void layMonHoc(DBHelper db) {
        String maMH, tenMH;
        String diem;
        int tongSoMH = 0;

        Cursor data = db.GetData("SELECT " + DBHelper.COL_MONHOC_MAMONHOC + ", " + DBHelper.COL_MONHOC_TENMONHOC
                + " FROM " + DBHelper.TB_MONHOC);
        while(data.moveToNext())
        {
            tongSoMH += 1 ;
            maMH = data.getString(0);
            tenMH = data.getString(1);
            TableRow tbRow = new TableRow(this);

            TextView tv = new TextView(this);
            tv.setText(maMH);
            tv.setTextColor(Color.WHITE);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(20);
            tbRow.addView(tv);

            TextView tv2 = new TextView(this);
            tv2.setText(tenMH);
            tv2.setTextColor(Color.WHITE);
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextSize(20);
            tbRow.addView(tv2);

            Cursor dataDiem = db.GetData("SELECT " + DBHelper.COL_DIEM_DIEM + " FROM " + DBHelper.TB_DIEM
                    + " WHERE " + DBHelper.COL_DIEM_MAHOCSINH + " = " + maHS +
                    " AND " + DBHelper.COL_DIEM_MAMONHOC + " = " + maMH);

            if(dataDiem.moveToNext())
            {
                diem = ""+dataDiem.getFloat(0);
            }
            else
            {
                diem = ".";
            }


            TextView tv3 = new TextView(this);
            tv3.setText("" + diem);
            tv3.setTextColor(Color.WHITE);
            tv3.setGravity(Gravity.CENTER);
            tv3.setTextSize(20);
            int id = Integer.parseInt(maMH);
            String diemNhap = diem;
            tv3.setId(id);
            tv3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    dialogNhapDiem(diemNhap, id);
                    return false;
                }
            });
            tbRow.addView(tv3);
            tbRow.setPadding(0,30,0,10);
            tbL.addView(tbRow);
        }
        tongMH = tongSoMH;
    }

    private void layDSLop(DBHelper db) {
        Cursor dsLopData = db.GetData("SELECT " + DBHelper.COL_HOCSINH_MAHOCSINH + " FROM " + DBHelper.TB_HOCSINH
                + " WHERE " + DBHelper.COL_HOCSINH_MALOP + " = '" + maLop + "'");
        while(dsLopData.moveToNext())
        {
            String data = dsLopData.getString(0);
            dsHS.add(data);
        }
    }


    private void dialogNhapDiem(String diem, int maMH){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_nhap_diem);

        EditText edtNhapDiem = (EditText) dialog.findViewById(R.id.edtNhapDiem);
        Button btnThem = (Button) dialog.findViewById(R.id.btnThem);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);

        edtNhapDiem.setText(diem);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diemMoi = edtNhapDiem.getText().toString().trim();
                if(TextUtils.isEmpty(diemMoi))
                {
                    Toast.makeText(StudentManagerActivity.this, "Vui lòng nhập điểm cho môn học", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Float diemMoiFloat = isFloat(diemMoi);
                    if(diemMoiFloat == -1)
                    {
                        Toast.makeText(StudentManagerActivity.this, "Điểm nhập phải thuộc khoảng từ 0 đến 10", Toast.LENGTH_SHORT).show();
                    }
                    else if (diemMoiFloat == -2)
                    {
                        Toast.makeText(StudentManagerActivity.this, "Vui lòng nhập đúng định dạng điểm", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Nếu chưa có điểm thì insert điểm mới vào
                        if(diem == "."){
                            insertDiem(database, maMH , diemMoiFloat);
                            Toast.makeText(StudentManagerActivity.this, "Nhập điểm thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        // Nếu có điểm rồi thì update điểm
                        else
                        {
                            updateDiem(database, maMH, diemMoiFloat);
                            Toast.makeText(StudentManagerActivity.this, "Sửa điểm thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private float isFloat(String diemString)
    {
        try {
            float diemFloat = Float.parseFloat(diemString);
            if(diemFloat >= 0 && diemFloat <=10 )
            {
                return diemFloat;
            }
            else
            {
                return -1;
            }

        }
        catch (NumberFormatException e)
        {
        }
        return -2;
    }

    private void insertDiem(DBHelper db, int maMH, float diemMoi)
    {
        db.QueryData("INSERT INTO " + DBHelper.TB_DIEM + " VALUES (" + maHS + ", " +  maMH + ", " + diemMoi + ")" );
        tbL.removeViewsInLayout(1, 7);
        layMonHoc(database);
    }

    private void updateDiem(DBHelper db, int maMH, float diemMoi)
    {
        db.QueryData("UPDATE " + DBHelper.TB_DIEM + " SET " + DBHelper.COL_DIEM_DIEM + " = " + diemMoi
                + " WHERE " + DBHelper.COL_DIEM_MAHOCSINH + " = " + maHS + " AND " + DBHelper.COL_DIEM_MAMONHOC + " = " + maMH);
        tbL.removeViewsInLayout(1, 7);
        layMonHoc(database);
    }

}
