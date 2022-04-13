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
                tbL.removeViewsInLayout(1, 7);
                layMonHoc(database);
                loadTrangThaiButton();
            }
        });

        btnSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maHS += 1;
                layThongTin(database);
                tbL.removeViewsInLayout(1, 7);
                layMonHoc(database);
                loadTrangThaiButton();
            }
        });
    }

    private void loadTrangThaiButton() {

        tvTongMH.setText(""+tongMH);
        tongMH=0;

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

        Cursor data = db.GetData("SELECT " + DBHelper.COL_MONHOC_MAMONHOC + ", " + DBHelper.COL_MONHOC_TENMONHOC
                + " FROM " + DBHelper.TB_MONHOC);
        while(data.moveToNext())
        {
            tongMH += 1 ;
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
            dsHS.add(data);
        }
    }
}
