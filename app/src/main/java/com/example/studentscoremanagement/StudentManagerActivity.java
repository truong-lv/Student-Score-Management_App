package com.example.studentscoremanagement;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
    TextView tvHoTen, tvNgaySinh, tvLop, tvPhai;
    Button btnTruoc, btnSau;
    String maLop;
    List<String> dsHS = new ArrayList<>();
//    int maHS = 1;

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
        Toast.makeText(this, "Xin chao", Toast.LENGTH_SHORT).show();
    }


    private void setEvent() {
        database = new DBHelper(this);

//        layThongTin(database);
//        Toast.makeText(this, maLop, Toast.LENGTH_SHORT).show();
//        layMonHoc(database);
//        layDSLop(database);
//        loadTrangThaiButton();
//        test(dsHS);

        btnTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StudentManagerActivity.this, "sau", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void loadTrangThaiButton() {
//        if(maHS == Integer.parseInt(dsHS.get(0)))
//        {
//            btnTruoc.setEnabled(false);
//        }
//        else if(maHS == Integer.parseInt(dsHS.get(dsHS.size())))
//        {
//            btnSau.setEnabled(false);
//        }
//    }


    public void layThongTin(DBHelper db){
        String ho, ten, phai, ngaysinh, malop = "";
        Cursor data =db.GetData("SELECT "+ DBHelper.COL_HOCSINH_HO +", "+DBHelper.COL_TAIKHOAN_TEN +
                ", " + DBHelper.COL_HOCSINH_PHAI + ", " + DBHelper.COL_HOCSINH_NGAYSINH + ", " + DBHelper.COL_HOCSINH_MALOP +
                " FROM "+ DBHelper.TB_HOCSINH
//                +" WHERE "+ DBHelper.COL_HOCSINH_MAHOCSINH + "= " + maHS);
                +" WHERE "+ DBHelper.COL_HOCSINH_MAHOCSINH + "= 1");
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
        Cursor data = db.GetData("SELECT " + DBHelper.COL_MONHOC_MAMONHOC + ", " + DBHelper.COL_MONHOC_TENMONHOC
                + " FROM " + DBHelper.TB_MONHOC);
        while(data.moveToNext())
        {

            maMH = data.getString(0);
            tenMH = data.getString(1);
            TableLayout tbL = (TableLayout) findViewById(R.id.tbDiem);
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
                    + " WHERE " + DBHelper.COL_DIEM_MAHOCSINH + " = 1" +
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
            tbRow.addView(tv3);
            tbRow.setPadding(0,30,0,10);
            tbL.addView(tbRow);
        }
    }

    private void layDSLop(DBHelper db) {
        Cursor dsLopData = db.GetData("SELECT " + DBHelper.COL_HOCSINH_MAHOCSINH + " FROM " + DBHelper.TB_HOCSINH
                + " WHERE " + DBHelper.COL_HOCSINH_MALOP + " = '" + maLop + "'");
        while(dsLopData.moveToNext())
        {
            String data = dsLopData.getString(0);
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
            dsHS.add(data);
        }
    }
}
